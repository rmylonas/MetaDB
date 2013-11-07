package it.fmach.metadb.isatab.model

class FEMStudy {

	String identifier
	String title = ""
	String description = ""
//	String designDescriptors = ""
	String iSATabFilePath
	
	Date dateCreated
	
	List assays
	
	static hasMany = [assays:FEMAssay]
	
	// List of Publications
	// List of Factors
	// List of Protocols
	// List of Contacts
	
    static constraints = {
		
    }
	
	static mapping = {
		description sqlType: 'text'
	}
	
}
