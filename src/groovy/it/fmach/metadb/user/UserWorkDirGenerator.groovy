package it.fmach.metadb.user

class UserWorkDirGenerator {

	
	/**
	 * Generate a workDir and all necessary subdirs (isatab, upload and stored). 
	 * Throw a RuntimeException if the directory already exists and is non-empty
	 * 
	 * @param path
	 * @return
	 */
	def createWorkDir(String path){
		File workDir = new File(path)
		
		// check if directory exists and is non-empty
		if(workDir.exists() && workDir.directory && !(workDir.list() as List).empty){
			throw new RuntimeException("Directory [" + path + "] already exists. Please choose another path.")
		}
		
		// and create the directories
		File isatabDir = new File(path + "/isatab")
		File uploadDir = new File(path + "/upload")
		File storedDir = new File(path + "/data")
		
		isatabDir.mkdirs()
		uploadDir.mkdir()
		storedDir.mkdir()
		
		return path
	}
	
}
