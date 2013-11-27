package it.fmach.metadb.isatab.model


class FEMRun {
	
	Integer rowNumber
	
	//String sampleName
	String protocolJSON = "{}"
	String msAssayName = ""
	String rawSpectraFilePath = ""
	String derivedSpectraFilePath = ""
	String status = "initialized"
	String scanPolarity
	
	Date lastChange = new Date()

	FEMSample sample
	
	// static belongsTo = [fEMAssay: FEMAssay]
	
    static constraints = {
    }
	
	static mapping = {
		protocolJSON sqlType: 'text'
	}
	
}
