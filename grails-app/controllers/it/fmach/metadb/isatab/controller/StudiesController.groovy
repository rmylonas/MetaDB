package it.fmach.metadb.isatab.controller

import it.fmach.metadb.isatab.model.FEMStudy

class StudiesController {

    def index() { flash.studies = FEMStudy.list() }
	
}
