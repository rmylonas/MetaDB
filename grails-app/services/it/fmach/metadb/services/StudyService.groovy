package it.fmach.metadb.services

import it.fmach.metadb.isatab.model.FEMStudy

class StudyService {

    def saveStudy(FEMStudy study) {
		
		// first we have to save the samples (only once)
		def alreadySaved = [:]
		
		study.assays.each{ assay ->
			assay.runs.each{ run ->
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