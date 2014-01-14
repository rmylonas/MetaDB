package it.fmach.metadb.isatab.model

class FEMGroup {
	static searchable = [except: ['projects']]
	
	String name
	String description
	
	List projects
	
	static hasMany = [projects: FEMProject]
	
    static constraints = {
		description nullable: true
		projects nullable: true
		name unique: true
    }
	
	static mapping = {
		projects lazy: false
	}
}
