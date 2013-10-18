package it.fmach.metadb.isatab.model

class FEMAssay {

	String name
	String instrument
	String description
	
	Date creationDate
	
	List runs
	
	static hasMany = [runs: FEMRun]
		
    static constraints = {
    }
}
