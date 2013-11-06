package it.fmach.metadb.isatab.model

class FEMAssay {

	// the unique accessCode, used for the booking system
	AccessCode accessCode 
	
	String name
	String instrument
	String description
	
	List runs
	
	static hasMany = [runs: FEMRun]
		
    static constraints = {
		description nullable: true
		accessCode unique: true
    }
}
