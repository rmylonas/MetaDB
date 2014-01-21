package it.fmach.metadb.services

import it.fmach.metadb.isatab.model.FEMAssay;
import it.fmach.metadb.isatab.model.FEMStudy;

class SearchService {

	def springSecurityService
	
    def searchAssays(String term, Boolean showAll) {
		
		def assays 
		
		if(!term || term == "*"){
			assays = FEMAssay.list()
		}else{
			assays = FEMAssay.searchEvery(term)
		}

		// we only return entries belonging to this user
		if(! showAll){
			// get the current user
			def currentUser = springSecurityService.getCurrentUser()
			
			// remove assays from list which are from a different user
			def assaySize = assays.size()
			
			for(def i=0; i < assaySize; i++){
				def assay = assays[i]
				// re-attach all information (was only lazy loaded)
				assay.refresh()
				if(assay.owner != currentUser){
					assaySize --
					i --
					assays.remove(assay)
				}
			}
		}
		
		return assays
    }
	
	def searchStudies(String term, Boolean showAll) {
		def studies 
		
		if(!term || term == "*"){
			studies = FEMStudy.list()
		}else{
			studies = FEMStudy.searchEvery(term)
		}

		// we only return entries belonging to this user
		if(! showAll){
			// get the current user
			def currentUser = springSecurityService.getCurrentUser()
			
			// remove studies from list which are from a different user
			def studiesSize = studies.size()
			
			for(def i=0; i < studiesSize; i++){
				def study = studies[i]
				// re-attach all information (was only lazy loaded)
				study.refresh()
				if(study.owner != currentUser){
					studiesSize --
					i --
					studies.remove(study)
				}
			}
		}
		
		return studies
	}
}
