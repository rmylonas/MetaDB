package it.fmach.metadb.isatab.model

class Group {

	String name
	String description
	
	List projects
	
	def hasMany = [projects: Project]
	
    static constraints = {
		description nullable: true
		name unique: true
    }
}
