package it.fmach.metadb.onthology

import it.fmach.metadb.helper.XmlConverter

class OrganismController {

    def index() { 
		session.organism = Organism.list()
	}
	
	def newOrganism(){
		flash.newOrganism = true
		render(view: "detail")
	}
	
	def editOrganism(){
		// load this instrument if params.id is provided
		session.organism = Organism.get(params.id)
		render(view: "detail")
	}
	
	def updateOrganism(){
		// reload organism
		def organism = session.organism
		organism.refresh()
		
		organism.name = params['name']
		organism.alternativeNames = params['alternativeNames']
		organism.description = params['description']
		
		try{
			organism.save(flush: true, failOnError: true)
		}catch(Exception e){
			e.printStackTrace()
			flash.error = e.message
		}
		
		if(! flash.error)flash.message = "Organism was updated"
		redirect(view: 'index')
	}
	
	def saveOrganism(){
		def organism = new Organism(name: params['name'], alternativeNames: params['alternativeNames'], description: params['description'])
		
		try{
			organism.save(flush: true, failOnError: true)
		}catch(Exception e){
			e.printStackTrace()
			flash.error = e.message
		}
		
		if(! flash.error)flash.message = "Organism was updated"
		redirect(view: 'index')
	}
	
	def deleteOrganism(){
		def organism = Organism.get(params.id)
		organism.delete()
		flash.message = "Organism was deleted"
		redirect(view: 'index')
	}
	
	def search(){
		def term = params['term']
		
		def organismList = Organism.searchEvery(term)
		
		def xmlConverter = new XmlConverter()
		def organismXml = xmlConverter.organismToXml(organismList)
		
		render(text: organismXml, contentType: "text/xml", encoding: "UTF-8")
	}
	
	
}
