package it.fmach.metadb.isatab.importer

import it.fmach.metadb.User
import it.fmach.metadb.isatab.model.AccessCode;
import it.fmach.metadb.isatab.model.FEMAssay;
import it.fmach.metadb.isatab.model.FEMSample;
import it.fmach.metadb.isatab.model.FEMStudy;
import it.fmach.metadb.isatab.model.Instrument;
import it.fmach.metadb.isatab.testHelper.TestDbSetup

import org.junit.*

import grails.test.mixin.*

@Mock([FEMStudy, FEMAssay, FEMSample, AccessCode, Instrument, User])
class StudyMergerTest {

	static String rootDir = "test/data/org/isatools/isacreator/io/importisa/"
	static def currentUser = new User(username: 'roman', password: 'namor', workDir: '/home/mylonasr/MetaDB/data/roman')

	@Test
	void testCheckStudy(){
		// create instruments
		def creator = new TestDbSetup()
		creator.createInstrument()
		
		String configDir = rootDir + "MetaboLightsConfig20130507"
		String isatabDir = rootDir + "Wine_Storage"
		
		def workDir = File.createTempFile("test_workdir", "")
		workDir.delete();
		workDir.mkdir();
	
		IsatabImporter importer = new IsatabImporterImpl(configDir, workDir.getAbsolutePath(), currentUser)
		FEMInvestigation investigation = importer.importIsatabFiles(isatabDir)
		Boolean success = investigation.isaParsingInfo.success
		assert success
	
		List<FEMStudy> studyList = investigation.studyList
		def study = studyList.get(0)
		
		// remove one assay and save the study
		study.assays.remove(5)
		study.save(flush: true, failOnError: true)
		def studyDb = FEMStudy.list().get(0)
		
		// reload the same study
		def reInvestigation = importer.importIsatabFiles(isatabDir)
		def reStudy = reInvestigation.studyList.get(0)
		
		// and see what the merger says
		def merger = new StudyMerger()
		def mergeInfo = merger.checkStudy(reStudy)
		
		assert 5 == mergeInfo.size()
		
	}
	
	
	@Test
	void testMergeStudy(){
		// create instruments
		def creator = new TestDbSetup()
		creator.createInstrument()
		
		String configDir = rootDir + "MetaboLightsConfig20130507"
		String isatabDir = rootDir + "Wine_Storage"
		
		def workDir = File.createTempFile("test_workdir", "")
		workDir.delete();
		workDir.mkdir();
	
		IsatabImporter importer = new IsatabImporterImpl(configDir, workDir.getAbsolutePath(), currentUser)
		FEMInvestigation investigation = importer.importIsatabFiles(isatabDir)
		Boolean success = investigation.isaParsingInfo.success
		assert success
	
		List<FEMStudy> studyList = investigation.studyList
		def study = studyList.get(0)
		
		// remove one assay and save the study
		study.assays.remove(5)
		study.save(flush: true, failOnError: true)
		def studyDb = FEMStudy.list().get(0)
		
		// reload the same study
		def reInvestigation = importer.importIsatabFiles(isatabDir)
		def reStudy = reInvestigation.studyList.get(0)
		
		// and see what the merger says
		def merger = new StudyMerger()
		def nrAssaysAdded = merger.mergeStudy(reStudy)
		
		assert 1 == nrAssaysAdded
		
	}
	
}
