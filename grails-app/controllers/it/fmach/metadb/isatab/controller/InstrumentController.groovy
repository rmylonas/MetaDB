package it.fmach.metadb.isatab.controller

import it.fmach.metadb.isatab.model.Instrument

class InstrumentController {

    def index() { 
		// list all instruments
		flash.instrumentList = Instrument.list()
	}
}
