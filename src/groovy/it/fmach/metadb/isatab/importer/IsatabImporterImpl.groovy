package it.fmach.metadb.isatab.importer

import java.util.List;

import it.fmach.metadb.isatab.model.FEMStudy;

import org.isatools.isacreator.io.importisa.ISAtabFilesImporter
import org.isatools.isacreator.model.Investigation;

/**
 * @author mylonasr
 *
 *	import isatab files
 */
class IsatabImporterImpl implements IsatabImporter {
	
	private ISAtabFilesImporter importer
	private ISAtoolsModelConverter converter
	
	/**
	 * @param configDir Directory containing xml files describing valid ISAtab structures (https://github.com/ISA-tools/ISAconfigurator)
	 */
	def IsatabImporterImpl(String configDir){
		importer = new ISAtabFilesImporter(configDir)
		converter = new ISAtoolsModelConverterImpl()
	}
	
	/**
	 * Imports an ISAtab directory and converts it into a Study object containing all information stored in our databases
	 *
	 * @param isatabDir ISAtab directory containing all necessary files
	 * @return returns a list of Study objects, if parsing was successful
	 */
	List<FEMStudy> importIsatabFiles(String isatabDir){
		importer.importFile(isatabDir)
		Investigation isaInvestig = importer.getInvestigation()
		List<FEMStudy> studyList = converter.convertInvestigation(isaInvestig)
		
		// add the filePath to all studies
		studyList.each {it.iSATabFilePath = isatabDir}
	}	
	
}
