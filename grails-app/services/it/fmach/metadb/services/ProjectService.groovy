package it.fmach.metadb.services

import it.fmach.metadb.isatab.model.FEMProject
import it.fmach.metadb.isatab.model.FEMStudy

class ProjectService {

	   	def delete(FEMProject project) {
		// let's first load the corresponding Group
		def studyList = FEMStudy.findByProject(project)
		
		if(studyList) throw new RuntimeException("Project [" + project.name + "] is used by some Studies. Please delete those Studies first.")
		
		project.delete()
    }
}
