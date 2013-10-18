package it.fmach.metadb.isatab.model

class FEMStudy {

	String identifier
	String title
	String description
	String designDescriptors
	String iSATabFilePath
	
	Date creationDate
	Date lastModificationDate
	
	List samples
	List assays
	
	static hasMany = [samples: FEMSample, assays:FEMAssay]
	
	// List of Publications
	// List of Factors
	// List of Protocols
	// List of Contacts
	
    static constraints = {
		
    }
}
