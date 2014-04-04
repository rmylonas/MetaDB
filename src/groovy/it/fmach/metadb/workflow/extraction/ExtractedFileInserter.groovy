package it.fmach.metadb.workflow.extraction

import org.apache.commons.lang.StringUtils;
import it.fmach.metadb.helper.UnZipper
import it.fmach.metadb.isatab.model.FEMAssay
import static groovy.io.FileType.FILES
import org.apache.commons.io.FilenameUtils

class ExtractedFileInserter {

	def unzipper = new UnZipper()
	
	def applicationDataPath
	
	def ExtractedFileInserter(String applicationDataPath){
		this.applicationDataPath = applicationDataPath
	}
	
	def addFromLocalFolder(FEMAssay assay){
		def extractedFilePath = this.applicationDataPath + '/' + assay.workDir
		
		// we're only interested in the study-dir
		// def studyPath = FilenameUtils.getFullPathNoEndSeparator(extractedFilePath)
		
		def fileList = []
		
		// look for files ending with ".cdf" ".mzxml" or ".mzdata"
		new File(extractedFilePath).eachFileRecurse(FILES) {
			def s = it.name.toLowerCase()
			if(s.endsWith('.cdf') || s.endsWith('.mzxml') || s.endsWith('.mzdata')) {
				fileList << it
			}
		}
		
		return this.addExtractedFiles(assay, fileList)
	}
	
	def addExtractedFilesZip(FEMAssay assay, String zipFilePath){
		
		// create the directories
		def workDirPath = this.applicationDataPath + "/" + assay.workDir
		File workDir = new File(workDirPath)
		workDir.mkdirs()
		File extractedFileDir = new File(workDirPath + "/extractedFiles")
		extractedFileDir.mkdir()
		
		// unzip and list all files
		unzipper.unzip(zipFilePath, extractedFileDir.getAbsolutePath())
		
		def fileList = []
		extractedFileDir.eachFile{
			fileList << it.getAbsolutePath()
		}
		
		return this.addExtractedFiles(assay, fileList)		
	}
	
	
	
	/**
	 * @param assay
	 * @param fileList
	 * @return [missingFiles, namesNotFound, nrFilesAdded]
	 */
	def addExtractedFiles(FEMAssay assay, List<String> fileList){
		
		if(! assay.acquiredRuns) throw new RuntimeException("This assay does not contain any acquiredRuns")
		
		def missingFiles = []
		def namesNotFound = []
		def nrFilesAdded = 0
		
		// create a map of all assay names
		def assayNameMap = [:]
		assay.acquiredRuns.each{
			assayNameMap[it.msAssayName] = false
		}
		
		// link the files to the run
		fileList.each{ path ->
			def filename = path.absolutePath.split("/").last()
			def found = false
			
			// look if we find the right name
			for(def run: assay.acquiredRuns){
				if(StringUtils.containsIgnoreCase(filename, run.msAssayName)){
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
			if(! v){
				missingFiles << k
			}else{
				nrFilesAdded ++
			}
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
			return [missingFiles, namesNotFound, nrFilesAdded]
		}
		
		return [null, null, nrFilesAdded]
	}
	
}
