package it.fmach.metadb.isatab.controller

import it.fmach.metadb.isatab.model.FEMAssay;
import it.fmach.metadb.isatab.model.FEMRun;

class SearchController {
	def searchService
	
    def index() {
		def term = params['searchTerm']
		def level = params['level']
		
		// show entries of all users?
		def showAllEntries = (params['showAll']) ? (true) : (false)
		
		// reset warning
		flash.warning = null
		
		// if a search term is provided..
		if(term){
			switch(level){
			
				case "Assay":
					flash.studies = null	
				
					def assays = searchService.searchAssays(term, showAllEntries)
					
					if(assays.size() > 0){
						assays.each {it.refresh()}
						flash.assays = assays
					}else{
						flash.assays = []
						flash.warning = "Sorry, no matching assays found"
					}
					break

								
				case "Study":
					flash.assays = null
				
					def studies = searchService.searchStudies(term, showAllEntries)
					
					if(studies.size() > 0){
						// re-attach all information (was only lazy loaded)
						studies.each {it.refresh()}
						flash.studies = studies
					}else{
						flash.studies = []
						flash.warning = "Sorry, no matching studies found"
					}
					break
			}
			
		}
		
		render(view:"index")
	}
	
}
