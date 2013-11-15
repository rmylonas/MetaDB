package it.fmach.metadb.isatab.model

class InstrumentMethod {

	String name
	String description
	String tag
	String startPattern
	String repeatPattern
	String endPattern
	
    static constraints = {
		description nullable: true
    }
	
}
