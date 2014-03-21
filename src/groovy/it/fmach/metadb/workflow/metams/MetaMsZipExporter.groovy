package it.fmach.metadb.workflow.metams

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class MetaMsZipExporter {
	
	def createTempZip(String resultDir){
		File tempFile = File.createTempFile("pipeline_", ".zip")
		tempFile.delete()
		
		String zipFileName = tempFile.absolutePath
		String inputDir = resultDir
		  
		ZipOutputStream zipFile = new ZipOutputStream(new FileOutputStream(zipFileName))
		new File(inputDir).eachFile() { file ->
			zipFile.putNextEntry(new ZipEntry(file.getName()))
			def buffer = new byte[1024]
			file.withInputStream { i ->
				def l = i.read(buffer)
				// check wether the file is empty
				if (l > 0) {
					zipFile.write(buffer, 0, l)
				}
			}
			zipFile.closeEntry()
		}
		zipFile.close()
		
		return zipFileName
	}	
}
