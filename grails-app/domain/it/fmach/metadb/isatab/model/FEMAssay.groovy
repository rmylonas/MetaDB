package it.fmach.metadb.isatab.model

import java.util.Date;
import java.util.List;

class FEMAssay {

	// the unique accessCode, used for the booking system
	AccessCode accessCode 
	
	String name
	String shortName
	String description
	Date dateCreated
	
	Instrument instrument
	InstrumentMethod method
	String instrumentPolarity
	
	List runs
	List randomizedRuns
	List acquiredRuns
	
	static hasMany = [runs: FEMRun, randomizedRuns: FEMRun]
		
    static constraints = {
		description nullable: true
		accessCode unique: true
		randomizedRuns nullable: true
		acquiredRuns nullable:true
    }
	
}
