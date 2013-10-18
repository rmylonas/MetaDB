package it.fmach.metadb.isatab.model

class FEMRun {
	
	Integer rowNumber
	
	String sampleName
	String parameters
	String protocols
	String msAssayName
	String rawSpectraFile
	String processedSpectraFile
	String status

    static constraints = {
    }
	
}
