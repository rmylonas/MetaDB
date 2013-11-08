package it.fmach.metadb.isatab.model

import java.util.Date;
import java.util.List;

class FEMAssay {

	// the unique accessCode, used for the booking system
	AccessCode accessCode 
	
	String name
	String description
	Date dateCreated
	
	Instrument selectedInstrument
	InstrumentMethod selectedMethod
	InstrumentPolarity selectedMode
	
	List runs
	List randomizedRuns
	
	static hasMany = [runs: FEMRun, randomizedRuns: FEMRun]
		
    static constraints = {
		description nullable: true
		accessCode unique: true
		randomizedRuns nullable: true
    }
	
}
