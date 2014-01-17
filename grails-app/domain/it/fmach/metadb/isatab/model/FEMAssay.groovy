package it.fmach.metadb.isatab.model

import it.fmach.metadb.User

import java.util.Date;
import java.util.List

class FEMAssay {

	// code used by the Searchable Grails plugin
	static searchable = {
		accessCode(component: true)
		shortName()
		dateCreated()
		keywords()
		owner(component: true)
		instrument(component: true)
		method(component: true)
		instrumentPolarity()
		project(component: true)
		group(component: true)
		except = ['name', 'description', 'status', 'workDir', 'protocolJSON', 'runs', 'randomizedRuns', 'acquiredRuns', 'metaMsSubmissions']
	}
	
	// the unique accessCode, used for the booking system
	AccessCode accessCode 
	
	String name
	String shortName
	String description
	Date dateCreated
	String status = "initialized"
	String workDir
	String protocolJSON = "{}"
	String keywords = ""
	
	User owner
	Instrument instrument
	InstrumentMethod method
	String instrumentPolarity
	FEMProject project
	FEMGroup group
	
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
		group nullable: true
		project nullable: true
    }
	
//	static mapping = {
//		acquiredRuns cascade: "all-delete-orphan"
//	}
	
	static mapping = {
		accessCode lazy: false
		metaMsSubmissions lazy: false
		// acquiredRuns lazy: false
		// randomizedRuns lazy: false
		protocolJSON sqlType: 'text'
		keywords sqlType: 'text'
		// show most recent ones first
		sort id: "desc"
	}
	
}
