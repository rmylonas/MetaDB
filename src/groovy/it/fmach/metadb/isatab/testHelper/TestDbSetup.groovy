package it.fmach.metadb.isatab.testHelper

import it.fmach.metadb.Role
import it.fmach.metadb.User
import it.fmach.metadb.UserRole
import it.fmach.metadb.isatab.instrument.Polarity;
import it.fmach.metadb.isatab.model.FEMGroup
import it.fmach.metadb.isatab.model.FEMProject
import it.fmach.metadb.isatab.model.Instrument
import it.fmach.metadb.isatab.model.InstrumentMethod
import it.fmach.metadb.isatab.model.MetaMsDb

class TestDbSetup {

	void createInstrument(){
		
			def polarities = [Polarity.POSITIVE.metabolightsName, Polarity.NEGATIVE.metabolightsName, Polarity.ALTERNATING.metabolightsName]
			
			def metaMsDb = new MetaMsDb(name: "default", rDataPath: "some/path")
			
			def synaptMethods = [
					new InstrumentMethod(name: 'untargeted RP',
						tag: 'syn_untar_RP',
						startPattern: '1.blank-1.STDmix-4.QC',
						repeatPattern: '6.sample-1.QC',
						endPattern: '1.STDmix-1.blank',
						randomization: true, 
						metaMsDb: metaMsDb),
					new InstrumentMethod(name: 'targeted RP',
						tag: 'syn_tar_RP',
						startPattern: '1.blank-1.STDmix-4.QC',
						repeatPattern: '6.sample-1.QC',
						endPattern: '1.STDmix-1.blank',
						randomization: true, 
						metaMsDb: metaMsDb)
				]
			
			new Instrument(name: "Synapt", 
				metabolightsName: "SYNAPT HDMS (Waters)", 
				methods: synaptMethods, 
				polarities: polarities.join(","),
				chromatography: "LC"
				).save(failOnError: true)
			
			def xevoMethods = [
				new InstrumentMethod(name: 'untargeted RP',
					tag: 'xev_tar_RP',
					startPattern: '5.blank-1.STDmix-2.QC',
					repeatPattern: '3.sample-1.QC',
					endPattern: '1.STDmix-5.blank',
					randomization: true, 
					metaMsDb: metaMsDb),
			]
		
			new Instrument(name: "Xevo", 
				metabolightsName: "Xevo TQ MS (Waters)", 
				methods: xevoMethods, 
				polarities: polarities.join(","),
				chromatography: "LC").save(failOnError: true)
	
			def tsqMethods = [
				new InstrumentMethod(name: 'targeted RP',
					tag: 'tsq_tar_RP',
					startPattern: '5.blank-1.STDmix-2.QC',
					repeatPattern: '3.sample-1.QC',
					endPattern: '1.STDmix-5.blank',
					randomization: true, 
					metaMsDb: metaMsDb),
			]
			new Instrument(name: "TSQ", 
				metabolightsName: "TSQ Quantum Ultra (Thermo Scientific)", 
				methods: tsqMethods, 
				polarities: polarities.join(","),
				chromatography: "GC").save(failOnError: true)

			
	}
	
	
//	void createUsers(){
//		// set the users
//		def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true, failOnError: true)
//		def userRole = new Role(authority: 'ROLE_USER').save(flush: true, failOnError: true)
//  
//		def adminUser = new User(username: 'admin', password: '1234', workDir: '/home/mylonasr/MetaDB/data/admin')
//		adminUser.save(flush: true, failOnError: true)
//		
//		def testUser = new User(username: 'roman', password: 'namor', workDir: '/home/mylonasr/MetaDB/data/roman')
//		testUser.save(flush: true, failOnError: true)
//  
//		UserRole.create(adminUser, adminRole, true)
//		UserRole.create(testUser, userRole, true)
//		
//		//new UserRole(user: adminUser, role: adminRole).save(flush: true, insert: true, failOnError: true)
//  
//		assert User.count() == 2
//		assert Role.count() == 2
//		assert UserRole.count() == 2
//	}
	
	
	void createGroups(){
		List fulvioProjects = [new FEMProject(name: 'Wine cellar'), new FEMProject(name: 'Nomacorc') ]
		List urskaProjects = [new FEMProject(name: 'Ager melo')]
			
		def fulvioGroup = new FEMGroup(name: "Fulvio", projects:fulvioProjects).save(failOnError: true)
		def urskaGroup = new FEMGroup(name: "Urska", projects:urskaProjects).save(failOnError: true)
	}
	
	
}
