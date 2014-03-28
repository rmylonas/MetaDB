package it.fmach.metadb.isatab.controller

import it.fmach.metadb.export.ExportCsv;
import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.workflow.acquisition.AcquiredNamesInserter
import it.fmach.metadb.workflow.extraction.ExtractedFileInserter
import it.fmach.metadb.workflow.extraction.RawFileInserter

class RunsController {
	
	def assayService
	def acquiredNamesInserter = new AcquiredNamesInserter()
	
	// ***************************************************
	// Show either randomized or acquired runs
	// ***************************************************
	
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
	
	// ***************************************************
	// Download CSV
	// ***************************************************
	
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
	
	
	// ***************************************************
	// Add new Assay names
	// ***************************************************
	
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
			def missingNames
			try{
				missingNames = acquiredNamesInserter.addAcquiredAssayNames(assay, assayNameList)
			}catch(Exception e){
				flash.error = e.message
				redirect(action: 'assayNames')
				return
			}
			
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
	
	
	// ***************************************************
	// Upload other data
	// ***************************************************
	
	def chooseOtherData(){}
	
	def uploadOtherData(){		
		
		// attach the assay
		def assay = session.assay
		assay.attach()
		
		// upload the file
		def dataPath = grailsApplication.config.metadb.dataPath
		def otherDataPath = dataPath + "/" + assay.workDir + "/otherData"
		File workDir = new File(otherDataPath)
		workDir.mkdirs()
		
		def f = request.getFile('extractedFile')
		def originalFilename = f.getOriginalFilename()
		
		if (f.empty) {
			flash.error = 'Uploaded zip file is empty'
			redirect(action: 'acquired')
			return
		}else{
			File targetFile = new File(workDir.getAbsolutePath() + "/" + originalFilename)
			f.transferTo(targetFile)
		}
		
		flash.message = 'ZIP archive was added'
		redirect(action: 'acquired')
	}
	
	
	
	// ***************************************************
	// Upload raw files
	// ***************************************************
	
	def chooseRaw(){}
	
	def localRawUpload(){
		
		def rawFileInserter = new RawFileInserter(grailsApplication.config.metadb.dataPath)
		
		// attach the assay
		def assay = session.assay
		assay.attach()
		
		def nrFilesAdded
			
		// and process it
	
		def info = rawFileInserter.addFromLocalFolder(assay)
		
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
		
		flash.message = nrFilesAdded + ' raw files were added'
		redirect(action: 'acquired')
	}
	
	
	def uploadRaw(){
		
		def rawFileInserter = new RawFileInserter(grailsApplication.config.metadb.dataPath)
		
		// attach the assay
		def assay = session.assay
		assay.attach()
		
		def nrFilesAdded
		
		// upload the file
		def f = request.getFile('extractedFile')
		
		if (f.empty) {
			flash.error = 'Uploaded zip file is empty'
			redirect(action: 'acquired')
			return
		}else{
			def importFile = File.createTempFile("raw_",".zip")
			f.transferTo(importFile)
			
			// and process it
			try{
				def info = rawFileInserter.addRawFilesZip(assay, importFile.absolutePath)
				
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
				redirect(action: 'acquired')
				return
			}
		}
		
		flash.message = nrFilesAdded + ' raw files were added'
		redirect(action: 'acquired')
	}
	
	
	// ***************************************************
	// Upload extracted files
	// ***************************************************
	
	def chooseExtracted(){}
	
	def localExtractedUpload(){
		
		def extractedFileInserter = new ExtractedFileInserter(grailsApplication.config.metadb.dataPath)
		
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
		def extractedFileInserter = new ExtractedFileInserter(grailsApplication.config.metadb.dataPath)
		
		// attach the assay
		def assay = session.assay
		assay.attach()
		
		def nrFilesAdded
		
		// upload the file
		def f = request.getFile('extractedFile')
		
		if (f.empty) {
			flash.error = 'Uploaded zip file is empty'
			redirect(action: 'acquired')
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
				redirect(action: 'acquired')
				return
			}
		}
		
		flash.message = nrFilesAdded + ' extracted files were added'
		redirect(action: 'acquired')
	}	
	
}
