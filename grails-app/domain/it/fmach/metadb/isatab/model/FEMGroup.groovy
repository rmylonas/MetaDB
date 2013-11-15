package it.fmach.metadb.isatab.model

class FEMGroup {

	String name
	String description
	
	List projects
	
	static hasMany = [projects: FEMProject]
	
    static constraints = {
		description nullable: true
		name unique: true
    }
}
