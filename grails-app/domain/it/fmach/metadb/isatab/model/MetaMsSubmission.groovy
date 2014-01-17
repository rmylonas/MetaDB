package it.fmach.metadb.isatab.model

import java.util.Date;

class MetaMsSubmission {
 
	String name
	String workDir
	String status
	String command
	String comment
	
	Double rtMin
	Double rtMax
	
	List selectedRuns
	MetaMsDb metaMsDb
	
	static hasMany = [selectedRuns:FEMRun]
		
	Date dateCreated
	
    static constraints = {
		metaMsDb nullable: true
		rtMin nullable: true
		rtMax nullable: true
		comment nullable: true
    }
}
