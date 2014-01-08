package it.fmach.metadb.isatab.controller

import it.fmach.metadb.isatab.model.Instrument

class InstrumentController {

    def index() { 
		// list all instruments
		flash.instrumentList = Instrument.list()
	}
	
	def newInstrument() {}
	
	def saveNewInstrument(){
		def newInstr = new Instrument(name: params.name, 
			metabolightsName: params.metabolightsName,
			bookingSystemName: params.bookingSystemName,
			chromatography: params.chromatography,)
		
		// check validity and save if ok		
		if(newInstr.validate()){
			newInstr.save(flush: true)
			flash.message = "New instrument was created"
		}else{
			flash.error = newInstr.errors.allErrors.join(" and ")
		}
		
		redirect(action: 'index')
	}
	
	def detail(){
		// load this instrument if params.id is provided
		if(params.id) session.instrument = Instrument.get(params.id)
			
		if(! session.instrument) throw new RuntimeException("missing params.id")
	}
	
}
