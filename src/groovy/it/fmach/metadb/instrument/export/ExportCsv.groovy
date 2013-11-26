package it.fmach.metadb.instrument.export

import it.fmach.metadb.isatab.model.FEMAssay;

/**
 * Export randomized runs as a .csv file
 * 
 * @author mylonasr
 */
class ExportCsv {

	String exportRandomizedRuns(FEMAssay assay){
		
		// throw an exception if there is no selected assay
		if(! assay) throw new RuntimeException("Missing assay ")
		
		def sep = ","
		def csv = new StringBuffer()
		
		// the header
		def header = ["Assay name", "Sample name", "Sample code"]
		
		// parse the factor names from first entry which is not blank, QC or STDmix
		def sampleName = 'QC'
		def k = 0
		while(sampleName =~ /blank|QC|STDmix/){
			sampleName = assay.randomizedRuns.get(k++).sample.name
		}
		
		def factorList = parseFactor(assay.randomizedRuns.get(k).sample.factorJSON)
		header << (factorList[0]).join(",")
		
		// for the samples, which do not have any factors (QC, blank and STDmix)
		def emptyFactorList = [null, Collections.nCopies(factorList[0].size(), '') ] 
		
		// print the header
		csv << header.join(sep) << "\n"
		
		def i = 1
		assay.randomizedRuns.each{ run ->			
			def line = []
			line << run.msAssayName
			line << run.sample.name
			line << run.sample.name + "_" + i++
			
			// try to parse the factors, but if there empty (null) we take the emptyFactorList
			def factors = parseFactor(run.sample.factorJSON)
			if(! factors) factors = emptyFactorList
			line << factors[1].join(",")
			
			csv << line.join(sep) << "\n"
		}
		
		return csv.toString()
	}

	
	def parseFactor(String factorJSON){
		def names = []
		def vals = []
		
		//remove {} and ""
		def clean = factorJSON.replace('{', '').replace('}', '').replace('"', '')
		
		// if there are no factors we return an empty list
		if(! clean) return null
		
		def groups = clean.split(",")
		
		groups.each{
			def entry = it.split(":")
			names << entry[0]
			vals << entry[1]
		}
		
		return [names, vals]
	}
		
}
