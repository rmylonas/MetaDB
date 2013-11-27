package it.fmach.metadb.workflow.acquisition

import it.fmach.metadb.helper.DeepCopier
import it.fmach.metadb.isatab.model.FEMAssay;
import it.fmach.metadb.isatab.model.FEMRun

class AcquiredNamesInserter {
	
	def deepCopier = new DeepCopier()

	/**
	 * Set the acquired names for all runs. Gives back a list of the names which are missing
	 * and another list of names which could not be mapped to the existing ones.
	 * 
	 *  ATTENTION: Here the AssayName is the name of a run, while in ISAtab the Assay is a collection of runs!
	 * 
	 * @param assay
	 * @return [listOfMissingNames, listOfNonMappedNames]
	 */
	def addAcquiredAssayNames(FEMAssay assay, List<String> assayNames){
		def acquiredRuns = []
		def missingNames = []
		def namesNotFound = []
		
		// construct a map of [samplenames:FEMRun]
		// QC, STDmix and blank we only take ones and separately (in qualitySamples)
		def runNameMap = [:]
		def qualitySampleMap = [:]
		
		this.prepareRunNameMaps(assay.randomizedRuns, runNameMap, qualitySampleMap)
		
		// look at all the assayNames
		def i = 0
		assayNames.each{ name ->
			// separate the last _int part
			def matcher = name =~ /(.+)_(\d+)/
			def idName = matcher ? matcher[0][1] : null
			// def number = matcher[0][2]
			
			// if it is a QC, stdMix or blank, we just add a run with this name
			if(idName =~ /QC|STDmix|blank/){
				acquiredRuns.add(
					new FEMRun(msAssayName: name, 
							rowNumber: i, scanPolarity: assay.instrumentPolarity, 
							status: 'acquired', sample: qualitySampleMap[idName])
				)
			// if it's a sample
			}else{
				// if we can map the name
				if(runNameMap[idName]){
					FEMRun run = deepCopier.deepCopy(runNameMap[idName])
					
					// change the status and msAssayName
					run.status = "acquired"
					run.msAssayName = name
					
					acquiredRuns << run
					runNameMap.remove(idName)
				}else{
					namesNotFound << name
				}
			}
		}
		
		// add acquiredRuns
		assay.acquiredRuns = acquiredRuns
		
		// add missing names
		runNameMap.each {name, run ->
			missingNames << name
		}
		
		return [missingNames, namesNotFound]
	}
	
	
	private def prepareRunNameMaps(def runList, def runNameMap, def qualitySampleMap){
		runList.each{ run ->
			switch (run.sample.name){
				case 'QC':
					if(! qualitySampleMap['QC']) qualitySampleMap['QC'] = run.sample
					break
					
				case 'STDmix':
					if(! qualitySampleMap['STDmix']) qualitySampleMap['STDmix'] = run.sample
					break
				
				case 'blank':
					if(! qualitySampleMap['blank']) qualitySampleMap['blank'] = run.sample
					break
					
				default:
					def matcher = run.msAssayName =~ /(.+)_(\d+)/
					def idName = matcher[0][1]
					runNameMap[idName] = run
			}
		}
	}
	
		
}
