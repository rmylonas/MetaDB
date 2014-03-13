package it.fmach.metadb.services

import it.fmach.metadb.isatab.model.FEMAssay;
import it.fmach.metadb.isatab.model.FEMStudy;

class SearchService {

	def springSecurityService
	
    def searchAssays(String searchTerm, Boolean showAll, Integer myOffset, Integer myMax) {
		
		def searchClosure
		
		// add the user to the search term if necessary
		if(! showAll){
			def currentUser = springSecurityService.getCurrentUser()
			
			searchClosure = {
					must(queryString(searchTerm))
					must(term('FEMAssay.owner.username', currentUser.username))
			}
		}else{
			searchClosure = {
				must(queryString(searchTerm))
			}
		}
		
		def assays
		def totalEntries
		
		if(!searchTerm || searchTerm == "*"){
			assays = FEMAssay.list(offset: myOffset, max: myMax)
			totalEntries = FEMAssay.count()
		}else{
			def answer = FEMAssay.search(searchClosure , [offset: myOffset, max: myMax, sort: "dateCreated", order: "desc"])
			
			assays = answer.results
			totalEntries = answer.total
		}
		
		return [assays, totalEntries]
		
    }
	
	def searchStudies(String searchTerm, Boolean showAll, Integer myOffset, Integer myMax) {
		
		def searchClosure
		
		// add the user to the search term if necessary
		if(! showAll){
			def currentUser = springSecurityService.getCurrentUser()
			
			searchClosure = {
					must(queryString(searchTerm))
					must(term('FEMStudy.owner.username', currentUser.username))
			}
		}else{
			searchClosure = {
				must(queryString(searchTerm))
			}
		}
		
		def studies
		def totalEntries
		
		if(!searchTerm || searchTerm == "*"){
			studies = FEMStudy.list(offset: myOffset, max: myMax)
			totalEntries = FEMStudy.count()
		}else{
			def answer = FEMStudy.search(searchClosure , [offset: myOffset, max: myMax, sort: "dateCreated", order: "desc"])
			
			studies = answer.results
			totalEntries = answer.total
		}
		
		return [studies, totalEntries]
	}
}
