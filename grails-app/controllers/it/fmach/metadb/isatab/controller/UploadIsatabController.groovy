package it.fmach.metadb.isatab.controller

import grails.converters.JSON;
import it.fmach.metadb.isatab.importer.FEMInvestigation;
import it.fmach.metadb.isatab.importer.IsatabImporter
import it.fmach.metadb.isatab.importer.IsatabImporterImpl
import it.fmach.metadb.isatab.model.FEMGroup;
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.isatab.model.FEMSample
import it.fmach.metadb.isatab.model.FEMStudy
import it.fmach.metadb.isatab.model.FEMAssay

class UploadIsatabController {
	
	def studyService

    def index() { }
	
	def upload() {
		IsatabImporter importer = new IsatabImporterImpl(grailsApplication.config.metadb.isatab.metabolConfigFile)
		
		def importFile
		FEMInvestigation investigation
		
		// upload the file
		def f = request.getFile('isaTabFile')
		
		if (f.empty) {
			flash.error = 'ISAtab file is empty'
			redirect(action: 'index')
			return
		}else{
			importFile = File.createTempFile("isatab_",".zip")		
			f.transferTo(importFile)
			
			// and process it
			try{
				investigation = importer.importIsatabZip(importFile.absolutePath)
				
				if(investigation.isaParsingInfo.success){
					flash.message = ('ISAtab file was succesfully processed')
				}else{
					flash.error = 'Parsing Error: sorry, your ISAtab file could not be parsed'
					redirect(action: 'index')
					return
				}				
				
			}catch(e){
				e.printStackTrace()
				flash.error = 'Exception occured: sorry, your ISAtab file could not be parsed'
				redirect(action: 'index')
				return
			}
		}
		
		session.investigation = investigation
		session.groups = FEMGroup.list()
		// get the projects of first group by default and update using availableProjects (AJAX)
		session.projects = session.groups.get(0).projects
		
		redirect(action: 'parsing')
		
	}
	
	def parsing() { }
	
	def insert() {
		// look at all assays and store the selected ones
		def insertedAssays = 0
		def insertedStudies = 0
		
		for(FEMStudy study: session.investigation.studyList){	
			def iter = study.assays.iterator()
			while(iter.hasNext()){
				FEMAssay assay = iter.next()
												
				// remove the entries which weren't selected
				if(params[assay.name] != "on") iter.remove()
			}
			
			// only insert study if there is at least one assay selected
			if(study.assays.size() >= 1){
				studyService.saveStudy(study)
				insertedAssays += study.assays.size()
				insertedStudies ++
			}
		}
		
		flash.message = insertedAssays + " assay(s) from " + insertedStudies + " study were succesfully inserted"
		redirect(action: 'index')
		
	}
	
	// AJAX method
	def ajaxProjects(){
		def group = FEMGroup.get(params.groupId)
		render group.projects as JSON
	}
	
}
