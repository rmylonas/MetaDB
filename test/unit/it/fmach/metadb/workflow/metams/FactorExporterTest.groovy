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
class FactorExporterTest {
	
	static String metaMsConfDir = "resources/conf/metaMS"
	
	@Test
	public void runMetaMsTest() {
		def exporter = new FactorExporter()
		
		def creator = new TestDomainCreator()
		def assay = creator.createRandomizedRunsQCFirst()
		
		def selectedRuns = assay.randomizedRuns
		
		// create temporary workdir
		File tmpFile = File.createTempFile("test_metams_", Long.toString(System.nanoTime()))
		tmpFile.delete()
		tmpFile.mkdir()
		def workDir = tmpFile.getAbsolutePath()
		
		// export the csv-file
		def csvFile = workDir + "/a.csv"
		exporter.exportFactorTable(selectedRuns, csvFile)
		
		// check number of lines (+1 because there is the header)
		def lines = 0
		new File(csvFile).withReader{
			it.each{
				lines ++
			}
		}
		
		assert (selectedRuns.size() + 1) == lines
	}
	
	
	@Test
	public void getFactorNamesTest() {
		def exporter = new FactorExporter()
	
		def creator = new TestDomainCreator()
		def assay = creator.createRandomizedRunsQCFirst()
		
		def selectedRuns = assay.randomizedRuns
		
		def factorNames = exporter.getFactorNames(selectedRuns)
		println(factorNames)
	}
	
	
}

