package it.fmach.metadb.isatab.model


class FEMRun {
		
	Integer rowNumber
	
	//String sampleName
	String msAssayName = ""
	String rawSpectraFilePath = ""
	String derivedSpectraFilePath = ""
	String status = "initialized"
	String scanPolarity
	
	Date lastChange = new Date()

	FEMSample sample
	
	// is this run already represented by a sample?
	Boolean additionalRun
	
	// static hasMany = [additionalRuns: FEMRun]
	// static belongsTo = [fEMAssay: FEMAssay]
	
    static constraints = {
		// additionalRuns nullable: true
		additionalRun nullable: true
    }
	
	static mapping = {
		
	}
	
}
