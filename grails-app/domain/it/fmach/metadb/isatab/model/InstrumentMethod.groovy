package it.fmach.metadb.isatab.model

class InstrumentMethod {

	String name
	String description
	String tag
	String startPattern
	String repeatPattern
	String endPattern
	
	// 
	MetaMsDb metaMsDb
	String metaMsParameterFile
	
	Boolean randomization
	
	// static belongsTo = [instrument:Instrument]
	
    static constraints = {
		description nullable: true
		startPattern nullable: true
		repeatPattern nullable: true
		endPattern nullable: true
		metaMsDb nullable: true
		metaMsParameterFile nullable: true
    }
	
}
