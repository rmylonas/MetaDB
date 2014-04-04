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
		session.factorList = exporter.getFactorNames(assay.acquiredRuns)
		
		// load the workDir from the submission
		def submission = MetaMsSubmission.get(submissionId)
		session.workDir = submission.workDir
	}
	
	def plotPCA(){
		def workDir = session.workDir
		def selectedFactor = params.factorSelection
		def sqrtScaling = (params.sqrtScaling == "on") ? (true) : (false)
		def sumNorm = (params.sumNorm == "on") ? (true) : (false)
		
		// create plotter object
		def metaMsDir = grailsApplication.config.metadb.conf.metams.script
		def plotter = new PCAPlotter(metaMsDir)
		
		// plot and get list of plot files
		def plotFileList
		
		try{
			plotFileList = plotter.plotPCA(workDir, selectedFactor, sqrtScaling, sumNorm)
		}catch(RuntimeException e){
			e.printStackTrace()
			flash.error = e.message
		}
		
		session.plotFileList = plotFileList
	}
	
	def displayPca = {
		
		def img = new FileInputStream(params.file)
		response.contentType = 'image/png'
		response.outputStream << img
		response.outputStream.flush()
	}
	
}
