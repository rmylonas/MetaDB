package it.fmach.metadb.workflow.extraction

import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.fmach.metadb.helper.UnZipper
import it.fmach.metadb.isatab.model.FEMAssay
import static groovy.io.FileType.ANY
import org.apache.commons.io.FilenameUtils


class RawFileInserter {
	
	def unzipper = new UnZipper()
	
	def applicationDataPath
	
	def RawFileInserter(String applicationDataPath){
		this.applicationDataPath = applicationDataPath
	}
	
	def addFromLocalFolder(FEMAssay assay){
		def extractedFilePath = this.applicationDataPath + '/' + assay.workDir
		
		// we're only interested in the study-dir
		// def studyPath = FilenameUtils.getFullPathNoEndSeparator(extractedFilePath)
		
		def fileList = []
		
		// look for folders with name "raw" all over the 
		new File(extractedFilePath).eachFileRecurse(ANY) {
			if(it.name.toLowerCase().endsWith('.raw')) {
				fileList << it
			}
		}
		
		return this.addRawFiles(assay, fileList)
	}
	
	def addRawFilesZip(FEMAssay assay, String zipFilePath){
		
		// create the directories
		def workDirPath = this.applicationDataPath + "/" + assay.workDir
		File workDir = new File(workDirPath)
		workDir.mkdirs()
		File extractedFileDir = new File(workDirPath + "/Data")
		extractedFileDir.mkdir()
		
		// unzip and list all files
		unzipper.unzip(zipFilePath, extractedFileDir.getAbsolutePath())
		
		def fileList = []
		extractedFileDir.eachFile{
			fileList << it.getAbsolutePath()
		}
		
		return this.addRawFiles(assay, fileList)
	}
	
	
	
	
	/**
	 * @param assay
	 * @param fileList
	 * @return [missingFiles, namesNotFound, nrFilesAdded]
	 */
	def addRawFiles(FEMAssay assay, List<String> fileList){
		
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
					run.rawSpectraFilePath = path
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
		
		// return missing files and names if necessary
		if(missingFiles || namesNotFound){
			return [missingFiles, namesNotFound, nrFilesAdded]
		}
		
		return [null, null, nrFilesAdded]
	}
	

}
