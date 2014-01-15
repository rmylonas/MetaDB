package it.fmach.metadb.isatab.importer

import it.fmach.metadb.isatab.model.FEMStudy;
import it.fmach.metadb.isatab.model.ISAParsingInfo;

/**
 * @author mylonasr
 * 
 * A simple POJO, tacking a list of Studies and information about errors. Alternatively we could have implemented
 * Trhowable or return null if there's an error. Or we could use a Map structure using def.
 *
 */
class FEMInvestigation { 
	List<FEMStudy> studyList
	ISAParsingInfo isaParsingInfo
}
