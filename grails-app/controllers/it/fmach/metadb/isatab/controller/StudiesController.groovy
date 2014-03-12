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
		def showEntriesPerPage = grailsApplication.config.metadb.showEntriesPerPage
		def myOffset = (params.offset) ? (params.offset) : (0)
		def myMax = (params.max) ? (params.max) : (showEntriesPerPage)
		
		session.studies = FEMStudy.list(offset: myOffset, max: myMax)
		session.totalEntries = FEMStudy.count()
		render(view:"index")
	}
	
	def myStudies() {
		def showEntriesPerPage = grailsApplication.config.metadb.showEntriesPerPage
		def myOffset = (params.offset) ? (params.offset) : (0)
		def myMax = (params.max) ? (params.max) : (showEntriesPerPage)
		def currentUser = springSecurityService.getCurrentUser()
		
		session.studies = FEMStudy.findAllByOwner(currentUser, [offset: myOffset, max: myMax])
		session.totalEntries = FEMStudy.countByOwner(currentUser)
		render(view:"index")
	}
	
}
