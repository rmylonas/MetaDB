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
		// new File(path + "/isatab").mkdirs()
		// new File(path + "/upload").mkdirs()
		new File(path + "/data").mkdirs()
		
		return path
	}
	
}
