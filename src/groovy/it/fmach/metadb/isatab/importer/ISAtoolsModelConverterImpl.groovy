package it.fmach.metadb.isatab.importer

import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.isatab.model.FEMSample
import it.fmach.metadb.isatab.model.FEMStudy
import org.isatools.isacreator.model.Assay
import org.isatools.isacreator.model.Investigation
import org.isatools.isacreator.model.Study
import it.fmach.metadb.isatab.importer.AccessCodeGenerator

/**
 * @author mylonasr
 *
 * Convert Objects from the isatools into our own Objects (typically from Investigation into our own Study)
 */
class ISAtoolsModelConverterImpl implements ISAtoolsModelConverter {
	def accessCodeGenerator = new AccessCodeGenerator()
	
	// field-names which are parsed
	static final def SAMPLE_NAME = "Sample Name"
	static final def SAMPLE_ORGANISM = "Characteristics[Organism]"
	static final def SAMPLE_ORGANISM_PART = "Characteristics[Organism part]"
	static final def SAMPLE_SOURCE_NAME = "Source Name"
	static final def RUN_MS_ASSAY_NAME = "MS Assay Name"
	static final def RUN_RAW_FILE = "Raw Spectral Data File"
	static final def RUN_DERIVED_FILE= "Derived Spectral Data File"
	
	@Override
	public List<FEMStudy> convertInvestigation(Investigation iSAInvestigation) {
		
		List<FEMStudy> studyList = new ArrayList<FEMStudy>() 
		
		// loop through all studies
		Map<String, Study> studyMap = iSAInvestigation.getStudies()
		for(String studyName : studyMap.keySet()){
			FEMStudy fEMStudy = new FEMStudy()
			
			fEMStudy.identifier = studyName
			convertStudy(studyMap.get(studyName), fEMStudy)
	
			studyList.add(fEMStudy)
		}
		
		return studyList
		
	}
	
	private void convertStudy(Study iSAStudy, FEMStudy fEMStudy){
		fEMStudy.title = iSAStudy.getStudyTitle()
		fEMStudy.description = iSAStudy.getStudyDesc()
		
		 Map<String, FEMSample> sampleList = convertSampleList(iSAStudy)
		
		List<FEMAssay> assayList = convertAssayList(iSAStudy, sampleList)
		fEMStudy.assays = assayList
	}
	
	private Map<String, FEMSample> convertSampleList(Study iSAStudy){
		 Map<String, FEMSample> sampleList = [:]
		Object[][] sampleMatrix = iSAStudy.getStudySampleDataMatrix()
		
		def headerMap = [:]
		def factorMap = [:]
		def nrColumns = sampleMatrix[0].length -1
		def nrRows = sampleMatrix.length - 1
		
		// parse the header
		for(i in 0..nrColumns){
			headerMap[sampleMatrix[0][i]] = i
			def m = sampleMatrix[0][i] =~ /Factor.*\[(.+)\]/
			m.each{factorMap[it[1]] = i}
			
		}
		
		// parse the fields of interest
		for(i in 1..nrRows){
			FEMSample sample = new FEMSample()
			sample.name = sampleMatrix[i][headerMap[SAMPLE_NAME]]
			sample.sourceName = sampleMatrix[i][headerMap[SAMPLE_SOURCE_NAME]]
			sample.organism = sampleMatrix[i][headerMap[SAMPLE_ORGANISM]]
			sample.organismPart = sampleMatrix[i][headerMap[SAMPLE_ORGANISM_PART]]
			sample.rowNumber = i
			
			// parse the factors and store them as a JSON string
			def builder = new groovy.json.JsonBuilder()
			def tempMap = [:]
			factorMap.each{ tempMap[it.key] = sampleMatrix[i][it.value] }
			builder(tempMap)
			
			sample.factorJSON = builder.toString()
			sampleList[sample.name] = sample
		}
		
		return sampleList

	}
	
	private List<FEMAssay> convertAssayList(Study iSAStudy, Map<String, FEMSample> sampleList){
		List<FEMAssay> assayList = new ArrayList<FEMAssay>()		
		def assayMap = iSAStudy.getAssays()
		
		assayMap.each{ k, v ->
			def assay = new FEMAssay()
			assay.name = k
			assay.instrument = v.getAssayPlatform()
			assay.runs = convertRunList(v, sampleList)
			assay.accessCode = accessCodeGenerator.getNewCode()
			assayList << assay
		}
		
		return assayList
	}
	
	private List<FEMRun> convertRunList(Assay iSAAssay, Map<String, FEMSample> sampleList){
		def runList = new ArrayList<FEMRun>()
		Object[][] assayMatrix = iSAAssay.getAssayDataMatrix()
		
		def headerMap = [:]
		def headerList = assayMatrix[0]
		def nrColumns = assayMatrix[0].length -1
		def nrRows = assayMatrix.length - 1
		
		// parse the header
		for(i in 0..nrColumns){
			headerMap[assayMatrix[0][i]] = i
		}
		
		// parse the fields of interest
		def tempList = []
		
		for(i in 1..nrRows){
			def run = new FEMRun()
			run.sampleName = assayMatrix[i][headerMap[SAMPLE_NAME]]
			run.sample = sampleList[run.sampleName]
			run.msAssayName = assayMatrix[i][headerMap[RUN_MS_ASSAY_NAME]]
			run.rawSpectraFilePath = assayMatrix[i][headerMap[RUN_RAW_FILE]]
			run.derivedSpectraFilePath = assayMatrix[i][headerMap[RUN_DERIVED_FILE]]
			run.rowNumber = i
						
			// parse the protocols
			def tempMap = [:]
			for(k in 0..nrColumns){
				if(headerList[k] =~ /Protocol/){
					// add the last protocol info
					if(tempMap) tempList << tempMap
					
					// reset the Map and add a new one
					tempMap = [:]
					tempMap.protocolREF = assayMatrix[i][k]
				}else{
					def m = headerList[k] =~ /Parameter Value\[(.+)\]/
					m.each {tempMap[it[1]] = assayMatrix[i][k]}
				}
			}
			
			// add the last Map
			if(tempMap) tempList << tempMap
			
			
			// make a JSON text and add it to the run
			def builder = new groovy.json.JsonBuilder()
			builder(tempList)
			run.protocolJSON = builder.toString()
			
			runList.add(run)
		}
		
		return runList
	}	

}
