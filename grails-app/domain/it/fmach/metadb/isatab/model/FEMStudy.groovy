package it.fmach.metadb.isatab.model

import it.fmach.metadb.User

class FEMStudy {
	
	// code used by the Searchable Grails plugin
	static searchable = {
		identifier()
		title()
		keywords()
		dateCreated()
		owner(component: true)
		project(component: true)
		group(component: true)
		except = ['description', 'workDir', 'originalFilename', 'assays']
	}
		
	String identifier
	String title = ""
	String description = ""
//	String designDescriptors = ""
	String iSATabFilePath
	String originalFilename = ""
	String workDir
	String keywords = ""
	
	Date dateCreated
	
	List assays
	
	User owner
	FEMProject project
	FEMGroup group
	
	static hasMany = [assays:FEMAssay]
	
	// List of Publications
	// List of Factors
	// List of Protocols
	// List of Contacts
	
    static constraints = {
		group nullable: true
		project nullable: true
		workDir nullable: true
    }
	
	static mapping = {
		description sqlType: 'text'
		keywords sqlType: 'text'
		// show most recent ones first
		sort id: "desc"
	}
	
}
