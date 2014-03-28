package it.fmach.metadb.isatab.controller

import it.fmach.metadb.isatab.model.MetaMsDb

class MetaMSSettingsController {

    def index() { 
		[metaMsDb: MetaMsDb.list()]
	}

	def newDb() {}
	
	def saveNewDb(){
		
		// check if file exists
		if(! new File(params.rDataPath).exists()){
			flash.error = "File [" + params.rDataPath + "] does not exist."
			redirect(action: 'index')
			return
		}
		
		def newDb = new MetaMsDb(name: params.name, rDataPath: params.rDataPath)

		// check validity and save if ok
		if(newDb.validate()){
			newDb.save(flush: true)
			flash.message = "New MetaMS DB was created"
		}else{
			flash.error = newDb.errors.allErrors.join(" and ")
		}

		redirect(action: 'index')
	}
		
	
	def delete(){
		// delete selected DB
		def metaMsDb = MetaMsDb.get(params.id)		
		
		try{
			metaMsDb.delete()
		}catch(Exception e){
			e.printStackTrace()
			flash.error = e.message
			redirect(action: 'index')
			return
		}
		
		flash.message = "MetaMS DB was deleted"
		redirect(action: 'index')
	}
		
}
