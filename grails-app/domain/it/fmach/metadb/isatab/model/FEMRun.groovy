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
	
	List additionalRuns
	
	static hasMany = [additionalRuns: FEMRun]
	
	// static belongsTo = [fEMAssay: FEMAssay]
	
    static constraints = {
		additionalRuns nullable: true
    }
	
	static mapping = {
		
	}
	
}
