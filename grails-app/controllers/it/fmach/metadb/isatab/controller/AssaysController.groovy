package it.fmach.metadb.isatab.controller

import it.fmach.metadb.isatab.model.FEMAssay;
import it.fmach.metadb.isatab.model.FEMStudy;

class AssaysController {

    def index() {
		String studyId = params['id']
		
		// if a study id is provided we load only the concerning assays
		if(studyId){
			def study = FEMStudy.get(studyId.toLong())
			flash.assays = study.assays
		}else{
			flash.assays = FEMAssay.list()
		}
	}
}
