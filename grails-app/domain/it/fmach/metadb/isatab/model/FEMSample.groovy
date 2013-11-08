package it.fmach.metadb.isatab.model

class FEMSample {
	
	String name
	String sourceName = ""
	String organism = ""
	String organismPart = ""
	String factorJSON = "{}"
	
	//static belongsTo = [fEMRun: FEMRun]
	
    static constraints = {
		factorJSON sqlType: 'text'
    }
	
}
