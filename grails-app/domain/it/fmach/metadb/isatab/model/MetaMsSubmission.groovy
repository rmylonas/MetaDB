package it.fmach.metadb.isatab.model

import java.util.Date;

class MetaMsSubmission {
 
	String workDir
	String status
	String command
	
	List selectedRuns
	MetaMsDb metaMsDb
	
	static hasMany = [selectedRuns:FEMRun]
		
	Date dateCreated
	
    static constraints = {
		metaMsDb nullable: true
    }
}
