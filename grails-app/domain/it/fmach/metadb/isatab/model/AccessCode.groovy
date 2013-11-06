package it.fmach.metadb.isatab.model

class AccessCode {

	String code
	
    static constraints = {
		code unique: true 
    }
}
