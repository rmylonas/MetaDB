package it.fmach.metadb.services

import it.fmach.metadb.isatab.model.FEMAssay;


class AssayService {

	def cleanAcquisitionRuns(FEMAssay assay){
		def l = []
		l += assay.acquiredRuns
		
		l.each { run ->
			
			// don't forget to delete the additional runs as well!
			def m = []
			m += run.additionalRuns
			m.each { additional ->
				run.removeFromAdditionalRuns(additional)
				additional.delete(flush: true)
			}
			
			// and then we delete the runs
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
