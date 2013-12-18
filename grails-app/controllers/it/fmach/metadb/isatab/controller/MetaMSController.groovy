package it.fmach.metadb.isatab.controller

import it.fmach.metadb.workflow.metams.MetaMsRunner

class MetaMSController {

    def index() { 
		

	}
	
	def metaMsSubmission() {
		
		def runSelection = params.list('runSelection')
		
		
		
		flash.runSelection = runSelection
		flash.message = runSelection.size().toString() + " runs were selected"
		
	}
	
	
	def runMetaMs(){
		def metaMsDir = grailsApplication.config.metadb.isatab.metabolConfigFile
		def metaMsDbDir = grailsApplication.config.metadb.conf.metams.script
		def metaMsSettingsDir = grailsApplication.config.metadb.conf.metams.databases
		
		def runner = new MetaMsRunner(metaMsDir, metaMsDbDir, metaMsSettingsDir)
		
		def assay = session.assay
		if(assay == null){
			flash.error = "No assay is selected"
		}
		
		// re-attach the assay object to the session
		assay.attach()
		
	}
	
}
