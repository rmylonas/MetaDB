package it.fmach.metadb.workflow.extraction

import it.fmach.metadb.isatab.testHelper.TestDomainCreator
import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMStudy
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.isatab.model.InstrumentMethod
import it.fmach.metadb.isatab.model.Instrument
import it.fmach.metadb.isatab.model.AccessCode
import grails.test.mixin.*

import org.codehaus.groovy.grails.io.support.ClassPathResource
import org.junit.*

@Mock([FEMStudy, FEMAssay, FEMRun, InstrumentMethod, Instrument, AccessCode])
class RawFileInserterTest {

	static String rootDir = "resources/it/fmach/metadb/workflow/extraction/"
	
	@Test
	public void addExtractedFilesZipTest() {
		
		def creator = new TestDomainCreator()
		def assay = creator.createRandomizedRunsQCFirst()
		
		def extractedZipDir = new ClassPathResource(rootDir + "raw_data.zip").getFile().getAbsolutePath()
		
		// set the status to "acquired"
		assay.status = "acquired"
		assay.acquiredRuns = assay.randomizedRuns
		
		// set a temporary working dir
		File tmpFile = File.createTempFile("test_extraction_", Long.toString(System.nanoTime()))
		tmpFile.delete()
		tmpFile.mkdir()
		
		def inserter = new RawFileInserter(tmpFile.getAbsolutePath())
		
		// add the raw files
		def info = inserter.addRawFilesZip(assay, extractedZipDir)
		
		assert info[0].size() == 9
		assert info[1][0] == "pipo.raw"
		assert info[2] == 3
		
	}

}
