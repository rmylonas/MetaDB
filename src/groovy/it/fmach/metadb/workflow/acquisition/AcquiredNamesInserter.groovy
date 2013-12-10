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
	
	def assayNamePattern = ~/(.+?)_?(\d+)_(.+)/
	
	def addAcquiredAssayNames(FEMAssay assay, List<String> assayNames){
		def acquiredRuns = []
		def missingNames = []
		def namesNotFound = []
		
		// construct a map of [samplenames:FEMRun]
		// QC, STDmix and blank we only take ones and separately (in qualitySamples)
		def runNameMap = [:]
		def qualitySampleMap = [:]
		def runFound = [:]
		
		this.prepareRunNameMaps(assay.randomizedRuns, runNameMap, qualitySampleMap)
		
		// look at all the assayNames
		def i = 1
		assayNames.each{ name ->
			// separate the last _int part
			def matcher = name =~ assayNamePattern
			def idName = matcher ? matcher[0][3] : null
			// def number = matcher[0][2]
			
			// if it is a QC, stdMix or blank, we just add a run with this name
			def nameMatcher = idName =~ /(?i)(QC|STDmix|blank)_.*/
			if(nameMatcher){
				def lcName = (nameMatcher[0][1]).toLowerCase()									
				acquiredRuns.add(
					new FEMRun(msAssayName: name.trim(), 
							rowNumber: i, scanPolarity: assay.instrumentPolarity, 
							status: 'acquired', sample: qualitySampleMap[lcName])
				)
			// if it's a sample
			}else{
				// if we can map the name
				if(runNameMap[idName]){
					
					// if a run with same name is already added, we add it in "additionalRuns"
					if(runFound[idName]){
						FEMRun alreadyFound = runFound[idName]
						
						// prepare and add the additional run
						FEMRun additionalRun = deepCopier.deepCopy(alreadyFound)
						additionalRun.status = "acquired"
						additionalRun.msAssayName = name.trim()
						
						if(alreadyFound.additionalRuns){
							alreadyFound.additionalRuns << additionalRun
						}else{
							alreadyFound.additionalRuns = [additionalRun]
						} 
					}else{
						FEMRun run = deepCopier.deepCopy(runNameMap[idName])
						
						// change the status and msAssayName
						run.status = "acquired"
						run.msAssayName = name.trim()
						run.rowNumber = i
						
						acquiredRuns << run
						runFound[idName] = run
	//					runNameMap.remove(idName)
					}
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
		runNameMap.each {name, run ->
			if(! runFound[name]) missingNames << name
		}
		
		if(missingNames.size() == 0 && namesNotFound == 0){
			return null
		}
		
		return [missingNames, namesNotFound]
		
	}
	
	
	private def prepareRunNameMaps(def runList, def runNameMap, def qualitySampleMap){
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
					def matcher = run.msAssayName =~ assayNamePattern
					def idName = matcher ? matcher[0][3] : null
					runNameMap[idName] = run
			}
		}
	}
	
		
}
