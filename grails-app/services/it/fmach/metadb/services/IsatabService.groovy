package it.fmach.metadb.services

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
}
