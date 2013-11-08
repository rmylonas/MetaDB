package it.fmach.metadb.isatab.model

class Project {

	String name
	String description
	
    static constraints = {
		description nullable: true
		name unique: true
    }
}
