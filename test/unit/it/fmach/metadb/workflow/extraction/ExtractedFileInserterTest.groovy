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
class ExtractedFileInserterTest {

	static String rootDir = "resources/it/fmach/metadb/workflow/extraction/"
	
	
	@Test
	public void addExtractedMzXMLZipTest() {
		def creator = new TestDomainCreator()
		def assay = creator.createRandomizedRunsQCFirst()
		
		def extractedZipDir = new ClassPathResource(rootDir + "extracted_mzXML.zip").getFile().getAbsolutePath()
		
		// set the status to "acquired"
		assay.status = "acquired"
		assay.acquiredRuns = assay.randomizedRuns
		
		// set a temporary working dir
		File tmpFile = File.createTempFile("test_extraction_", Long.toString(System.nanoTime()))
		tmpFile.delete()
		// tmpFile.mkdir()
		def inserter = new ExtractedFileInserter(tmpFile.getAbsolutePath())
		
		// add the raw files
		def info = inserter.addExtractedFilesZip(assay, extractedZipDir)
		
		assert assay.acquiredRuns.size()-3 == info[0].size()
		assert "pipo.mzXML" == info[1][0]
		assert assay.acquiredRuns.get(0).derivedSpectraFilePath.contains("AA_001_QC_tag_01.mzXML")
		assert "extracted" == assay.acquiredRuns.get(1).status
		assert 3 == info[2]
		
		// the assay status should still be "acquired", since not all files were added
		assert "acquired" == assay.status
	}
	
	
	@Test
	public void addExtractedFilesZipTest() {		
		def creator = new TestDomainCreator()
		def assay = creator.createRandomizedRunsQCFirst()
		
		def extractedZipDir = new ClassPathResource(rootDir + "extracted.zip").getFile().getAbsolutePath()
		
		// set the status to "acquired"
		assay.status = "acquired"
		assay.acquiredRuns = assay.randomizedRuns
		
		// set a temporary working dir
		File tmpFile = File.createTempFile("test_extraction_", Long.toString(System.nanoTime()))
		tmpFile.delete()
		// tmpFile.mkdir()
		def inserter = new ExtractedFileInserter(tmpFile.getAbsolutePath())
		
		// add the raw files
		def info = inserter.addExtractedFilesZip(assay, extractedZipDir)
		
		assert assay.acquiredRuns.size()-3 == info[0].size()
		assert "pipo.CDF" == info[1][0]
		assert assay.acquiredRuns.get(0).derivedSpectraFilePath.contains("AA_001_QC_tag_01.CDF")
		assert "extracted" == assay.acquiredRuns.get(1).status
		assert 3 == info[2]
		
		// the assay status should still be "acquired", since not all files were added
		assert "acquired" == assay.status
	}
	
	
	@Test
	public void addExtractedFilesTest() {
		def inserter = new ExtractedFileInserter()
		
		def creator = new TestDomainCreator()
		def assay = creator.createRandomizedRunsQCFirst()
		
		// set the status to "acquired"
		assay.status = "acquired"
		assay.acquiredRuns = assay.randomizedRuns
		
		// a list of (imaginary) extracted files
		def extractedFiles = [new File("/test/AA_001_QC_tag_01.CDF"), new File("/test/AA_23_Sample_1_tag_01.CDF"), new File("/test/AA_33_Sample_2_tag_01.CDF"), new File("/test/pipo.CDF")]
		
		// add the raw files	
		def info = inserter.addExtractedFiles(assay, extractedFiles)
		
		assert assay.acquiredRuns.size()-3 == info[0].size()
		assert "pipo.CDF" == info[1][0]
		assert "/test/AA_001_QC_tag_01.CDF" == assay.acquiredRuns.get(0).derivedSpectraFilePath
		assert "extracted" == assay.acquiredRuns.get(1).status
		assert 3 == info[2]
		
		// and control the number of files added
		
		// the assay status should still be "acquired", since not all files were added
		assert "acquired" == assay.status
	}
	
	
	@Test
	public void addMarynkasFilesTest() {
		def inserter = new ExtractedFileInserter()
		
		def creator = new TestDomainCreator()
		def assay = creator.createMarynkasRuns()
		
		// set the status to "acquired"
		assay.status = "acquired"
		assay.acquiredRuns = assay.randomizedRuns
		
		// a list of (imaginary) extracted files
		def extractedFiles = [new File("/test/solv_130827185620.mzXML"), new File("/test/JALI108_DietB_V3.mzXML"), new File("/test/Creat55_Cinn11_130828044500.mzdata"), new File("/test/JALI015_DietC_V2_130829010454.mzXML"), new File("/test/pipo.CDF")]
		
		// add the raw files : info = [missingFiles, namesNotFound, nrFilesAdded]
		def info = inserter.addExtractedFiles(assay, extractedFiles)
		
		assert 0 == info[0].size()
		assert "pipo.CDF" == info[1][0]
		assert "/test/solv_130827185620.mzXML" == assay.acquiredRuns.get(0).derivedSpectraFilePath
		assert "extracted" == assay.acquiredRuns.get(1).status
		assert 4 == info[2]
		assert "extracted" == assay.status
	}

}
