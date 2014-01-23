package it.fmach.metadb.isatab.importer


import it.fmach.metadb.User
import it.fmach.metadb.isatab.model.AccessCode;
import it.fmach.metadb.isatab.model.FEMAssay;
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.isatab.model.FEMSample
import it.fmach.metadb.isatab.model.FEMStudy;
import it.fmach.metadb.isatab.model.Instrument;
import it.fmach.metadb.isatab.testHelper.TestDbSetup

import org.apache.commons.io.FileUtils
import org.junit.*

import grails.test.mixin.*
import it.fmach.metadb.isatab.testHelper.TestDbSetup

@Mock([FEMStudy, FEMAssay, AccessCode, Instrument, User])
class LocalIsatabImporterTest {

	static String rootDir = "test/data/org/isatools/isacreator/io/importisa/"
	static def currentUser = new User(username: 'roman', password: 'namor', workDir: '/home/mylonasr/MetaDB/data/roman')
	static String configDir = rootDir + "MetaboLightsConfig20130507"
	
	@Test
	void testCheckUploadFolder(){
		// setup test
		def workDir = this.setupTest()
		
		def importer = new LocalIsatabImporter(configDir, workDir, currentUser)
		
		assert importer.checkUploadFolder()
	}
	
	@Test
	void testImportIsatabFile(){
		// setup test
		def workDir = this.setupTest()
		
		def importer = new LocalIsatabImporter(configDir, workDir, currentUser)
		def investigation = importer.importIsatabFile()
		
		Boolean success = investigation.isaParsingInfo.success
		assert success

		List<FEMStudy> studyList = investigation.studyList

		// check study content
		assert 1 == studyList.size()
		FEMStudy study = studyList.get(0)
		assert "Wine_Storage" == study.identifier
		assert "Metabolic changes during wine storage" == study.title

		// check assay content
		assert 6 == study.assays.size
		FEMAssay assay = study.assays.get(0)
		assert "a_wine_storage_metabolite profiling_mass spectrometry-5.txt" == assay.name
		assert "wine_storage-5" == assay.shortName
		assert "Xevo TQ MS (Waters)" == assay.instrument.metabolightsName

		// check run content
		assert 90 == assay.runs.size()
		FEMRun run = assay.runs.get(0)
		assert assay.protocolJSON.contains("MS:ACQUITY UPLC")
		assert "Q034_01_00_R_MRM" == run.msAssayName
		assert "0001_R" == run.sample.name
		assert "T:experimentsXevo_Stefania_Maggio2013.PROData" == run.rawSpectraFilePath
		assert "" == run.derivedSpectraFilePath

		// check sample content
		FEMSample sample = run.sample
		assert "0001_R" == sample.name
		assert "NCBITaxon:Vitis vinifera" == sample.organism
		assert "Wine" == sample.organismPart
		assert '{"Sample type":"Sample","Storage type":"Reference","Storage time":"0"}' == sample.factorJSON
	}
	
	
	private String setupTest(){
		// create instruments
		def creator = new TestDbSetup()
		creator.createInstrument()
		
		File isatabDir = new File(rootDir + "Wine_Storage")
		
		def workDir = File.createTempFile("test_localupload", "")
		workDir.delete()
		
		def uploadDir = new File(workDir.absolutePath + "/upload")
		
		FileUtils.copyDirectory(isatabDir, uploadDir)
		
		// create the data dir
		def dataDir = new File(workDir.absolutePath + "/data")
		dataDir.mkdir()
		
		return workDir.absolutePath
	}
	

	
}
