package it.fmach.metadb.services

import org.apache.commons.io.FileUtils

import it.fmach.metadb.helper.JsonConverter;
import it.fmach.metadb.isatab.model.FEMStudy;

class IsatabService {

	def grailsApplication
	
    def copyIsatabFile(FEMStudy study){
		def dataPath = grailsApplication.config.metadb.dataPath + '/'
		
		// move temp directory to right place
		File workDir = new File(dataPath + study.workDir)
		workDir.mkdir()
		File tempIsatabDir = new File(study.iSATabFilePath)
		File newIsatabDir = new File(dataPath + study.workDir + "/" + "isatab")
		study.iSATabFilePath = newIsatabDir
		
		FileUtils.copyDirectory(tempIsatabDir, newIsatabDir)
		tempIsatabDir.deleteDir()
	}
	
	
	def createDirs(FEMStudy study){
		def dataPath = grailsApplication.config.metadb.dataPath + '/'
		
		// create new directories
		study.assays.each{
			def workDir = new File(dataPath + it.workDir)
			//workDir.deleteDir()
			
			// create new ones
			new File(dataPath + it.workDir + "/NETcdf").mkdirs()
			new File(dataPath + it.workDir + "/Data").mkdirs()
			new File(dataPath + it.workDir + "/otherData").mkdirs()
			new File(dataPath + it.workDir + "/pipeline").mkdirs()
		}
		
	}
	
	
	def addKeywords(FEMStudy study){
		def jsonConverter = new JsonConverter()
		
		Set studyKeywords = []
		
		study.assays.each{ assay ->
			Set assayKeywords = []
			
			// look in runs and samples for keywords
			assay.randomizedRuns.each{ run ->
				//assayKeywords.add(run.msAssayName)
				//assayKeywords.add(run.sample.name)
				assayKeywords.add(run.sample.sourceName)
				assayKeywords.add(run.sample.organism)
				assayKeywords.add(run.sample.organismPart)
				
				def factors = jsonConverter.parseFactorJson(run.sample.factorJSON)
				factors?.get(1).each{
					if(it) assayKeywords.add(it)
				}
			}
			
			// remove empty strings
			assayKeywords = assayKeywords - ''
			
			// add to the assay and add keywords to studyKeywords
			assay.keywords = assayKeywords.join(" ") 
			studyKeywords.addAll(assayKeywords)
		}
		
		study.keywords = studyKeywords.join(" ")
		return study
	}
	
	
	
}
