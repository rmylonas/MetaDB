package it.fmach.metadb.onthology

class Organism {

	String name
	String alternativeNames
	String description
	
	// code used by the Searchable Grails plugin
	static searchable = {
		name()
		alternativeNames()
	}
	
    static constraints = {
		name unique: true
		description nullable: true
    }
}
