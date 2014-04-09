package it.fmach.metadb.export

import java.util.List;

import it.fmach.metadb.helper.JsonConverter
import it.fmach.metadb.isatab.model.FEMAssay;
import it.fmach.metadb.isatab.model.FEMRun

/**
 * Export randomized runs as a .csv file
 * 
 * @author mylonasr
 */
class ExportCsv {

	private static String sep = ","
	
	def jsonConverter = new JsonConverter()
	
	String exportRandomizedRuns(FEMAssay assay){
		// throw an exception if there is no selected assay
		if(! assay) throw new RuntimeException("Missing assay ")
		
		this.exportRandomizedRuns(assay.randomizedRuns)
	}
	
	
	String exportRandomizedRuns(List<FEMRun> runs){		
		def csv = new StringBuffer()
		
		// the header
		def header = ["Assay name", "Sample name", "Sample code"]
		
		// parse the factor names from first entry which is not blank, QC or STDmix
		def sampleName = 'QC'
		def k = 0
		while(sampleName =~ /(?i)blank|QC|STDmix/ && k < runs.size()){
			FEMRun run = runs.get(k++)
			run.attach()
			sampleName = run.sample.name
		}
		
		// the last index was the good one
		k -= 1
		runs.get(k).attach()
		def factorList = jsonConverter.parseFactorJson(runs.get(k).sample.factorJSON)
		def emptyFactorList = []
		
		if(factorList){
			header << (factorList[0]).join(sep)
			
			// for the samples, which do not have any factors (QC, blank and STDmix)
			emptyFactorList = [null, Collections.nCopies(factorList[0].size(), '') ]
		}
		
		// print the header
		csv << header.join(sep) << "\n"
		
		def i = 1
		runs.each{ run ->			
			def line = []
			line << run.msAssayName
			line << run.sample.name
			line << (run.sample.name ==~ /(?i)blank|QC|STDmix/ ? '' : run.sample.name + "_" + sprintf('%03d', i++))
			
			// try to parse the factors, but if there empty (null) we take the emptyFactorList
			def factors = jsonConverter.parseFactorJson(run.sample.factorJSON)
			if(! factors) factors = emptyFactorList
			if(factors) line << factors[1].join(sep)
			
			csv << line.join(sep) << "\n"
		}
		
		return csv.toString()
	}

	String exportAcquiredRuns(FEMAssay assay){
		// throw an exception if there is no selected assay
		if(! assay) throw new RuntimeException("Missing assay ")
		
		// select the runs with status "extracted"
		List<FEMRun> runs = []
		assay.acquiredRuns.each{ run ->
			runs << run
		}
		
		this.exportAcquiredRuns(runs)
	}
	
	
	String exportAcquiredRuns(List<FEMRun> runs){
		def csv = new StringBuffer()
		
		// the header
		def header = ["Row No.", "Sample Name", "Parameter Value [Scan polarity]", "MS Assay Name", "Derived Spectral Data File", "Raw Spectral Data File", "status"]
		
		// parse the factor names from first entry which is not blank, QC or STDmix
		def sampleName = 'QC'
		def k = 0
		while(sampleName =~ /(?i)blank|QC|STDmix/){
			FEMRun run = runs.get(k++)
			run.attach()
			sampleName = run.sample.name
		}
		
		// the last index was the good one
		k -= 1
		runs.get(k).attach()
		def factorList = jsonConverter.parseFactorJson(runs.get(k).sample.factorJSON)
		header << (factorList[0]).join(sep)
		
		// for the samples, which do not have any factors (QC, blank and STDmix)
		def emptyFactorList = [null, Collections.nCopies(factorList[0].size(), '') ]
		
		// print the header
		csv << header.join(sep) << "\n"
		
		def i = 1
		runs.each{ run ->
			def line = []
			line << i ++
			line << run.sample.name
			line << run.scanPolarity
			line << run.msAssayName
			line << run.derivedSpectraFilePath
			line << run.rawSpectraFilePath
			line << run.status
			
			// try to parse the factors, but if there empty (null) we take the emptyFactorList
			def factors = jsonConverter.parseFactorJson(run.sample.factorJSON)
			if(! factors) factors = emptyFactorList
			line << factors[1].join(sep)
			
			csv << line.join(sep) << "\n"
		}
		
		return csv.toString()
	}
	
		
}
