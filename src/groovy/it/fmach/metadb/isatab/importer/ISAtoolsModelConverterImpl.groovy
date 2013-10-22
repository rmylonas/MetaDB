package it.fmach.metadb.isatab.importer

import it.fmach.metadb.isatab.model.FEMAssay;
import it.fmach.metadb.isatab.model.FEMRun;
import it.fmach.metadb.isatab.model.FEMSample;
import it.fmach.metadb.isatab.model.FEMStudy;

import org.isatools.isacreator.model.Assay;
import org.isatools.isacreator.model.Investigation;
import org.isatools.isacreator.model.Study;

/**
 * @author mylonasr
 *
 * Convert Objects from the isatools into our own Objects (typically from Investigation into our own Study)
 */
class ISAtoolsModelConverterImpl implements ISAtoolsModelConverter {

	// field-names which are parsed
	static final def SAMPLE_NAME = "Sample Name"
	static final def SAMPLE_ORGANISM = "Characteristics[Organism]"
	static final def SAMPLE_ORGANISM_PART = "Characteristics[Organism part]"
	static final def SAMPLE_SOURCE_NAME = "Source Name"
	
	
	
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
		fEMStudy.designDescriptors = "TODO"
		
		List<FEMSample> sampleList = convertSampleList(iSAStudy)
		fEMStudy.samples = sampleList
		
		List<FEMAssay> assayList = convertAssayList(iSAStudy)
		fEMStudy.assays = assayList
	}
	
	private List<FEMSample> convertSampleList(Study iSAStudy){
		List<FEMSample> sampleList = []
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
			
			sample.factors = builder.toString()
			sampleList.add(sample)
		}
		
		return sampleList

	}
	
	private List<FEMAssay> convertAssayList(Study iSAStudy){
		List<FEMAssay> assayList = new ArrayList<FEMAssay>()		
		def assayMap = iSAStudy.getAssays()
		
		assayMap.each{ k, v ->
			def assay = new FEMAssay()
			assay.name = k
			assay.instrument = v.getAssayPlatform()
			convertRunList(v)
			assayList << assay
		}
		
		return assayList
	}
	
	private List<FEMRun> convertRunList(Assay iSAAssay){
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
		for(i in 1..nrRows){
			def run = new FEMRun()
			run.sampleName = assayMatrix[i][headerMap[SAMPLE_NAME]]

			run.rowNumber = i
						
			// parse the protocols
			def tempList = []
			for(k in 0..nrColumns){
				def tempMap = [:]
				if(headerList[k] =~ /Protocol/){
					tempMap.protocolREF = assayMatrix[i][k]
				}else{
					def m = k =~ /Parameter Value\[(.+)\]/
					m.each {tempMap[it[1]] = assayMatrix[i][k]}
				}
				if(tempMap) tempList << tempMap
			}
			
			// make a JSON text
			def builder = new groovy.json.JsonBuilder()
			builder(tempList)
			
			println builder.toString()
			
			runList.add(run)
		}
		
		
		return runList
	}	

}
