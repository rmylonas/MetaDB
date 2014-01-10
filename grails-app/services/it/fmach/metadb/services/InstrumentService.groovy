package it.fmach.metadb.services

import it.fmach.metadb.isatab.model.Instrument
import it.fmach.metadb.isatab.model.InstrumentMethod
import it.fmach.metadb.isatab.model.FEMAssay

class InstrumentService {

    def delete(Instrument instrument) {
		// let's check if this group is used by some assays
		def assayList = FEMAssay.findByInstrument(instrument)
		
		if(assayList) throw new RuntimeException("Instrument [" + instrument.name + "] is used by some Assays. Please delete those Assays first.")
		
		// delete all methods belonging to this instrument
		instrument.methods.each{
			it.delete()
		}
		
		instrument.delete()
    }
	
	def deleteMethod(Instrument instrument, InstrumentMethod instrumentMethod) {
		// let's complain if the method is used by some assays
		def assayList = FEMAssay.findByMethod(instrumentMethod)
		if(assayList) throw new RuntimeException("Method [" + instrumentMethod.name + "] is used by some Assays. Please delete those Assays first.")
		
		// and delete
		instrument.removeFromMethods(instrumentMethod)
		instrument.save(flush: true, failOnError: true)
		instrumentMethod.delete()
	}
	
}
