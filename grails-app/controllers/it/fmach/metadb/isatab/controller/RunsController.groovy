package it.fmach.metadb.isatab.controller

import it.fmach.metadb.instrument.export.ExportCsv
import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMRun

class RunsController {
	
    def index() { 
		String assayId = params['id']
		
		// if a assay id is provided we load only the concerning runs
		if(assayId){
			def assay = FEMAssay.get(assayId.toLong())
			flash.runs = assay.randomizedRuns
			session.assay = assay
			render(view: "index")
		}
		
		// if no assayId is given, we'll check if there is already an assay loaded
		if(session.assay){
			flash.runs = session.assay.randomizedRuns
			render(view: "index")
		}
	}
	
	def downloadCsv(){	
		def assay = session.assay
		if(assay == null){
			flash.error = "No assay selected"	
		}
		
		def exporter = new ExportCsv()
		def csvString = exporter.exportRandomizedRuns(assay)
		
		// hand a csv file to the browser
		response.setHeader "Content-disposition", "attachment; filename=${assay.name}.csv"
		response.contentType = 'text/csv'
		response.outputStream << csvString
		response.outputStream.flush()
	}
}
