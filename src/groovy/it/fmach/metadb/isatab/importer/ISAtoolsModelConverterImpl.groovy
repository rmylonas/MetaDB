package it.fmach.metadb.isatab.importer

import it.fmach.metadb.isatab.model.FEMSample;
import it.fmach.metadb.isatab.model.FEMStudy;

import org.isatools.isacreator.model.Investigation;
import org.isatools.isacreator.model.Study;

/**
 * @author mylonasr
 *
 * Convert Objects from the isatools into our own Objects (typically from Investigation into our own Study)
 */
class ISAtoolsModelConverterImpl implements ISAtoolsModelConverter {

	static final def SAMPLE_NAME = "Sample Name"
	
	@Override
	public List<FEMStudy> convertInvestigation(Investigation iSAInvestigation) {
		
		List<FEMStudy> studyList = new ArrayList<FEMStudy>()
		
		iSAInvestigation 
		
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
	}
	
	private List<FEMSample> convertSampleList(Study iSAStudy){
		List<FEMSample> sampleList = []
		Object[][] sampleMatrix = iSAStudy.getStudySampleDataMatrix()
		
		def headerMap = [:]
		def nrColumns = sampleMatrix[0].length -1
		def nrRows = sampleMatrix.length - 1
		
		// parse the header
		for(i in 0..nrColumns){
			headerMap[sampleMatrix[0][i]] = i
		}
		
		// parse the fields of interest
		for(i in 1..nrRows){
			FEMSample sample = new FEMSample()
			sample.name = sampleMatrix[i][headerMap[SAMPLE_NAME]]
			sampleList.add(sample)
		}
		
		return sampleList

	}
	

}
