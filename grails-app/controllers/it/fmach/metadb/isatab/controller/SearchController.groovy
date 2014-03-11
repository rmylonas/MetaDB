package it.fmach.metadb.isatab.controller

import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMStudy
import it.fmach.metadb.isatab.model.FEMRun
import javax.servlet.http.Cookie

class SearchController {
	def searchService
	def springSecurityService
	
    def index() {
		def term = params['searchTerm']
		def level = params['level']
		
		// if there is no term and level defined, we load all studies of this user
		if(! term && ! level){
			def currentUser = springSecurityService.getCurrentUser()
			session.studies = FEMStudy.findAllByOwner(currentUser)
			session.assays = null
			render(view:"index")
			return
		}
			
		// show entries of all users?
		def showAllEntries = (params['showAll']) ? (true) : (false)
		
		// reset warning
		flash.warning = null
		
		switch(level){
	
			case "Assay":
				session.studies = null	
			
				def assays = searchService.searchAssays(term, showAllEntries)
				
				if(assays.size() > 0){
					assays.each {it.refresh()}
					session.assays = assays
				}else{
					session.assays = []
					flash.warning = "Sorry, no matching assays found"
				}
				break
	
							
			case "Study":
				session.assays = null
			
				def studies = searchService.searchStudies(term, showAllEntries)
				
				if(studies.size() > 0){
					// re-attach all information (was only lazy loaded)
					studies.each {it.refresh()}
					session.studies = studies
				}else{
					session.studies = []
					flash.warning = "Sorry, no matching studies found"
				}
				break
		}
		
		
/*		// set the cookies
		def showAllCookieValue = (params['showAll']) ? ("checked") : ("")
		
		// study as default selected?
		def studySelected = (params['level'] == "Study") ? ("selected") : ("")
		
		// save cookies (for 100 days)
		Cookie levelCookie = new Cookie("studySelected", studySelected)
		
		levelCookie.maxAge = 100 * 24 * 60 * 60
		response.addCookie(levelCookie)
		Cookie showAllCookie = new Cookie("showAll", showAllCookieValue)
		showAllCookie.maxAge = 100 * 24 * 60 * 60
		response.addCookie(showAllCookie)*/
		
		render(view:"index")
	}
	
}
