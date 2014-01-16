package it.fmach.metadb.isatab.importer

import it.fmach.metadb.User;

class LocalIsatabImporter {

	def workDir
	def currentUser
	def configDir
	
	
	def LocalIsatabImporter(String configDir, String workDir, User currentUser){
		this.workDir = workDir
		this.currentUser = currentUser
		this.configDir = configDir
	}
	
	
	def checkUploadFolder(){
		// check if a i_Investigation.txt file is in the upload folder
		def uploadFile = new File(this.workDir + "/upload/i_Investigation.txt")
		return (uploadFile.exists()) ? (true) : (false)
	}
	
	
	def importIsatabFile(){
		def uploadDir = this.workDir + "/upload"
		
		// import the isatabfiles
		def isatabImporter = new IsatabImporterImpl(configDir, workDir + "/data", currentUser)
		def investigation = isatabImporter.importIsatabFiles(uploadDir)
		
		// if parsing was successfull, we delete the files in upload folder
//		if(investigation.isaParsingInfo.success){
//			def uploadFiles = new File(uploadDir)
//			uploadFiles.deleteDir()
//			uploadFiles.mkdir()
//		}
		
		return investigation
	}
	
	
	
	
}
