package it.fmach.metadb.instrument.export

import it.fmach.metadb.isatab.model.FEMAssay;

/**
 * Export randomized runs as a .csv file
 * 
 * @author mylonasr
 */
class ExportCsv {

	String exportRandomizedRuns(FEMAssay assay){
		
		def csv = new StringBuffer()
		
		// the header
		csv << ""
		
		return csv.toString()
	}
	
}
