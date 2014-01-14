package it.fmach.metadb.isatab.model

class AccessCode {

	static searchable = true
	
	String code
	
    static constraints = {
		code unique: true 
    }
}
