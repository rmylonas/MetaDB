package it.fmach.metadb.isatab.controller

import it.fmach.metadb.isatab.model.FEMAssay;
import it.fmach.metadb.isatab.model.FEMRun;

class RunsController {
	
    def index() { 
		String assayId = params['id']
		
		// if a assay id is provided we load only the concerning runs
		if(assayId){
			def assay = FEMAssay.get(assayId.toLong())
			flash.runs = assay.runs
		}else{
			flash.runs = FEMRun.list()
		}
	}
}
