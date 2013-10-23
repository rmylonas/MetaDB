package it.fmach.metadb.isatab.model

class FEMRun {
	
	Integer rowNumber
	
	String sampleName
	String protocols
	String msAssayName
	String rawSpectraFilePath
	String derivedSpectraFilePath
	String status = "initialized"
	
	Date lastChange = new Date()

    static constraints = {
    }
	
}
