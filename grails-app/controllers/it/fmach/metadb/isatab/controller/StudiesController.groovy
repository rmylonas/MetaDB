package it.fmach.metadb.isatab.controller

import it.fmach.metadb.isatab.model.FEMStudy

class StudiesController {
	
	def springSecurityService
	def studyService

	def delete(){
		def study = FEMStudy.get(params.id)
		
		// remove the selected entry from the session
		def i = session.studies.iterator()
		while (i.hasNext()) {
			if (i.next().id == study.id) {
				i.remove()
				break
			}
		}
		
		// delete selected study
		try{
			studyService.delete(study)
		}catch(Exception e){
			e.printStackTrace()
			flash.error = e.message
			render(view:"index")
			return
		}
		
		flash.message = "Study was deleted"
		render(view:"index")
	}
	
    def allStudies() { 
		session.studies = FEMStudy.list()
		render(view:"index")
	}
	
	def myStudies() {
		def currentUser = springSecurityService.getCurrentUser()
		session.studies = FEMStudy.findAllByOwner(currentUser)
		render(view:"index")
	}
	
}
