package it.fmach.metadb.isatab.controller

import it.fmach.metadb.instrument.export.ExportCsv
import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.workflow.acquisition.AcquiredNamesInserter
import it.fmach.metadb.workflow.extraction.ExtractedFileInserter


class RunsController {
	
	def assayService
	def acquiredNamesInserter = new AcquiredNamesInserter()
	def extractedFileInserter = new ExtractedFileInserter()
	
	
	def index(){
		// if a assay id is provided we set the assay to the session
		String assayId = params['id']
		
		def assay
		
		if(assayId){
			assay = FEMAssay.get(assayId.toLong())
			session.assay = assay
		}
		
		if(assay?.status == "acquired"){
			redirect(action: 'acquired')
		}else{
			redirect(action: 'randomized')
		}
	}
	
	
    def randomized() { 
		String assayId = params['id']
		
		// if no assayId is given, we'll check if there is already an assay loaded
		if(session.assay){
			def assay = session.assay
			assay.attach()
			session.runs = assay.randomizedRuns
		}else{
			flash.error = "No assay is selected"
		}
			
		render(view: "randomized")
	}


	def acquired(){		
		String assayId = params['id']
		
		// if no assayId is given, we'll check if there is already an assay loaded
		if(session.assay){
			def assay = session.assay
			assay.attach()
			session.runs = assay.acquiredRuns
		}else{
			flash.error = "No assay is selected"
		}
			
		render(view: "acquired")
	}	

	
	def downloadAcquiredCSV(){
		def assay = session.assay
		if(assay == null){
			flash.error = "No assay is selected"
		}
		
		// re-attach the assay object to the session
		assay.attach()
		
		def exporter = new ExportCsv()
		def csvString = exporter.exportAcquiredRuns(assay)
		
		// hand a csv file to the browser
		response.setHeader "Content-disposition", "attachment; filename=${assay.shortName}.csv"
		response.contentType = 'text/csv'
		response.outputStream << csvString
		response.outputStream.flush()
	}
	
	def downloadCsv(){	
		def assay = session.assay
		if(assay == null){
			flash.error = "No assay is selected"	
		}
		
		// re-attach the assay object to the session
		assay.attach()
		
		def exporter = new ExportCsv()
		def csvString = exporter.exportRandomizedRuns(assay)
		
		// hand a csv file to the browser
		response.setHeader "Content-disposition", "attachment; filename=${assay.shortName}.csv"
		response.contentType = 'text/csv'
		response.outputStream << csvString
		response.outputStream.flush()
	}
	
	def assayNames() {}
	
	
	def uploadAssayNames() {
		if(session.assay){
			def assayNames = params["assayNames"]
			List<String> assayNameList = assayNames.split("\n")
			
			// re-attach the assay (to get the whole structure)
			def assay = session.assay
			assay.attach()
			
			// clean existing acquiredRuns (we replace all entries)		
			assayService.cleanAcquisitionRuns(assay)
			
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
			assayService.saveAssayWithAcquisitions(assay)
			
			// set the runs into flash
			session.runs = assay.acquiredRuns
			
		}else{
			flash.error = "No assay is selected"
		}	
		
		render(view: "acquired")
	}
	
	
	def chooseExtracted(){}
	
	
	def localExtractedUpload(){
		
		// attach the assay
		def assay = session.assay
		assay.attach()
		
		def nrFilesAdded
			
		// and process it
	
		def info = extractedFileInserter.addFromLocalFolder(assay)
		
		// flash a warning, if names were missing

		def missingN = info[0]
		def notFoundN = info[1]
		nrFilesAdded = info[2]
		
		flash.warning = ''
		
		if(missingN){
			flash.warning += "Following files were missing: <ul>"
			
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
		
		flash.message = nrFilesAdded + ' extracted files were added'
		redirect(action: 'acquired')
	}
	
	
	def uploadExtracted(){
		// attach the assay
		def assay = session.assay
		assay.attach()
		
		def nrFilesAdded
		
		// upload the file
		def f = request.getFile('extractedFile')
		
		if (f.empty) {
			flash.error = 'Uploaded zip file is empty'
			redirect(action: 'index')
			return
		}else{
			def importFile = File.createTempFile("extracted_",".zip")
			f.transferTo(importFile)
			
			// and process it
			try{
				def info = extractedFileInserter.addExtractedFilesZip(assay, importFile.absolutePath)
				
				// flash a warning, if names were missing

				def missingN = info[0]
				def notFoundN = info[1]
				nrFilesAdded = info[2]
				
				flash.warning = ''
				
				if(missingN){
					flash.warning += "Following files were missing: <ul>"
					
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
									
			}catch(e){
				e.printStackTrace()
				flash.error = 'Exception occured: sorry, your ZIP file could not be added'
				redirect(action: 'index')
				return
			}
		}
		
		flash.message = nrFilesAdded + ' extracted files were added'
		redirect(action: 'index')
	}	
	
}
