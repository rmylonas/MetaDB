package it.fmach.metadb.isatab.importer

import it.fmach.metadb.isatab.model.FEMStudy;

import org.isatools.isacreator.model.Investigation;
import org.isatools.isacreator.model.Study;

/**
 * @author mylonasr
 *
 * Convert Objects from the isatools into our own Objects (typically from Investigation into our own Study)
 */
class ISAtoolsModelConverterImpl implements ISAtoolsModelConverter {

	@Override
	public List<FEMStudy> convertInvestigation(Investigation iSAInvestigation) {
		
		List<FEMStudy> studyList = new ArrayList<FEMStudy>()
		
		iSAInvestigation
		
		// loop through all studies
		Map<String, Study> studyMap = iSAInvestigation.getStudies()
		for(String studyName : studyMap.keySet()){
			FEMStudy study = new FEMStudy()
			
			study.identifier = studyName
			convertStudy(studyMap.get(studyName), study)
	
			studyList.add(study)
		}
		
		return studyList
		
		
	}

}
