package it.fmach.metadb.isatab.testHelper

import java.util.Date;
import java.util.List;

import it.fmach.metadb.isatab.importer.AccessCodeGenerator
import it.fmach.metadb.isatab.model.AccessCode;
import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.isatab.model.FEMSample
import it.fmach.metadb.isatab.model.FEMStudy;
import it.fmach.metadb.isatab.model.Instrument;
import it.fmach.metadb.isatab.model.InstrumentMethod;

class TestDomainCreator {

	/**
	 * create a study object for unit-testing
	 */
	FEMStudy createStudy(){
		def accessCodeGenerator = new AccessCodeGenerator()
		
		// create runs
		def runList = []
		for(i in 1..12){
			runList.add(new FEMRun(msAssayName: "assay_"+i, rowNumber: i, scanPolarity: "positive", sample: new FEMSample(name: "Sample_"+i)))
		}
		
		// create assays
		def assayList = []
		def j = 0
		for(i in 0..2){
			assayList.add(new FEMAssay(accessCode: accessCodeGenerator.getNewCode(), name: "name_"+i, shortName: "short_"+i, 
				instrument: Instrument.get(1), method: this.createMethod(), 
				instrumentPolarity: 'positive', runs: runList[j..(j+3)]))
			j += 4
		}
		
		// create study
		def study = new FEMStudy(identifier: 'study_id', iSATabFilePath: '/path', assays: assayList)
		
		return study
		
	}
	
	
	InstrumentMethod createMethod(){
		def xevoMethod = new InstrumentMethod(name: 'untargeted RP',
				tag: 'xev_tar_RP',
				startPattern: '5.blank-1.STDmix-2.QC',
				repeatPattern: '3.sample-1.QC',
				endPattern: '1.STDmix-5.blank')
		
		return xevoMethod
		
	}
	
}
