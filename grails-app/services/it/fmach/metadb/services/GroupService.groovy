package it.fmach.metadb.services

import it.fmach.metadb.isatab.model.FEMGroup
import it.fmach.metadb.isatab.model.FEMProject;
import it.fmach.metadb.isatab.model.FEMStudy

class GroupService {

    def delete(FEMGroup group) {
		// let's check if this group is used by some studies
		def studyList = FEMStudy.findByGroup(group)
		
		if(studyList) throw new RuntimeException("Group [" + group.name + "] is used by some Studies. Please delete those Studies first.")
		
		// delete all the projects first
		group.projects.each{
			it.delete()
		}
		
		group.delete()
    }
	
	def deleteProject(FEMGroup group, FEMProject project) {
		// let's complaine if the project is used by some studies
		def studyList = FEMStudy.findByProject(project)
		if(studyList) throw new RuntimeException("Project [" + project.name + "] is used by some Studies. Please delete those Studies first.")
		
		// and delete
		group.removeFromProjects(project)
		group.merge()
		group.save(flush: true, failOnError: true)
		project.delete()
		
		
	}
	
}
