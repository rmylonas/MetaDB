package it.fmach.metadb.workflow.metams

import it.fmach.metadb.helper.JsonConverter
import it.fmach.metadb.instrument.export.ExportCsv
import it.fmach.metadb.isatab.model.FEMRun

class FactorExporter {
	
	def exportCsv = new ExportCsv()

	def exportFactorTable(List<FEMRun> runs, String csvFile){
		
		// create the csv-table
		def factorString = exportCsv.exportRandomizedRuns(runs)
		
		// write it to the file
		new File(csvFile).withWriter{ it << factorString }
		
	}
	
	def getFactorNames(List<FEMRun> runs){
		def jsonConverter = new JsonConverter()
		
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
		
		return factorList[0]
	}
	
}
