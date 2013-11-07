package it.fmach.metadb.isatab.model

class FEMRun {
	
	Integer rowNumber
	
	String sampleName
	String protocolJSON = "{}"
	String msAssayName = ""
	String rawSpectraFilePath = ""
	String derivedSpectraFilePath = ""
	String status = "initialized"
	
	Date lastChange = new Date()

    static constraints = {		
    }
	
	static mapping = {
		protocolJSON sqlType: 'text'
	}
	
}
