package it.fmach.metadb.isatab.controller

class MetaMSController {

    def index() { 
		

	}
	
	def runMetaMS() {
		
		def runSelection = params.list('runSelection')
		
		def assay = session.assay
		if(assay == null){
			flash.error = "No assay is selected"
		}
		
		// re-attach the assay object to the session
		assay.attach()
		
	}
	
}
