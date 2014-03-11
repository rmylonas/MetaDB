package it.fmach.metadb.isatab.controller

import it.fmach.metadb.isatab.model.FEMAssay;
import it.fmach.metadb.isatab.model.FEMStudy;

class AssaysController {
	
	def springSecurityService
	def assayService

    def index() {
		String studyId = params['id']
		
		// if a study id is provided we load only the concerning assays
		if(studyId){
			def study = FEMStudy.get(studyId.toLong())
			session.assays = study.assays
		}else{
			session.assays = FEMAssay.list()
		}
	}
	
	def allAssays() {
		session.assays = FEMAssay.list()
		render(view:"index")
	}
	
	def myAssays() {
		def currentUser = springSecurityService.getCurrentUser()
		session.assays = FEMAssay.findAllByOwner(currentUser)
		render(view:"index")
	}
	
	def delete(){
		def assay = FEMAssay.get(params.id)
		
		// remove the selected entry from the session
		def i = session.assays.iterator()
		while (i.hasNext()) {
			if (i.next().id == assay.id) {
				i.remove()
				break
			}
		}
		
		// delete selected assay
		try{
			assayService.delete(assay)
		}catch(Exception e){
			e.printStackTrace()
			flash.error = e.message
			redirect(uri: request.getHeader('referer') )
			return
		}
		
		flash.message = "Assay was deleted"
		redirect(uri: request.getHeader('referer') )
	}
	
}
