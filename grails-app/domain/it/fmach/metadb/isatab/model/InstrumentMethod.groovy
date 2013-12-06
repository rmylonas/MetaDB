package it.fmach.metadb.isatab.model

class InstrumentMethod {

	String name
	String description
	String tag
	String startPattern
	String repeatPattern
	String endPattern
	
	Boolean randomization
	
    static constraints = {
		description nullable: true
		startPattern nullable: true
		repeatPattern nullable: true
		endPattern nullable: true
    }
	
}
