package it.fmach.metadb.isatab.model

class Instrument {
	
	String name
	String metabolightsName
	String bookingSystemName
	
	List methods
	static hasMany = [methods: InstrumentMethod]

    static constraints = {
		bookingSystemName nullable: true
		name unique: true
		metabolightsName unique: true
    }
}
