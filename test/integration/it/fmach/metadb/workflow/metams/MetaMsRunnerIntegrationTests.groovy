package it.fmach.metadb.workflow.metams

import java.util.List;

import grails.test.mixin.*
import it.fmach.metadb.User
import it.fmach.metadb.isatab.testHelper.TestDomainCreator

import org.codehaus.groovy.grails.io.support.ClassPathResource
import org.junit.*

import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMStudy
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.isatab.model.InstrumentMethod
import it.fmach.metadb.isatab.model.Instrument
import it.fmach.metadb.isatab.model.AccessCode
import it.fmach.metadb.isatab.model.MetaMsSubmission
import it.fmach.metadb.isatab.model.MetaMsDb

@Mock([FEMStudy, FEMAssay, FEMRun, InstrumentMethod, Instrument, AccessCode, MetaMsSubmission, MetaMsDb])
class MetaMsRunnerIntegrationTests {
	
	static def currentUser = new User(username: 'roman', password: 'namor', workDir: '/home/mylonasr/MetaDB/data/roman')
	static String metaMsConfDir = "resources/conf/metaMS"
	static String mzDataDir = "resources/it/fmach/metadb/workflow/metams"
	
	@Test
	public void runMetaMsTest() {
		def creator = new TestDomainCreator()
		def assay = creator.createExtractedRuns()
		
		def configDir = new ClassPathResource(metaMsConfDir).getFile().getAbsolutePath()
		def dataDir = new ClassPathResource(mzDataDir).getFile().getAbsolutePath()
		
		assay.acquiredRuns.each{
			println(it.msAssayName)
		}
		
		def selectedMsAssayNames = ['run_2', 'run_3', 'run_4']
		
		// create temporary workdir
		File tmpFile = File.createTempFile("test_metams_", Long.toString(System.nanoTime()))
		tmpFile.delete()
		tmpFile.mkdir()
		
		// set raw_files
		assay.acquiredRuns.get(1).derivedSpectraFilePath = dataDir + "/small_1.mzdata"
		assay.acquiredRuns.get(2).derivedSpectraFilePath = dataDir + "/small_2.mzdata"
		assay.acquiredRuns.get(3).derivedSpectraFilePath = dataDir + "/small_QC.mzdata"
		
		// set method
		assay.method.metaMsParameterFile = "Synapt.RP"
		
		
		def runner = new MetaMsRunner(configDir, "", "", tmpFile.getAbsolutePath())
		assay.workDir = "myWorkDir"
		assay.owner = currentUser
		assay.save(flash: true, failOnError: true)
		
		runner.runMetaMs(assay, selectedMsAssayNames, "0.0", "10.5", "comment")
		
		// make sure metams is running
		assay.refresh()
		Thread.sleep(100)
		
		def metaMsSubmission = MetaMsSubmission.get(1)
		
		// submission fails because the CDF files are missing
		assert "running"	== metaMsSubmission.status
		assert 3 == metaMsSubmission.selectedRuns.size()
		
		// check assay was saved correctly
		assert tmpFile.getAbsolutePath() + "/" +  assay.workDir + "/pipeline/1" == assay.metaMsSubmissions.get(0).workDir
			
		// after half a minute it should be done on any system
		Thread.sleep(30000)
		
		metaMsSubmission.refresh()
		assert "done"	== metaMsSubmission.status
	}
	
}

