package it.fmach.metadb.workflow.extraction

import it.fmach.metadb.helper.UnZipper
import it.fmach.metadb.isatab.model.FEMAssay


class ExtractedFileInserter {

	def unzipper = new UnZipper()
	
	
	def addExtractedFilesZip(FEMAssay assay, String zipFilePath){
		
		// create the directory
		File extractedFileDir = new File(assay.workDir + "/extractedFiles")
		extractedFileDir.mkdir()
		
		// unzip and list all files
		unzipper.unzip(zipFilePath, extractedFileDir.getAbsolutePath())
		
		def fileList = []
		extractedFileDir.eachFile{
			fileList << it.getAbsolutePath()
		}
		
		return this.addExtractedFiles(assay, fileList)		
	}
	
	
	
	def addExtractedFiles(FEMAssay assay, List<String> fileList){
		
		if(! assay.acquiredRuns) throw new RuntimeException("This assay does not contain any acquiredRuns")
		
		def missingFiles = []
		def namesNotFound = []
		
		// create a map of all assay names
		def assayNameMap = [:]
		assay.acquiredRuns.each{
			assayNameMap[it.msAssayName] = false
		}
		
		// link the files to the run
		fileList.each{ path ->
			def filename = path.split("/").last()
			def found = false
			
			// look if we find the right name
			for(def run: assay.acquiredRuns){
				if(filename.contains(run.msAssayName)){
					run.derivedSpectraFilePath = path
					run.status = "extracted"
					assayNameMap[run.msAssayName] = true
					found = true
					break
				}
			}
			
			if(! found) namesNotFound << filename
		}
		
		// list of missing files
		assayNameMap.each{ k, v ->
			if(! v) missingFiles << k
		}
		
		// if all runs are status "extracted", the assay gets "extracted"
		def status = "extracted"
		for(def run: assay.acquiredRuns){
			if(run.status != "extracted"){
				status = "acquired"
				break
			} 
		}
		assay.status = status
		
		// return missing files and names if necessary
		if(missingFiles || namesNotFound){
			return [missingFiles, namesNotFound]
		}
		
		return null
	}
	
}
