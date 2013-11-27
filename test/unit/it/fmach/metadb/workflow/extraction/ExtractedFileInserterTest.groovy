package it.fmach.metadb.workflow.extraction

import it.fmach.metadb.isatab.testHelper.TestDomainCreator
import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMStudy
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.isatab.model.InstrumentMethod
import it.fmach.metadb.isatab.model.Instrument
import it.fmach.metadb.isatab.model.AccessCode
import grails.test.mixin.*

import org.junit.*

@Mock([FEMStudy, FEMAssay, FEMRun, InstrumentMethod, Instrument, AccessCode])
class ExtractedFileInserterTest {

	static String rootDir = "test/data/it/fmach/metadb/workflow/extraction/"
	
	@Test
	public void addExtractedFilesZipTest() {
		def inserter = new ExtractedFileInserter()
		
		def creator = new TestDomainCreator()
		def assay = creator.createRandomizedRunsQCFirst()
		
		// set the status to "acquired"
		assay.status = "acquired"
		assay.acquiredRuns = assay.randomizedRuns
		
		// set a temporary working dir
		File tmpFile = File.createTempFile("test_extraction_", Long.toString(System.nanoTime()))
		tmpFile.delete()
		tmpFile.mkdir()
		assay.workDir = tmpFile.getAbsolutePath()
		
		// add the raw files
		def missing = inserter.addExtractedFilesZip(assay, rootDir + "extracted.zip")
		
		assert assay.acquiredRuns.size()-3 == missing[0].size()
		assert "pipo.CDF" == missing[1][0]
		assert assay.acquiredRuns.get(0).derivedSpectraFilePath.contains("AA_001_QC_tag_01.CDF")
		assert "extracted" == assay.acquiredRuns.get(1).status
		
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
		def extractedFiles = ["AA_001_QC_tag_01.CDF", "AA_23_Sample_1_tag_01.CDF", "AA_33_Sample_2_tag_01.CDF", "pipo.CDF"]
		
		// add the raw files	
		def missing = inserter.addExtractedFiles(assay, extractedFiles)
		
		assert assay.acquiredRuns.size()-3 == missing[0].size()
		assert "pipo.CDF" == missing[1][0]
		assert "AA_001_QC_tag_01.CDF" == assay.acquiredRuns.get(0).derivedSpectraFilePath
		assert "extracted" == assay.acquiredRuns.get(1).status
		
		// the assay status should still be "acquired", since not all files were added
		assert "acquired" == assay.status
	}

}
