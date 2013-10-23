package it.fmach.metadb.isatab.model

class FEMAssay {

	String name
	String instrument
	String description
	
	List runs
	
	static hasMany = [runs: FEMRun]
		
    static constraints = {
		description nullable: true
    }
}
