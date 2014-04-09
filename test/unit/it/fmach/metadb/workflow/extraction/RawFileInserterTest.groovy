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
	public void addRawFilesZipTest() {
		
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
	
	@Test
	public void addMarynkasFilesTest() {
		def inserter = new RawFileInserter()
		
		def creator = new TestDomainCreator()
		def assay = creator.createMarynkasRuns()
		
		// set the status to "acquired"
		assay.status = "acquired"
		assay.acquiredRuns = assay.randomizedRuns
		
		// a list of (imaginary) extracted files
		def extractedFiles = [new File("/test/solv_130827185620.raw"), new File("/test/JALI108_DietB_V3.raw"), new File("/test/Creat55_Cinn11_130828044500.raw"), new File("/test/JALI015_DietC_V2_130829010454.data"), new File("/test/pipo.CDF")]
		
		// add the raw files : info = [missingFiles, namesNotFound, nrFilesAdded]
		def info = inserter.addRawFiles(assay, extractedFiles)
		
		assert 0 == info[0].size()
		assert "pipo.CDF" == info[1][0]
		assert "/test/solv_130827185620.raw" == assay.acquiredRuns.get(0).rawSpectraFilePath
		assert 4 == info[2]
	}

}
