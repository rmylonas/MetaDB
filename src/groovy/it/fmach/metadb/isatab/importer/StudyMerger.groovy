package it.fmach.metadb.isatab.importer

import it.fmach.metadb.isatab.model.FEMStudy

class StudyMerger {

	/**
	 * Checks if a Study with the same identifier exists already.
	 * Gives back a Map containing the names of Assays which exist already 
	 * 
	 * @param study
	 * @return
	 */
	def checkStudy(FEMStudy study){
		// look if we find a study with the same identifier
		def dbStudy = FEMStudy.findByIdentifier(study.identifier)		
		
		// if we find, we compare the assays and see if there are new ones
		def already = [:]
		if(dbStudy){
			// get all assay-names
			def assayMap = [:]
			dbStudy.assays.each{
				assayMap[it.name] = true
			}
			
			study.assays.each{
				if(assayMap[it.name]) already[it.name] = true	
			}
		}
		
		return already
	}
	
	
	/**
	 * Merge the provided study with the one from the database and saves it.
	 * Returns the number of assays inserted
	 * 
	 * @param study
	 * @return
	 */
	def mergeStudy(FEMStudy study){
		// look for a study with the same identifier
		def dbStudy = FEMStudy.findByIdentifier(study.identifier)
		
		// load all samples which where already saved
		def sampleMap = [:]
		dbStudy.assays.each{ assay ->
			assay.randomizedRuns.each{
				sampleMap[it.sample.name] = it.sample
			}
		}
		
		def added = 0
		
		// we add additional assays to the existing FEMStudy and save all
		if(dbStudy){
			// get all assay-names
			def assayMap = [:]
			dbStudy.assays.each{
				assayMap[it.name] = true
			}
			
			study.assays.each{ assay ->
				// if its a new one
				if(! assayMap[assay.name]){
					// add any necessary samples from randomizedRuns
					assay.randomizedRuns.each{
						// if it was already saved before
						if(sampleMap[it.sample.name]){
							it.sample = sampleMap[it.sample.name]
						}else{
							it.sample.save(flush: true, failOnError: true)
							sampleMap[it.sample.name] = it.sample
						}
					}
					
					// and from runs
					assay.runs.each{
						// if it was already saved before
						if(sampleMap[it.sample.name]){
							it.sample = sampleMap[it.sample.name]
						}else{
							it.sample.save(flush: true, failOnError: true)
							sampleMap[it.sample.name] = it.sample
						}
					}
					
					// set the group and project from loaded study
					assay.group = dbStudy.group
					assay.project = dbStudy.project
					
					// save the assay and add it to the run
					assay.save(flush: true, failOnError: true)
					dbStudy.addToAssays(assay)
					added ++
				}
			}
			
			// let's save the new study
			dbStudy.save(flush: true, failOnError: true)
		}
		
		return added
	}
	
}
