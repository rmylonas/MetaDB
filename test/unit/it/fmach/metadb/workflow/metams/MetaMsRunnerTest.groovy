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

@Mock([FEMStudy, FEMAssay, FEMRun, InstrumentMethod, Instrument, AccessCode])
class MetaMsRunnerTest {
	
	static String metaMsConfDir = "resources/conf/metaMS"
	
	@Test
	public void runMetaMsTest() {
		def runner = new MetaMsRunner(metaMsConfDir)
		
		def creator = new TestDomainCreator()
		def assay = creator.createExtractedRuns()
		
		assay.acquiredRuns.each{
			println(it.msAssayName)
		}
		
		def selectedMsAssayNames = ['run_2', 'run_3']
		
		// create temporary workdir
		File tmpFile = File.createTempFile("test_metams_", Long.toString(System.nanoTime()))
		tmpFile.delete()
		tmpFile.mkdir()
		String workDir = tmpFile.getAbsolutePath()
		
		runner.runMetaMs(workDir, assay, selectedMsAssayNames)
		
		
	}
}

