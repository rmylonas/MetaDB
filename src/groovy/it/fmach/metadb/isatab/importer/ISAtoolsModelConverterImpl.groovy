package it.fmach.metadb.isatab.importer

import it.fmach.metadb.User
import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.isatab.model.FEMSample
import it.fmach.metadb.isatab.model.FEMStudy
import it.fmach.metadb.isatab.model.Instrument

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
	def instrumentMap = [:]
	def protocolJSON
	
	String workDir
	User currentUser
	
	// field-names which are parsed
	static final def SAMPLE_NAME = "Sample Name"
	static final def SAMPLE_ORGANISM = "Characteristics[Organism]"
	static final def SAMPLE_ORGANISM_PART = "Characteristics[Organism part]"
	static final def SAMPLE_SOURCE_NAME = "Source Name"
	static final def RUN_MS_ASSAY_NAME = "MS Assay Name"
	static final def RUN_RAW_FILE = "Raw Spectral Data File"
	static final def RUN_DERIVED_FILE= "Derived Spectral Data File"
	static final def RUN_SCAN_POLARITY= "Parameter Value[Scan polarity]"
	
	ISAtoolsModelConverterImpl(String workDir, User currentUser){
		// create map of available instruments and polarities
		Instrument.list().each {inst->
			instrumentMap[inst.metabolightsName] = inst
		}
		
		this.workDir = workDir
		this.currentUser = currentUser
	}
	
	@Override
	public List<FEMStudy> convertInvestigation(Investigation iSAInvestigation) {
		
		List<FEMStudy> studyList = new ArrayList<FEMStudy>() 
		
		// loop through all studies
		Map<String, Study> studyMap = iSAInvestigation.getStudies()
		for(String studyName : studyMap.keySet()){
			FEMStudy fEMStudy = new FEMStudy()
			
			fEMStudy.identifier = studyName.trim()
			this.convertStudy(studyMap.get(studyName), fEMStudy)
	
			studyList.add(fEMStudy)
		}
		
		return studyList
		
	}
	
	private void convertStudy(Study iSAStudy, FEMStudy fEMStudy){
		fEMStudy.title = iSAStudy.getStudyTitle().trim()
		fEMStudy.description = iSAStudy.getStudyDesc().trim()
		
		// set the workingDir
		fEMStudy.workDir = currentUser.workDir + "/data/" + fEMStudy.identifier.replaceAll(/\s+/, '_')
		
		 Map<String, FEMSample> sampleList = this.convertSampleList(iSAStudy)
		
		List<FEMAssay> assayList = this.convertAssayList(iSAStudy, sampleList)
		fEMStudy.assays = assayList
		
		// set the owner
		fEMStudy.owner = this.currentUser
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
			sample.name = sampleMatrix[i][headerMap[SAMPLE_NAME]].trim()
			sample.sourceName = sampleMatrix[i][headerMap[SAMPLE_SOURCE_NAME]].trim()
			sample.organism = sampleMatrix[i][headerMap[SAMPLE_ORGANISM]].trim()
			sample.organismPart = sampleMatrix[i][headerMap[SAMPLE_ORGANISM_PART]].trim()
			
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
			
			// reset the protocolJSON
			this.protocolJSON = null
			
			def assay = new FEMAssay()
			assay.name = k.trim()
			assay.shortName = createShortAssayName(k)
			
			// select the instrument
			def instrument = this.instrumentMap[v.getAssayPlatform()]
			if(instrument == null){throw new RuntimeException("instrument [" + v.getAssayPlatform() + "] is not available")}
			assay.instrument = instrument
			
			assay.runs = convertRunList(v, sampleList)
			
			// after extracting the runs, we should have a protocolJSON
			assay.protocolJSON = this.protocolJSON
			
			// set the workDir of this assay
			def studyDir = iSAStudy.getStudyTitle().trim().replaceAll(/\s+/, '_')
			def assayDir = assay.shortName.replaceAll(/\s+/, '_')
			assay.workDir = currentUser.workDir + "/data/" + studyDir + "/" + assayDir
			
			//select the polarity
			def polarity = assay.runs[0].scanPolarity
			if(polarity == null){throw new RuntimeException("polarity [" + polarity + "] has to be defined")}
			assay.instrumentPolarity = polarity
			
			// and for the method we just take the first available
			assay.method = instrument.methods[0]
			
			// set the owner
			assay.owner = this.currentUser
			
			assay.accessCode = accessCodeGenerator.getNewCode()
			assayList << assay
		}
		
		return assayList
	}
	
	private String createShortAssayName(String name){
		def matcher = name =~ /a_(.+)_metabolite profiling_mass spectrometry-?(.*)\.txt/
		
		// return the complete name, if there is a matcher
		if(! matcher) return name
		
		def end = (matcher[0][2] != '')?("-" + matcher[0][2]):('')
		def shortName = matcher[0][1] + end
		
		return shortName
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
			def sampleName = assayMatrix[i][headerMap[SAMPLE_NAME]].trim()
			run.sample = sampleList[sampleName]
			run.msAssayName = assayMatrix[i][headerMap[RUN_MS_ASSAY_NAME]].trim()
			run.rawSpectraFilePath = assayMatrix[i][headerMap[RUN_RAW_FILE]].trim()
			run.derivedSpectraFilePath = assayMatrix[i][headerMap[RUN_DERIVED_FILE]].trim()
			run.scanPolarity = assayMatrix[i][headerMap[RUN_SCAN_POLARITY]].trim()
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
			
			// make a JSON text and put it into protocolJSON to store it afterwards in the assay
			def builder = new groovy.json.JsonBuilder()
			builder(tempList)
			if(! this.protocolJSON) this.protocolJSON = builder.toString()
			
			runList.add(run)
		}
		
		return runList
	}	

}
