package it.fmach.metadb.export

import java.util.List;

import it.fmach.metadb.helper.JsonConverter
import it.fmach.metadb.isatab.model.FEMAssay;
import it.fmach.metadb.isatab.model.FEMRun
import groovy.util.AntBuilder

/**
 * Export a ZIP file from the whole Folder of this study
 * 
 * @author mylonasr
 */
class StudyZipExporter {

	String createTempZip(String dir){
		File tempFile = File.createTempFile("study_", ".zip")
		tempFile.delete()
		
		String zipFileName = tempFile.absolutePath		
		
		// use AntBuilder to create zip file of the whole folder
		def ant = new AntBuilder()
		ant.zip(destfile: zipFileName, basedir: dir)
		
		return zipFileName
	}	
	
}
