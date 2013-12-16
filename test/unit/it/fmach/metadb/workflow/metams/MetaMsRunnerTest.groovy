package it.fmach.metadb.workflow.metams

import java.util.List;

import grails.test.mixin.*
import it.fmach.metadb.isatab.testHelper.TestDomainCreator

import org.junit.*

import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMStudy
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.isatab.model.InstrumentMethod
import it.fmach.metadb.isatab.model.Instrument
import it.fmach.metadb.isatab.model.AccessCode
import it.fmach.metadb.isatab.model.MetaMsSubmission

@Mock([FEMStudy, FEMAssay, FEMRun, InstrumentMethod, Instrument, AccessCode, MetaMsSubmission])
class MetaMsRunnerTest {
	
	static String metaMsConfDir = "resources/conf/metaMS"
	
	@Test
	public void runMetaMsTest() {
		def runner = new MetaMsRunner(metaMsConfDir)
		
		def creator = new TestDomainCreator()
		def assay = creator.createExtractedRuns()
		
//		assay.acquiredRuns.each{
//			println(it.msAssayName)
//		}
		
		def selectedMsAssayNames = ['run_2', 'run_3']
		
		// create temporary workdir
		File tmpFile = File.createTempFile("test_metams_", Long.toString(System.nanoTime()))
		tmpFile.delete()
		tmpFile.mkdir()
		String workDir = tmpFile.getAbsolutePath()
		
		println(workDir)
		
		runner.runMetaMs(workDir, assay, selectedMsAssayNames, null, null, null)
		
		// make sure metams is done
		Thread.sleep(500)
		
		def metaMsSubmission = MetaMsSubmission.get(1)
		
		// submission fails because the CDF files are missing
		assert "failed"	== metaMsSubmission.status
		assert 2 == metaMsSubmission.selectedRuns.size()
		
	}
	
	@Test
	public void selectAcquiredRunsTest() {
		def runner = new MetaMsRunner(metaMsConfDir)
		
		def creator = new TestDomainCreator()
		def assay = creator.createExtractedRuns()
		
		def selectedMsAssayNames = ['run_2', 'run_3']
		
		def selectedRuns = runner.selectAcquiredRuns(assay, selectedMsAssayNames)
		
		assert 2 == selectedRuns.size()
	}
}

