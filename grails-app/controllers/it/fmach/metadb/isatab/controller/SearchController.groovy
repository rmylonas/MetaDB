package it.fmach.metadb.isatab.controller

import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMStudy
import it.fmach.metadb.isatab.model.FEMRun
import javax.servlet.http.Cookie

class SearchController {
	def searchService
	def springSecurityService
	
    def index() {
		def showEntriesPerPage = grailsApplication.config.metadb.showEntriesPerPage
		def myOffset = (params.offset) ? (params.offset as Integer) : (0)
		def myMax = (params.max) ? (params.max as Integer) : (showEntriesPerPage)
		
		def term = params['searchTerm']
		def level = params['level']
		
		// if there is no term and level defined, we load all studies of this user
		if(! term && ! level){
			def currentUser = springSecurityService.getCurrentUser()
			session.studies = FEMStudy.findAllByOwner(currentUser, [offset: myOffset, max: myMax])
			session.totalEntries = FEMStudy.countByOwner(currentUser)
			
			session.assays = null
			flash.lastSearchTerm = null
			flash.lastLevel = null
			flash.lastShowAll = null
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
			
				def (assays, totalEntries) = searchService.searchAssays(term, showAllEntries, myOffset, myMax)
				
				if(assays.size() > 0){
					assays.each {it.refresh()}
					session.assays = assays
					session.totalEntries = totalEntries
				}else{
					session.assays = []
					session.totalEntries = 0
					flash.warning = "Sorry, no matching assays found"
				}
				break
	
							
			case "Study":
				session.assays = null
			
				def (studies, totalEntries) = searchService.searchStudies(term, showAllEntries, myOffset, myMax)
				
				if(studies.size() > 0){
					// re-attach all information (was only lazy loaded)
					studies.each {it.refresh()}
					session.studies = studies
					session.totalEntries = totalEntries
				}else{
					session.studies = []
					session.totalEntries = 0
					flash.warning = "Sorry, no matching studies found"
				}
				break
		}	
		
		// cookies were removed: They were always one step behind (since the page reloads itself faster, 
		// 	than the cookies are stored)
		
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
		response.addCookie(showAllCookie)	*/
		
		// set the lastSearchTerm to make correct pagination function
		flash.lastSearchTerm = term
		flash.lastLevel = level
		flash.lastShowAll = params['showAll']
		
		render(view:"index")
	}
	
}
