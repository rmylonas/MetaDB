package it.fmach.metadb.services

import it.fmach.metadb.isatab.model.FEMStudy

class StudyService {
	
	def assayService
	
	def delete(FEMStudy study){
		def assaySize = study.assays.size()
		for(def i=0; i < assaySize; i++){
			// delete the first entry till none is left
			assayService.delete(study.assays.get(0))
		}
		
		study.delete()
	}

    def saveStudy(FEMStudy study) {
		
		// first we have to save the samples (only once)
		def alreadySaved = [:]
		
		study.assays.each{ assay ->
			
			// save the runs
			assay.runs.each{ run ->
				if(alreadySaved[run.sample] == null){
					run.sample.save(flush: true, failOnError: true)			
					alreadySaved[run.sample] = true
				}
				run.save(flush: true, failOnError: true)
			}
			
			// save randomizedRuns
			assay.randomizedRuns.each{ run ->
				if(alreadySaved[run.sample] == null){
					run.sample.save(flush: true, failOnError: true)
					alreadySaved[run.sample] = true
				}
				run.save(flush: true, failOnError: true)
			}
			
			assay.save(flush: true, failOnError: true)

		}
		
		// then the rest
		study.save(flush: true, failOnError: true)
    }
}
