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
	String status = "initialized"
	String workDir
	String protocolJSON = "{}"
	
	Instrument instrument
	InstrumentMethod method
	String instrumentPolarity
	
	List runs
	List randomizedRuns
	List acquiredRuns
	List metaMsSubmissions
	
	static hasMany = [runs: FEMRun, randomizedRuns: FEMRun, acquiredRuns: FEMRun, metaMsSubmissions: MetaMsSubmission]
		
    static constraints = {
		description nullable: true
		accessCode unique: true
		randomizedRuns nullable: true
		acquiredRuns nullable: true
		workDir nullable: true
		instrumentPolarity nullable: true
		metaMsSubmissions nullable: true
    }
	
//	static mapping = {
//		acquiredRuns cascade: "all-delete-orphan"
//	}
	
	static mapping = {
		metaMsSubmissions lazy: false
		// acquiredRuns lazy: false
		// randomizedRuns lazy: false
		protocolJSON sqlType: 'text'
	}
	
}
