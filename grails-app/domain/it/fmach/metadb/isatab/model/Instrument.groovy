package it.fmach.metadb.isatab.model

class Instrument {
	
	String name
	String metabolightsName
	String bookingSystemName
	String chromatography // either GC or LC
	
	List methods
	List polarities
	static hasMany = [methods: InstrumentMethod, polarities: String]
	
	
    static constraints = {
		bookingSystemName nullable: true
		name unique: true
		metabolightsName unique: true
    }
	
	static mapping = {
		methods lazy: false
	}
}
