package it.fmach.metadb.isatab.model

import java.util.Date;
import java.util.List;

class FEMAssay {

	// the unique accessCode, used for the booking system
	AccessCode accessCode 
	
	String name
	String instrument
	String description
	
	Date dateCreated
	
	List samples
	List runs
	
	static hasMany = [samples: FEMSample, runs: FEMRun]
		
    static constraints = {
		description nullable: true
		accessCode unique: true
    }
}
