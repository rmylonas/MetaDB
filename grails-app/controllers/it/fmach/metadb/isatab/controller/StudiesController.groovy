package it.fmach.metadb.isatab.controller

import it.fmach.metadb.isatab.model.FEMStudy

class StudiesController {
	
	def springSecurityService

    def allStudies() { 
		flash.studies = FEMStudy.list()
		render(view:"index")
	}
	
	def myStudies() {
		def currentUser = springSecurityService.getCurrentUser()
		flash.studies = FEMStudy.findAllByOwner(currentUser)
		render(view:"index")
	}
	
}
