package it.fmach.metadb.isatab.controller

import it.fmach.metadb.instrument.export.ExportCsv
import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.workflow.acquisition.AcquiredNamesInserter

class RunsController {
	
	def acquiredNamesInserter = new AcquiredNamesInserter()
	
    def index() { 
		String assayId = params['id']
		
		// if a assay id is provided we set the assay to the session
		if(assayId){
			def assay = FEMAssay.get(assayId.toLong())
			session.assay = assay
		}
		
		// if no assayId is given, we'll check if there is already an assay loaded
		if(session.assay){
			switch(session.assay.status){
				case "randomized":
					flash.runs = session.assay.randomizedRuns
					break
				
				case "acquired":
					flash.runs = session.assay.acquiredRuns
			}
			render(view: "index")
		}
	}
	
	def downloadCsv(){	
		def assay = session.assay
		if(assay == null){
			flash.error = "No assay is selected"	
		}
		
		def exporter = new ExportCsv()
		def csvString = exporter.exportRandomizedRuns(assay)
		
		// hand a csv file to the browser
		response.setHeader "Content-disposition", "attachment; filename=${assay.name}.csv"
		response.contentType = 'text/csv'
		response.outputStream << csvString
		response.outputStream.flush()
	}
	
	def assayNames() {}
	
	
	def uploadAssayNames() {
		if(session.assay){
			def assayNames = params["assayNames"]
			List<String> assayNameList = assayNames.split("\n")
			
			// reload the assay (to get the whole structure)
			def assay = FEMAssay.get(session.assay.id)
			
			// add acquiredRuns to assay
			def missingNames = acquiredNamesInserter.addAcquiredAssayNames(assay, assayNameList)
			
			// flash a warning, if names were missing
			if(missingNames){
				def missingN = missingNames[0]
				def notFoundN = missingNames[1]
				
				if(missingN){
					flash.warning = "Following names were missing: <ul>"
					
					missingN.each{
						flash.warning += "<li>" + it + "</it>"
					}
					
					flash.warning += "</ul>"
				}
				
				if(notFoundN){
					flash.warning += "Following names were not found in database: <ul>"
					
					notFoundN.each{
						flash.warning += "<li>" + it + "</it>"
					}
					
					flash.warning += "</ul>"
				}
				
			}
			
			// update the assay			
			assay.save(flush: true)
			
			// set the runs into flash
			flash.runs = assay.acquiredRuns
			
		}else{
			flash.error = "No assay is selected"
		}	
		
		render(view: "index")
	}
	
	
	def chooseExtracted(){}
	
	def uploadExtracted(){
		// upload the file
		def f = request.getFile('extractedFile')
		
		if (f.empty) {
			flash.error = 'Uploaded zip file is empty'
			redirect(action: 'index')
			return
		}else{
			importFile = File.createTempFile("extracted_",".zip")
			f.transferTo(importFile)
			
			// and process it
			try{
				investigation = importer.importIsatabZip(importFile.absolutePath)
				
				if(investigation.isaParsingInfo.success){
					flash.message = ('ISAtab file was succesfully processed')
				}else{
					flash.error = 'Parsing Error: sorry, your ISAtab file could not be parsed'
					redirect(action: 'index')
					return
				}
				
			}catch(e){
				e.printStackTrace()
				flash.error = 'Exception occured: sorry, your ZIP file could not be added'
				redirect(action: 'index')
				return
			}
		}
		
		redirect(action: 'index')
	}	
	
}
