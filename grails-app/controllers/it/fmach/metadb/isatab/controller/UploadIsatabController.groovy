package it.fmach.metadb.isatab.controller

import grails.converters.JSON;
import it.fmach.metadb.isatab.importer.FEMInvestigation;
import it.fmach.metadb.isatab.importer.IsatabImporter
import it.fmach.metadb.isatab.importer.IsatabImporterImpl
import it.fmach.metadb.isatab.importer.StudyMerger;
import it.fmach.metadb.isatab.model.FEMGroup;
import it.fmach.metadb.isatab.model.FEMProject;
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.isatab.model.FEMSample
import it.fmach.metadb.isatab.model.FEMStudy
import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.InstrumentMethod;
import it.fmach.metadb.workflow.randomization.RunRandomization;

class UploadIsatabController {
	
	def isatabService
	def springSecurityService
	def studyService
	def runRandomization = new RunRandomization()
	def studyMerger = new StudyMerger()

    def index() { }
	
	def upload() {
		def currentUser = springSecurityService.getCurrentUser()
		IsatabImporter importer = new IsatabImporterImpl(grailsApplication.config.metadb.isatab.metabolConfigFile, currentUser.workDir + "/data", currentUser)
		
		def importFile
		FEMInvestigation investigation
		
		// upload the file
		def f = request.getFile('isaTabFile')
		def originalFilename = f.getOriginalFilename()
		
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
					//flash.message = ('ISAtab file was succesfully processed')
				}else{
					flash.error = 'Parsing Error: sorry, your ISAtab file could not be parsed'
					println(investigation.isaParsingInfo.errorMessage)
					redirect(action: 'index')
					return
				}				
				
			}catch(e){
				e.printStackTrace()
				flash.error = "Sorry, your ISAtab file could not be parsed: " + e.message
				redirect(action: 'index')
				return
			}
		}
		
		// set the original filenames
		investigation.studyList.each{
			it.originalFilename = originalFilename
			
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
		session.already = studyMerger.checkStudy(session.study)
		
		session.groups = FEMGroup.list()
		
		// get the projects of first group by default and update using availableProjects (AJAX)
		session.projects = session.groups.get(0).projects
		
		render(view: 'parsing')
	}
	
	def parsing() { }
	
	def insert() {
		// look at all assays and store the selected ones
		def insertedAssays = 0
		def insertedStudies = 0
		
		try{
			
			FEMStudy study = session.study
			
			// assign selected group and project			
			study.project = FEMProject.get(params["project"])
			study.group = FEMGroup.get(params["group"])
			
			def iter = study.assays.iterator()
			while(iter.hasNext()){
				FEMAssay assay = iter.next()
												
				// remove the entries which weren't selected
				if(params[assay.name + "_cb"] != "on"){
					iter.remove()
					continue
				}
				
				// set instrument method
				assay.method = InstrumentMethod.get(params[assay.name + "_me"])
				
				// randomize the runs as described in the method
				if(assay.method.randomization){
					runRandomization.randomizeAssay(assay)
				}else{
					runRandomization.noRandomization(assay)
				}
				
				// set assay project and group
				assay.project = study.project
				assay.group = study.group
			}
				
			// only insert study if there is at least one assay selected
			if(study.assays.size() >= 1){
				// if a Study with same identifier exists, we merge it
				if(session.already){
					insertedAssays = this.studyMerger.mergeStudy(study)
				// otherwise we insert a new study
				}else{
					studyService.saveStudy(study)
					insertedAssays += study.assays.size()
					insertedStudies ++
				}
				
				this.isatabService.createDirs(study)
				this.isatabService.addKeywords(study)
			}
		
		}catch(Exception e){
			e.printStackTrace()
			flash.error = e.getMessage()
		}
		
		// empty the session objects
		session.study = null
		session.already = null
		session.groups = null
		session.projects = null
		
		// return a success message, if there is no error
		if(! flash.error) flash.message = insertedAssays + " assay(s) from " + insertedStudies + " study were succesfully inserted"
		redirect(action: 'index')
		
	}
	
	// AJAX method
	def ajaxProjects(){
		def group = FEMGroup.get(params.groupId)		
		render group.projects as JSON
	}
	
}
