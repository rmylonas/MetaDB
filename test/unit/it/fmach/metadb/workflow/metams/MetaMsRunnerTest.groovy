package it.fmach.metadb.workflow.metams

import java.util.List;

import grails.test.mixin.*
import it.fmach.metadb.User
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
	
	static def currentUser = new User(username: 'roman', password: 'namor', workDir: '/home/mylonasr/MetaDB/data/roman')
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
		assay.workDir = tmpFile.getAbsolutePath()
		assay.owner = currentUser
		assay.save(flash: true, failOnError: true)
		
		runner.runMetaMs(assay, selectedMsAssayNames, "1.2", "10.5", "comment")
		
		// make sure metams is done
		assay.refresh()
		Thread.sleep(500)
		
		def metaMsSubmission = MetaMsSubmission.get(1)
		
		// submission fails because the CDF files are missing
		assert "failed"	== metaMsSubmission.status
		assert 2 == metaMsSubmission.selectedRuns.size()
		
		// check assay was saved correctly
		assert assay.workDir + "/pipeline/1" == assay.metaMsSubmissions.get(0).workDir
		
		println (assay.workDir)
		
	}
	
	
	@Test
	public void runMetaMs2submissionsTest() {
		def runner = new MetaMsRunner(metaMsConfDir)
		
		def creator = new TestDomainCreator()
		def assay = creator.createExtractedRuns()
		assay.owner = currentUser
		
//		assay.acquiredRuns.each{
//			println(it.msAssayName)
//		}
		
		def selectedMsAssayNames = ['run_2', 'run_3']
		
		// create temporary workdir
		File tmpFile = File.createTempFile("test_metams_", Long.toString(System.nanoTime()))
		tmpFile.delete()
		tmpFile.mkdir()
		assay.workDir = tmpFile.getAbsolutePath()
		assay.save(flash: true, failOnError: true)
		
		// run the first time
		runner.runMetaMs(assay, selectedMsAssayNames, "1.2", "10.5", "comment")
		
		// make sure metams is done
		Thread.sleep(500)
		
		// run the second time (without retention time restrictions)
		runner.runMetaMs(assay, selectedMsAssayNames, null, null, null)
		
		// make sure metams is done
		Thread.sleep(500)
		
		// check assay has two metaMsSubmission entries
		assay.refresh()
		assert 2 == assay.metaMsSubmissions.size()
		
		// check the names
		assert "1" == assay.metaMsSubmissions.get(0).name
		assert "2" == assay.metaMsSubmissions.get(1).name
		
		// check the paths
		assert assay.workDir + "/pipeline/1" == assay.metaMsSubmissions.get(0).workDir
		assert assay.workDir + "/pipeline/2" == assay.metaMsSubmissions.get(1).workDir
		
	}
	
	@Test
	public void constructCommandTest(){
		def runner = new MetaMsRunner(metaMsConfDir)
		runner.workDir = "work/dir"
		runner.metaMsSettingsDir = "setting/dir"
		runner.metaMsDbDir = "db/dir"
		runner.fileListPath = "filelist/dir"
		
		def creator = new TestDomainCreator()
		def assay = creator.createExtractedRuns()
		assay.workDir = runner.workDir

		def command = runner.constructCommand(assay, null, null)
		assert "Rscript resources/conf/metaMS/runMetaMS.R -i LC -p negative -f filelist/dir -s setting/dir/some/path -o work/dir" == command
		
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
	
	
	@Test
	public void createWorkDirTest() {
		def runner = new MetaMsRunner(metaMsConfDir)
		
		def creator = new TestDomainCreator()
		def assay = creator.createExtractedRuns()
		
		// create temporary workdir
		File tmpFile = File.createTempFile("test_metams_", Long.toString(System.nanoTime()))
		tmpFile.delete()
		tmpFile.mkdir()
		assay.workDir = tmpFile.getAbsolutePath()
		
		def workDir = runner.createWorkDir(assay)
		
		assert assay.workDir + "/pipeline/1" == workDir
	}
	
	
	
	
}

