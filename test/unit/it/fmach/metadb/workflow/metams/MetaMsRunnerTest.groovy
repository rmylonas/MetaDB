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
import it.fmach.metadb.isatab.model.MetaMsDb

@Mock([FEMStudy, FEMAssay, FEMRun, InstrumentMethod, Instrument, AccessCode, MetaMsSubmission, MetaMsDb])
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
		
		runner.runMetaMs(workDir, assay, selectedMsAssayNames, "1.2", "10.5")
		
		// make sure metams is done
		Thread.sleep(500)
		
		def metaMsSubmission = MetaMsSubmission.get(1)
		
		// submission fails because the CDF files are missing
		assert "failed"	== metaMsSubmission.status
		assert 2 == metaMsSubmission.selectedRuns.size()
		
	}
	
	
	@Test
	public void constructCommandTest(){
		def runner = new MetaMsRunner(metaMsConfDir)
		runner.workDir = "/some/path"
		
		def creator = new TestDomainCreator()
		def assay = creator.createExtractedRuns()

		def command = runner.constructCommand(assay, null, null)
		assert "Rscript resources/conf/metaMS/runMetaMS.R -i LC -p negative -f null -s some/path -o /some/path" == command
		
		def commandWithRt = runner.constructCommand(assay, "1.234", "8.875")
		
		assert commandWithRt.contains("-m 1.234 -x 8.875")
	}
	
	
	@Test
	public void getInstrumentChromatographyTest() {
		def runner = new MetaMsRunner(metaMsConfDir)
		
		def creator = new TestDomainCreator()
		def assay = creator.createExtractedRuns()
		
		def chrom = runner.getInstrumentChromatography(assay)
		
		assert "LC" == chrom
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

