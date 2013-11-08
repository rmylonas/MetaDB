package it.fmach.metadb.isatab.model

class InstrumentMethod {

	String name
	String description
	
	List polarities
	
	static hasMany = [polarities: InstrumentPolarity]
	
    static constraints = {
		description nullable: true
    }
	
}
