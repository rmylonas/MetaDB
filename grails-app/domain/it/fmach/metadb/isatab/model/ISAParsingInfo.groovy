package it.fmach.metadb.isatab.model

/**
 * @author mylonasr
 *
 *	Contains the parsing info.
 *	I assume, that if it's validated, it was parsed successfully.
 *	While for the ISAtab creation, those are two completely different steps, we can consider
 *	that they both (validation and parsing) are part of the loading process. 
 *	
 */
class ISAParsingInfo {

	Boolean success
	String status
	String errorMessage = ""
	Integer nrOfErrors = 0
	
    static constraints = {
		status nullable: true
    }
}
