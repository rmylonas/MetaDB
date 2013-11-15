package it.fmach.metadb.isatab.testHelper

import it.fmach.metadb.isatab.instrument.Polarity;
import it.fmach.metadb.isatab.model.Instrument
import it.fmach.metadb.isatab.model.InstrumentMethod

class InstrumentCreator {

	void createInstrument(){
		
			def polarities = [Polarity.POSITIVE.metabolightsName, Polarity.NEGATIVE.metabolightsName, Polarity.ALTERNATING.metabolightsName]
			
			def synaptMethods = [
					new InstrumentMethod(name: 'untargeted RP',
						tag: 'syn_untar_RP',
						startPattern: '1.blank-1.STDmix-4.QC',
						repeatPattern: '6.sample-1.QC',
						endPattern: '1.STDmix-1.blank'),
					new InstrumentMethod(name: 'targeted RP',
						tag: 'syn_tar_RP',
						startPattern: '1.blank-1.STDmix-4.QC',
						repeatPattern: '6.sample-1.QC',
						endPattern: '1.STDmix-1.blank')
				]
			
			new Instrument(name: "Synapt", metabolightsName: "SYNAPT HDMS (Waters)", methods: synaptMethods, polarities: polarities).save(failOnError: true)
			
			def xevoMethods = [
				new InstrumentMethod(name: 'untargeted RP',
					tag: 'xev_tar_RP',
					startPattern: '5.blank-1.STDmix-2.QC',
					repeatPattern: '3.sample-1.QC',
					endPattern: '1.STDmix-5.blank'),
			]
		
			new Instrument(name: "Xevo", metabolightsName: "Xevo TQ MS (Waters)", methods: xevoMethods, polarities: polarities).save(failOnError: true)
	
			def tsqMethods = [
				new InstrumentMethod(name: 'targeted RP',
					tag: 'tsq_tar_RP',
					startPattern: '5.blank-1.STDmix-2.QC',
					repeatPattern: '3.sample-1.QC',
					endPattern: '1.STDmix-5.blank'),
			]
			new Instrument(name: "TSQ", metabolightsName: "TSQ Quantum Ultra (Thermo Scientific)", methods: tsqMethods, polarities: polarities).save(failOnError: true)

			
	}
	
}
