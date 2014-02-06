import it.fmach.metadb.Role
import it.fmach.metadb.User
import it.fmach.metadb.UserRole
import it.fmach.metadb.isatab.instrument.Polarity
import it.fmach.metadb.isatab.model.Instrument
import it.fmach.metadb.isatab.model.InstrumentMethod
import it.fmach.metadb.isatab.model.FEMGroup
import it.fmach.metadb.isatab.model.FEMProject
import it.fmach.metadb.user.UserWorkDirGenerator;
import org.codehaus.groovy.grails.commons.GrailsApplication

class BootStrap {
	
	GrailsApplication grailsApplication
	
    def init = { servletContext ->

		// create default user		
		if(! User.count()){
			// set the users
			def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true, failOnError: true)
			def userRole = new Role(authority: 'ROLE_USER').save(flush: true, failOnError: true)
			
			def dataPath = grailsApplication.config.metadb.dataPath + "/"
			def adminDir = dataPath + 'admin'
			def testUserDir = dataPath + 'test'
			
			def adminUser = new User(username: 'admin', password: 'admin', workDir: 'admin')
			adminUser.save(flush: true, failOnError: true)
			
			def testUser = new User(username: 'test', password: 'test', workDir: 'test')
			testUser.save(flush: true, failOnError: true)
			
			UserRole.create(adminUser, adminRole, true)
			UserRole.create(testUser, userRole, true)
			
			// delete and create new directories
			 new File(adminDir).deleteDir()
			 new File(testUserDir).deleteDir()
			
			def dirGenerator = new UserWorkDirGenerator()
			dirGenerator.createWorkDir(adminDir)
			dirGenerator.createWorkDir(testUserDir)
	  
			assert User.count() == 2
			assert Role.count() == 2
			assert UserRole.count() == 2
		}		
		
		// create the instruments
		if (!Instrument.count()) {
			
			// def polarities = [Polarity.POSITIVE.metabolightsName, Polarity.NEGATIVE.metabolightsName, Polarity.ALTERNATING.metabolightsName]
			
			def synaptMethods = [
					new InstrumentMethod(name: 'untargeted RP', 
						tag: 'syn_untar_RP',
						startPattern: '1.blank-1.STDmix-4.QC',
						repeatPattern: '6.sample-1.QC',
						// according to Domenico:
						// 6.sample-1.QC-6.sample-1.QC-1.STDmix
						endPattern: '1.STDmix-1.blank',
						randomization: true,
						metaMsParameterFile: 'Synapt.QTOF.RP.RData'),
					new InstrumentMethod(name: 'Import data (no randomization)',
						tag: 'syn_md_RP',
						randomization: false,
						metaMsParameterFile: 'Synapt.QTOF.RP.RData')
				]
			new Instrument(name: "Synapt", 
							metabolightsName: "SYNAPT HDMS (Waters)", 
							methods: synaptMethods, 
							//polarities: polarities.join(","),
							chromatography: "LC").save(flush: true, failOnError: true)
			
			def xevoMethods = [
				new InstrumentMethod(name: 'untargeted RP',
					tag: 'xev_tar_RP',
					startPattern: '5.blank-1.STDmix-2.QC',
					repeatPattern: '3.sample-1.QC',
					endPattern: '1.STDmix-5.blank',
					randomization: true),
				new InstrumentMethod(name: 'Import data (no randomization)',
					tag: 'xev_tar_RP',
					randomization: false)
			]	
			new Instrument(name: "Xevo", 
							metabolightsName: "Xevo TQ MS (Waters)", 
							methods: xevoMethods, 
							//polarities: polarities.join(","),
							chromatography: "LC").save(flush: true, failOnError: true)	
			
			def tsqMethods = [
				new InstrumentMethod(name: 'targeted RP',
					tag: 'tsq_tar_RP',
					startPattern: '5.blank-1.STDmix-2.QC',
					repeatPattern: '3.sample-1.QC',
					endPattern: '1.STDmix-5.blank',
					randomization: true),
				new InstrumentMethod(name: 'Import data (no randomization)',
					tag: 'xev_tar_RP',
					randomization: false)
			]
			new Instrument(name: "TSQ", 
				metabolightsName: "TSQ Quantum Ultra (Thermo Scientific)", 
				methods: tsqMethods,
				chromatography: "GC").save(flush: true, failOnError: true)
			
		}
		
		// create groups and projects
		if(!FEMGroup.count()){
			List fulvioProjects = [new FEMProject(name: 'Wine cellar'), new FEMProject(name: 'Nomacorc') ]
			List urskaProjects = [new FEMProject(name: 'Ager melo')]
				
			def fulvioGroup = new FEMGroup(name: "DQAN QA", description: "Gruppo Ricerca Qualità Alimentare (Fulvio Mattivi)", projects:fulvioProjects).save(flush: true, failOnError: true)
			def urskaGroup = new FEMGroup(name: "DQAN M", description: "Piattaforma Metabolomica (Urska Vrhovsek)", projects:urskaProjects).save(flush: true, failOnError: true)
			
		}
    }
	
    def destroy = {
    }
	
}
