package it.fmach.metadb.services

import it.fmach.metadb.helper.JsonConverter;
import it.fmach.metadb.isatab.model.FEMStudy;

class IsatabService {

    def copyIsatabFile(FEMStudy study){
		// move temp directory to right place
		File workDir = new File(study.workDir)
		workDir.mkdir()
		File tempIsatabDir = new File(study.iSATabFilePath)
		File newIsatabDir = new File(study.workDir + "/" + "isatab")
		study.iSATabFilePath = newIsatabDir
		
		tempIsatabDir.renameTo(newIsatabDir)
	}
	
	
	def createDirs(FEMStudy study){
		// create new directories
		study.assays.each{
			def workDir = new File(it.workDir)
			//workDir.deleteDir()
			
			// create new ones
			def extractedDir = new File(it.workDir + "/extractedFiles")
			def pipelineDir = new File(it.workDir + "/pipeline")
			extractedDir.mkdirs()
			pipelineDir.mkdir()
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
