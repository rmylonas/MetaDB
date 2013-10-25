package it.fmach.metadb.isatab.importer

import it.fmach.metadb.isatab.model.FEMStudy;
import it.fmach.metadb.isatab.model.ISAParsingInfo;

/**
 * @author mylonasr
 * 
 * A simple POJO, tacking a list of Studies and information about errors. Alternatively we could have implemented
 * Trhowable or return null if there's an error.
 * I just read, that in Scala, functions can give back complex constructs.
 *
 */
class FEMInvestigation { 
	List<FEMStudy> studyList
	ISAParsingInfo isaParsingInfo
}
