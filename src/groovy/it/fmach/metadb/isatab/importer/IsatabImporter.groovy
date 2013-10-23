package it.fmach.metadb.isatab.importer

import java.util.List;

import it.fmach.metadb.isatab.model.FEMStudy;


/**
 * @author mylonasr
 *
 *	import isatab files
 */
interface IsatabImporter {

	/**
	 * Imports an ISAtab directory and converts it into a Study object containing all information stored in our databases
	 * 
	 * @param isatabDir ISAtab directory containing all necessary files
	 * @return returns a list Study objects, if parsing was successful
	 */
	FEMInvestigation importIsatabFiles(String isatabDir)
	
}
