package it.fmach.metadb.services

import java.util.List;

import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMStudy


class AssayService {
	
	def delete(FEMAssay assay){
		
		// get the lists to delete them afterwards
		def runs = assay.runs
		def randomizedRuns = assay.randomizedRuns
		def acquiredRuns = assay.acquiredRuns
		def metaMsSubmissions = assay.metaMsSubmissions		
		
		// remove assay from parent Study
		def study = FEMStudy.withCriteria {
			assays {
			  idEq(assay.id)
			}
		 }
		
		study[0].removeFromAssays(assay)
		
		assay.delete()
		
		// and now we can remove the corresponding runs and submission
		runs.each{ it.delete() }
		randomizedRuns.each{ it.delete() }
		metaMsSubmissions.each{ it.delete() }
		
		acquiredRuns.each { run ->
			// don't forget to delete the additional runs as well!
			if(run.additionalRuns){
				def m = []
				m += run.additionalRuns
				m.each { additional ->
					run.removeFromAdditionalRuns(additional)
					additional.delete()
				}
			}
			
			// and then we delete the runs
			run.delete(flush: true)
		}
		
	}

	def cleanAcquisitionRuns(FEMAssay assay){		
		def l = []
		l += assay.acquiredRuns
		
		l.each { run ->
			// delete the runs
			assay.removeFromAcquiredRuns(run)
			run.delete(flush: true)
		}
	}
	
	
	def saveAssayWithAcquisitions(FEMAssay assay) {
//		// save acquired runs first
		assay.acquiredRuns.each{ run ->
			run.save(flush: true, failOnError: true)
		}
		// and then the assay
		assay.save(flush: true, failOnError: true)
	}

}
