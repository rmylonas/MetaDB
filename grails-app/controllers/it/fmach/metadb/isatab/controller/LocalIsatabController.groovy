package it.fmach.metadb.isatab.controller

import it.fmach.metadb.isatab.importer.LocalIsatabImporter
import it.fmach.metadb.isatab.importer.StudyMerger
import it.fmach.metadb.isatab.model.FEMGroup;

class LocalIsatabController {
	
	def springSecurityService
	def isatabService
	def studyMerger = new StudyMerger()
	
	def dataPath = grailsApplication.config.metadb.dataPath
	
    def index() { 
		def currentUser = springSecurityService.getCurrentUser()
		def importer = new LocalIsatabImporter(grailsApplication.config.metadb.isatab.metabolConfigFile, currentUser.workDir, currentUser)
		
		def ready = importer.checkUploadFolder()
		
		if(ready){
			
			def investigation
			
			// load the file
			try{
				investigation = importer.importIsatabFile()
				
				if(investigation.isaParsingInfo.success){
					//flash.message = ('ISAtab file was succesfully processed')
				}else{
					flash.error = 'Parsing Error: sorry, your ISAtab file could not be parsed'
					redirect(action: 'index', controller: 'uploadIsatab')
					return
				}
				
			}catch(e){
				e.printStackTrace()
				flash.error = "Sorry, your ISAtab file could not be parsed: " + e.message
				redirect(action: 'index', controller: 'uploadIsatab')
				return
			}
			
			// set the original filenames
			investigation.studyList.each{				
				// copy isatab files to the right place
				isatabService.copyIsatabFile(it)
			}
			
			// we currently only allow one study per isatab
			if(investigation.studyList.size() > 1){
				flash.error = 'Sorry, but only one Study is allowed per IsaTab file'
				redirect(action: 'index')
				return
			}
			session.study = investigation.studyList.get(0)
			
			// check if the study has to be merged
			session.already = this.studyMerger.checkStudy(session.study)
			
			session.groups = FEMGroup.list()
			
			// get the projects of first group by default and update using availableProjects (AJAX)
			session.projects = session.groups.get(0).projects
			
			redirect(action: 'parsing', controller: 'uploadIsatab')
			return
		}
		
		flash.warning = "No valid Isatab file was found in [" + currentUser.workDir + "/upload]. Copy files into this folder or upload through the interface."
		redirect(action: 'index', controller: 'uploadIsatab')
	}
}
