package it.fmach.metadb.isatab.controller

import it.fmach.metadb.workflow.metams.MetaMsRunner

class MetaMSController {

    def index() {
		
		def assay = session.assay
		if(assay == null){
			flash.error = "No assay is selected"
		}
		
		// re-attach the assay object to the session
		assay.refresh()
		
		flash.metaMsSubmissions = assay.metaMsSubmissions
		
	}
	
	def metaMsSubmission() {
		
		def runSelection = flash.runsSelection
		
		// in case we were not redericted from runMetaMS
		if(! flash.runsSelection){
			runSelection = params.list('runSelection')
		}
		
		flash.runSelection = runSelection
		flash.message = runSelection.size().toString() + " runs were selected"
		
	}
	
	
	def runMetaMS(){
		
		def minRt = params['minRt']
		def maxRt = params['maxRt']
		def runSelection = flash.runSelection		
		
		// check if RT's are valid
		if(minRt || maxRt){
			if(! (maxRt.isDouble() && minRt.isDouble())){
				flash.error = "invalid retention times [" + minRt + "] and/or [" + maxRt + "]"
				flash.runsSelection = runSelection
				redirect(action: 'metaMsSubmission')
				return
			}
		}
	
		def metaMsDir = grailsApplication.config.metadb.conf.metams.script
		def metaMsDbDir = grailsApplication.config.metadb.conf.metams.databases
		def metaMsSettingsDir = grailsApplication.config.metadb.conf.metams.instrumentSettings
		
		def runner = new MetaMsRunner(metaMsDir, metaMsDbDir, metaMsSettingsDir)
		
		def assay = session.assay
		if(assay == null){
			flash.error = "No assay is selected"
		}
		
		// re-attach the assay object to the session
		assay.attach()
		
		// start runner
		runner.runMetaMs(assay, runSelection, minRt, maxRt)
		
		flash.message = "started runMetaMS"
		
		redirect(action: 'index')
	}
	
}
