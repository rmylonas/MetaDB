package it.fmach.metadb.workflow.metams

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import groovy.util.AntBuilder

class MetaMsZipExporter {
	
	def createTempZip(String resultDir){
		File tempFile = File.createTempFile("pipeline_", ".zip")
		tempFile.delete()
		
		String zipFileName = tempFile.absolutePath
		
		// create zip file using Ant
		def ant = new AntBuilder()
		ant.zip(destfile: zipFileName, basedir: resultDir)
		
		return zipFileName
	}	
}
