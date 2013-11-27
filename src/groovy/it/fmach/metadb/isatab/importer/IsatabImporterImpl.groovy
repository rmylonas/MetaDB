package it.fmach.metadb.isatab.importer

import java.util.List;


import it.fmach.metadb.helper.UnZipper
import it.fmach.metadb.isatab.model.FEMStudy;
import it.fmach.metadb.isatab.model.ISAParsingInfo;

import org.isatools.errorreporter.model.ErrorMessage;
import org.isatools.errorreporter.model.ISAFileErrorReport;
import org.isatools.isacreator.io.importisa.ISAtabFilesImporter
import org.isatools.isacreator.model.Investigation;

/**
 * @author mylonasr
 *
 *	import isatab files
 */
class IsatabImporterImpl implements IsatabImporter {
	
	def unzipper = new UnZipper()
	
	private ISAtabFilesImporter importer
	private ISAtoolsModelConverter converter
	
	/**
	 * @param configDir Directory containing xml files describing valid ISAtab structures (https://github.com/ISA-tools/ISAconfigurator)
	 */
	def IsatabImporterImpl(String configDir){
		// check if configDir is valid, otherwise throw a runtime error
		def folder = new File(configDir)
		if( !folder.exists() ) {
			throw new RuntimeException("the ISAtab configuration folder [" + configDir + "] cannot be read")	
		}
		
		importer = new ISAtabFilesImporter(configDir)
		converter = new ISAtoolsModelConverterImpl()
	}
	
	/**
	 * Imports an ISAtab directory and converts it into a Study object containing all information stored in our databases
	 *
	 * @param isatabDir ISAtab directory containing all necessary files
	 * @return returns a list of Study objects, if parsing was successful
	 */
	FEMInvestigation importIsatabFiles(String isatabDir){
		def investigation = new FEMInvestigation()
		
		importer.importFile(isatabDir)
		
		// get the errors if there are any
		def errorSet = []
		
		for(ISAFileErrorReport error: importer.getMessages()){
			def errorMessage = ""
			for(ErrorMessage message : error.getMessages()){
				errorMessage += message.getMessage()
			}
			errorSet.add(error.getProblemSummary() + ": " + errorMessage)
		}
		
		ISAParsingInfo parsingInfo = new ISAParsingInfo()
		
		if(errorSet){
			parsingInfo.success = false
			parsingInfo.nrOfErrors = errorSet.size()
			
			// create JSON string of errors
			def builder = new groovy.json.JsonBuilder()
			builder(errorSet)
			parsingInfo.errorMessage = builder.toString()
			parsingInfo.status = "parsing failed"
		}else{
			Investigation isaInvestig = importer.getInvestigation()
			List<FEMStudy> studyList = converter.convertInvestigation(isaInvestig)
	
			// add the filePath to all studies
			studyList.each {it.iSATabFilePath = isatabDir}
			investigation.studyList = studyList
			
			// fill in log info
			parsingInfo.success = true
			parsingInfo.status = "parsed"
			
		}
				
		investigation.isaParsingInfo = parsingInfo
		return investigation
		
	}	

	@Override
	public FEMInvestigation importIsatabZip(String isatabZipDir) {
			
			File tempDir = File.createTempFile("unzip_", Long.toString(System.nanoTime()))
			tempDir.delete()
			tempDir.mkdir()
			// tempDir.deleteOnExit()
			
			unzipper.unzip(isatabZipDir, tempDir.getAbsolutePath())	
			return this.importIsatabFiles(tempDir.getAbsolutePath())
	}
	
}
