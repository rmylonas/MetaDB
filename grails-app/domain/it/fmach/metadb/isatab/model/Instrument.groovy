package it.fmach.metadb.isatab.model

class Instrument {
	
	String name
	String metabolightsName
	String bookingSystemName
	String chromatography // either GC or LC
	
	String polarities // polarities separated by comma
	
	List methods
	
	static hasMany = [methods: InstrumentMethod]
	
    static constraints = {
		bookingSystemName nullable: true
		polarities nullable: true
		name unique: true
		metabolightsName unique: true
    }
	
	static mapping = {
		methods lazy: false
	}
}
