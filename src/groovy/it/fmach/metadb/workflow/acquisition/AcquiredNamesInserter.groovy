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
	
	//def assayNamePattern = ~/(.+?)_?(\d+)_(.+)/
	
	def addAcquiredAssayNames(FEMAssay assay, List<String> assayNames){
		// list of acquired runs
		def acquiredRuns = []
		
		// names which were not found
		def namesNotFound = []
		
		// names that were found multiple times
		def runNameAlready = []
		
		// construct a map of [samplenames:FEMRun]
		// QC, STDmix and blank we only take ones and separately (in qualitySamples)
		def qualitySampleMap = [:]
		def sampleMap = [:]
		
		Set sampleFound = []
		Set runFound = []
		Set runNames = []
		
		this.prepareRunNameMaps(assay.randomizedRuns, qualitySampleMap, sampleMap, runNames)
		
		// look at all the assayNames
		def i = 1
		assayNames.each{ name ->
			// throw an exception if the run name is not unique
			if(runNameAlready.contains(name)) throw new RuntimeException("[" + name + "] is used multiple times. Names have to be unique.")			
			runNameAlready << name
			
			// if it is a QC, stdMix or blank, we just add a run with this name
			def nameMatcher = name =~ /(?i)(QC|STDmix|blank)_.*/
			if(nameMatcher){
				def lcName = (nameMatcher[0][1]).toLowerCase()									
				acquiredRuns.add(
					new FEMRun(msAssayName: name.trim(), 
							rowNumber: i, scanPolarity: assay.instrumentPolarity, 
							status: 'acquired', sample: qualitySampleMap[lcName])
				)
			// if it's a sample
			}else{
				// let's look in the sample names
				def newRun
				sampleMap.each{ k , v ->
					if(name.contains(k)){
						// create a new run
						newRun = this.copyRun(v, i, sampleFound, name)
						runFound << v.msAssayName
					}
				}
			
				if(newRun){
					acquiredRuns << newRun
				}else{
					namesNotFound << name
				}
				
			}
			
			i++
		}
		
		// add acquiredRuns
		assay.acquiredRuns = acquiredRuns
		assay.status = "acquired"
		
		// add missing names
		def missingNames = []
		
		runNames.each {name ->
			if(! runFound.contains(name)) missingNames << name
		}
		
		if(missingNames.size() == 0 && namesNotFound == 0){
			return null
		}
		
		return [missingNames, namesNotFound]
		
	}
	
	
	private def copyRun(FEMRun run, int i, Set sampleFound, String assayName){
		FEMRun newRun = deepCopier.deepCopy(run)
		
		// change the status and msAssayName
		newRun.status = "acquired"
		newRun.msAssayName = assayName.trim()
		newRun.rowNumber = i
		
		// if a run with same sample is already added, we add the tag "additionalRuns"
		newRun.additionalRun = (sampleFound.contains(run.sample.name)) ? (true) : (false)
		sampleFound << run.sample.name
		
		return newRun
	}
	
	private def prepareRunNameMaps(def runList, def qualitySampleMap, def sampleMap, Set runNames){
		runList.each{ run ->
			def lcSampleName = run.sample.name.toLowerCase()
			switch (lcSampleName){
				case 'qc':
					if(! qualitySampleMap['qc']) qualitySampleMap['qc'] = run.sample
					break
					
				case 'stdmix':
					if(! qualitySampleMap['stdmix']) qualitySampleMap['stdmix'] = run.sample
					break
				
				case 'blank':
					if(! qualitySampleMap['blank']) qualitySampleMap['blank'] = run.sample
					break
					
				default:
					sampleMap[run.sample.name] = run
					runNames << run.msAssayName
			}
		}
	}
	
		
}
