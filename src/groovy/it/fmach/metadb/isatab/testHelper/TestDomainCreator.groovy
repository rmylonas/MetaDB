package it.fmach.metadb.isatab.testHelper

import java.util.Date;
import java.util.List;

import it.fmach.metadb.User
import it.fmach.metadb.isatab.importer.AccessCodeGenerator
import it.fmach.metadb.isatab.model.AccessCode;
import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.isatab.model.FEMSample
import it.fmach.metadb.isatab.model.FEMStudy;
import it.fmach.metadb.isatab.model.Instrument;
import it.fmach.metadb.isatab.model.InstrumentMethod;

class TestDomainCreator {

	static def currentUser = new User(username: 'roman', password: 'namor', workDir: '/home/mylonasr/MetaDB/data/roman')
	
	/**
	 * create a study object for unit-testing
	 */
	FEMStudy createStudy(){
		def accessCodeGenerator = new AccessCodeGenerator()
		
		def method = this.createMethod()
		
		// create runs
		def runList = []
		for(i in 1..12){
			runList.add(new FEMRun(msAssayName: "assay_"+i, 
									rowNumber: i, 
									scanPolarity: "positive", 
									sample: new FEMSample(name: "Sample_"+i, factorJSON: '{"Bottling Type":"N2","Bottling time":"10","Wine":"Pinot Gris"}')
									)
			)
		}
		
		// create assays
		def assayList = []
		def j = 0
		for(i in 0..2){
			assayList.add(new FEMAssay(status: "randomized", 
										accessCode: accessCodeGenerator.getNewCode(), 
										name: "name_"+i, 
										shortName: "short_"+i,
										method: method, 
										instrumentPolarity: 'positive', 
										runs: runList[j..(j+3)], 
										randomizedRuns: runList[j..(j+3)],
										instrument: this.createInstrument(),
										owner: currentUser)
							)
			j += 4
		}
		
		// create study
		def study = new FEMStudy(identifier: 'study_id', iSATabFilePath: '/path', assays: assayList, owner: currentUser)
		
		return study
		
	}
	
	FEMAssay createRandomizedRuns(){
		def accessCodeGenerator = new AccessCodeGenerator()
		
		// create runs
		def runList = []
		for(i in 1..12){
			runList.add(new FEMRun(msAssayName: "run_"+i, 
									rowNumber: i,
									scanPolarity: "positive", 
									sample: new FEMSample(name: "Sample_"+i,
															factorJSON: '{"Bottling Type":"O2","Bottling time":"5","Wine":"Muller Thurgau  "}')
									))
		}
		
		// create assay
		def assay = new FEMAssay(accessCode: accessCodeGenerator.getNewCode(),
			name: "randomized_assay",
			shortName: "shortname",
			method: this.createMethod(),
			instrumentPolarity: 'positive',
			owner: currentUser,
			randomizedRuns: runList)
	}
	
	
	InstrumentMethod createMethod(){
		
		def xevoMethod = new InstrumentMethod(name: 'untargeted RP',
				tag: 'xev_tar_RP',
				startPattern: '5.blank-1.STDmix-2.QC',
				repeatPattern: '3.sample-1.QC',
				endPattern: '1.STDmix-5.blank',
				randomization: true,
				metaMsParameterFile: 'some/path'
				)
	
		
		return xevoMethod
		
	}
	
	
	Instrument createInstrument(){
		
		def xevoMethod = new InstrumentMethod(name: 'untargeted RP',
				tag: 'xev_tar_RP',
				startPattern: '5.blank-1.STDmix-2.QC',
				repeatPattern: '3.sample-1.QC',
				endPattern: '1.STDmix-5.blank',
				randomization: true,
				metaMsParameterFile: 'some/path'
				)
		
		def instr = new Instrument(name: 'Xevo',
			metabolightsName: 'Xevo Metabolights',
			chromatography: "LC",
			methods: [xevoMethod])
		
		return instr
		
	}
	
	
	FEMAssay createMarynkasRuns(){
		def accessCodeGenerator = new AccessCodeGenerator()
		
		// create runs
		def runList = []
		
		runList.add(new FEMRun(msAssayName: "solv_130827185620",
			rowNumber: 1,
			scanPolarity: "positive",
			sample: new FEMSample(name: "Solvent", factorJSON: '{}')
			)
		)
		
		runList.add(new FEMRun(msAssayName: "JALI108_DietB_V3",
			rowNumber: 2,
			scanPolarity: "positive",
			sample: new FEMSample(name: "JALI108_DietB_V3", factorJSON: '{}')
			)
		)
		
		runList.add(new FEMRun(msAssayName: "Creat55_Cinn11_130828044500",
			rowNumber: 3,
			scanPolarity: "positive",
			sample: new FEMSample(name: "Creat55", factorJSON: '{}')
			)
		)
		
		runList.add(new FEMRun(msAssayName: "JALI015_DietC_V2_130829010454",
			rowNumber: 4,
			scanPolarity: "positive",
			sample: new FEMSample(name: "ALI015_DietC_V2", factorJSON: '{}')
			)
		)
		
		// create assay
		def assay = new FEMAssay(accessCode: accessCodeGenerator.getNewCode(),
			name: "randomized_assay",
			shortName: "shortname",
			method: this.createMethod(),
			instrumentPolarity: 'positive',
			randomizedRuns: runList,
			workDir: "myWorkDir")
		
	}
	
	
	FEMAssay createRandomizedRunsQCFirst(){
		def accessCodeGenerator = new AccessCodeGenerator()
		
		// create runs
		def runList = []
		
		def tag = "_tag"
		def pre = "AA_"
		
		// a QC first
		runList.add(new FEMRun(msAssayName: "AA_001_QC_tag",
									rowNumber: 1,
									scanPolarity: "positive",
									sample: new FEMSample(name: "QC",
															factorJSON: '{}')
									))
		
		// and then the others
		for(i in 2..12){
			runList.add(new FEMRun(msAssayName: pre + sprintf("%d3", i) + "_Sample_" + (i-1) + tag,
									rowNumber: i,
									scanPolarity: "positive",
									sample: new FEMSample(name: "Sample_"+ (i-1),
															factorJSON: '{"Bottling Type":"O2","Bottling time":"5","Wine":"Muller Thurgau  "}')
									))
		}
		
		// create assay
		def assay = new FEMAssay(accessCode: accessCodeGenerator.getNewCode(), 
			name: "randomized_assay", 
			shortName: "shortname",
			method: this.createMethod(),
			instrumentPolarity: 'positive', 
			randomizedRuns: runList,
			workDir: "myWorkDir")
	}
	
	
	FEMAssay createExtractedRuns(){
		def accessCodeGenerator = new AccessCodeGenerator()
		
		// create runs
		def runList = []
		for(i in 1..12){
			runList.add(new FEMRun(msAssayName: "run_"+i,
									rowNumber: i,
									status: 'extracted',
									scanPolarity: "negative",
									sample: new FEMSample(name: "Sample_"+i,
															factorJSON: '{"Bottling Type":"O2","Bottling time":"5","Wine":"Muller Thurgau  "}')
									))
		}
		
		// create assay
		def assay = new FEMAssay(accessCode: accessCodeGenerator.getNewCode(), 
			name: "randomized_assay", 
			shortName: "shortname",
			method: this.createMethod(),
			instrumentPolarity: 'negative', 
			acquiredRuns: runList,
			instrument: this.createInstrument(),
			workDir: "myWorkDir")
	}

	
}
