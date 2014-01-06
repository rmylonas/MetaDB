package it.fmach.metadb.isatab.controller

import it.fmach.metadb.workflow.metams.FactorExporter
import it.fmach.metadb.workflow.metams.PCAPlotter
import it.fmach.metadb.isatab.model.MetaMsSubmission

class PcaController {

    def index() {
		def submissionId = params.id
		
		def assay = session.assay
		if(assay == null){
			flash.error = "No assay is selected"
			return
		}
		
		// re-attach the assay object to the session
		assay.refresh()
		
		def exporter = new FactorExporter()
		flash.factorList = exporter.getFactorNames(assay.acquiredRuns)
		
		// load the workDir from the submission
		def submission = MetaMsSubmission.get(submissionId)
		flash.workDir = submission.workDir
	}
	
	def plotPCA(){
		def workDir = flash.workDir
		def selectedFactor = params.factorSelection
		
		// create plotter object
		def metaMsDir = grailsApplication.config.metadb.conf.metams.script
		def plotter = new PCAPlotter(metaMsDir)
		
		// plot and get list of plot files
		def plotFileList
		
		try{
			plotFileList = plotter.plotPCA(workDir, selectedFactor)
		}catch(RuntimeException e){
			e.printStackTrace()
			flash.error = e.message
		}
		
		flash.plotFileList = plotFileList
	}
	
	def displayPca = {
		
		println(params)
		
		def img = new FileInputStream(params.file)
		response.contentType = 'image/png'
		response.outputStream << img
		response.outputStream.flush()
	}
	
}
