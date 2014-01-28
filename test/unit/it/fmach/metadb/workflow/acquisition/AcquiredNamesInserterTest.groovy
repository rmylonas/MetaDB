package it.fmach.metadb.workflow.acquisition

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
class AcquiredNamesInserterTest {
	
	def insert = new AcquiredNamesInserter()

	@Test
	public void testAddAcquiredAssayNames() {
		
		def creator = new TestDomainCreator()
		def assay = creator.createRandomizedRunsQCFirst()
		
		// check a QC run
		def assayNames = ["AA_001_QC_tag", "AA_002_QC_tag", "AA_003_Sample_1_tag", "AA_004_Sample_2_tag", 
			"AA_005_Sample_3_tag", "AA_006_Sample_4_tag", "AA_007_QC_tag", "AA_008_Sample_5_tag", 
			"AA_009_Sample_6_tag", "AA_010_Sample_7_tag", "AA_011_Sample_8_tag", "AA_012_Sample_9_tag", "Pipo"]
		
		def missingNames = insert.addAcquiredAssayNames(assay, assayNames)
		
		def qcRun = assay.acquiredRuns[0]
		def qcRun_2 = assay.acquiredRuns[1]
		def run_3 = assay.acquiredRuns[2]
		def run_8 = assay.acquiredRuns[7]
		
		assert "AA_001_QC_tag" == qcRun.msAssayName
		assert "AA_002_QC_tag" == qcRun_2.msAssayName
		assert "AA_003_Sample_1_tag" == run_3.msAssayName
		assert "AA_008_Sample_5_tag" == run_8.msAssayName
		
		assert 2 == missingNames[0].size()
		assert 1 == missingNames[1].size()
		assert 12 == assay.acquiredRuns.size()
		
		// QC sample should be the same
		assert qcRun.sample == qcRun_2.sample
		
		// other samples different
		assert 	run_3.sample != qcRun.sample
		assert run_8.sample != run_3.sample
		assert "Sample_1" == run_3.sample.name
		
		// samples from randomizedRuns and acquiredRuns should be the same
		assert assay.randomizedRuns.get(0).sample == qcRun.sample
		assert assay.randomizedRuns.get(1).sample == run_3.sample
		
	}
	
	@Test
	public void testAddAcquiredAssayNamesNumber() {
		
		def creator = new TestDomainCreator()
		def assay = creator.createRandomizedRunsQCFirst()
		
		// check a QC run
		def assayNames = ["AA01_001_QC_tag", "AA01_002_QC_tag", "AA01_003_Sample_1_tag", "AA01_004_Sample_2_tag",
			"AA01_005_Sample_3_tag", "AA01_006_Sample_4_tag", "AA01_007_QC_tag", "AA01_008_Sample_5_tag",
			"AA01_009_Sample_6_tag", "AA01_010_Sample_7_tag", "AA01_011_Sample_8_tag", "AA01_012_Sample_9_tag", "Pipo"]
		
		def missingNames = insert.addAcquiredAssayNames(assay, assayNames)
		
		def qcRun = assay.acquiredRuns[0]
		def qcRun_2 = assay.acquiredRuns[1]
		def run_3 = assay.acquiredRuns[2]
		def run_8 = assay.acquiredRuns[7]
		
		assert "AA01_001_QC_tag" == qcRun.msAssayName
		assert "AA01_002_QC_tag" == qcRun_2.msAssayName
		assert "AA01_003_Sample_1_tag" == run_3.msAssayName
		assert "AA01_008_Sample_5_tag" == run_8.msAssayName
		
		assert 2 == missingNames[0].size()
		assert 1 == missingNames[1].size()
		assert 12 == assay.acquiredRuns.size()
		
		// QC sample should be the same
		assert qcRun.sample == qcRun_2.sample
		
		// other samples different
		assert 	run_3.sample != qcRun.sample
		assert run_8.sample != run_3.sample
		assert "Sample_1" == run_3.sample.name
		
		// samples from randomizedRuns and acquiredRuns should be the same
		assert assay.randomizedRuns.get(0).sample == qcRun.sample
		assert assay.randomizedRuns.get(1).sample == run_3.sample
		
	}
	
	
	@Test
	public void testAddAcquiredAssayNamesMultiSameSamplesNumbers() {
		
		def creator = new TestDomainCreator()
		def assay = creator.createRandomizedRunsQCFirst()
		
		// check a QC run
		def assayNames = ["AA001_001_QC_tag", "AA001_002_QC_tag", "AA001_003_Sample_1_tag", "AA001_004_Sample_2_tag",
			"AA001_005_Sample_3_tag", "AA001_006_Sample_4_tag", "AA001_007_QC_tag", "AA001_008_Sample_5_tag",
			"AA001_009_Sample_6_tag", "AA001_010_Sample_7_tag", "AA001_011_Sample_8_tag", "AA001_012_Sample_9_tag", "Pipo", "AA001_013_Sample_9_tag"]
		
		def missingNames = insert.addAcquiredAssayNames(assay, assayNames)
		// check names
		assert "AA001_001_QC_tag" == assay.acquiredRuns[0].msAssayName
		assert "AA001_002_QC_tag" == assay.acquiredRuns[1].msAssayName
		assert "AA001_003_Sample_1_tag" == assay.acquiredRuns[2].msAssayName
		assert "AA001_008_Sample_5_tag" == assay.acquiredRuns[7].msAssayName
		assert "AA001_011_Sample_8_tag" == assay.acquiredRuns[10].msAssayName
		assert "AA001_012_Sample_9_tag" == assay.acquiredRuns[11].msAssayName
		assert "AA001_013_Sample_9_tag" == assay.acquiredRuns[12].msAssayName
		
		// check additionalRun flag
		assert false == assay.acquiredRuns[11].additionalRun
		assert true == assay.acquiredRuns[12].additionalRun
		
		assert 2 == missingNames[0].size()
		assert 1 == missingNames[1].size()
		assert 13 == assay.acquiredRuns.size()
		
		// QC sample should be the same
		assert assay.acquiredRuns[0].sample == assay.acquiredRuns[1].sample
		
		// other samples different
		assert 	assay.acquiredRuns[2].sample != assay.acquiredRuns[0].sample
		assert assay.acquiredRuns[7].sample != assay.acquiredRuns[2].sample
		assert "Sample_1" == assay.acquiredRuns[2].sample.name
		
		// samples from randomizedRuns and acquiredRuns should be the same
		assert assay.randomizedRuns.get(0).sample == assay.acquiredRuns[0].sample
		assert assay.randomizedRuns.get(1).sample == assay.acquiredRuns[2].sample
		
	}
	
	
	
	@Test
	public void testAddAcquiredAssayNamesMultiSameSamples() {
		
		def creator = new TestDomainCreator()
		def assay = creator.createRandomizedRunsQCFirst()
		
		// check a QC run
		def assayNames = ["AA_001_QC_tag", "AA_002_QC_tag", "AA_003_Sample_1_tag", "AA_004_Sample_2_tag",
			"AA_005_Sample_3_tag", "AA_006_Sample_4_tag", "AA_007_QC_tag", "AA_008_Sample_5_tag",
			"AA_009_Sample_6_tag", "AA_010_Sample_7_tag", "AA_011_Sample_8_tag", "AA_012_Sample_9_tag", "Pipo", "AA_013_Sample_9_tag"]
		
		def missingNames = insert.addAcquiredAssayNames(assay, assayNames)
		
		// check names
		assert "AA_001_QC_tag" == assay.acquiredRuns[0].msAssayName
		assert "AA_002_QC_tag" == assay.acquiredRuns[1].msAssayName
		assert "AA_003_Sample_1_tag" == assay.acquiredRuns[2].msAssayName
		assert "AA_008_Sample_5_tag" == assay.acquiredRuns[7].msAssayName
		assert "AA_011_Sample_8_tag" == assay.acquiredRuns[10].msAssayName
		assert "AA_012_Sample_9_tag" == assay.acquiredRuns[11].msAssayName
		assert "AA_013_Sample_9_tag" == assay.acquiredRuns[12].msAssayName
		
		// check additionalRun flag
		assert false == assay.acquiredRuns[11].additionalRun
		assert true == assay.acquiredRuns[12].additionalRun
		
		assert 2 == missingNames[0].size()
		assert 1 == missingNames[1].size()
		assert 13 == assay.acquiredRuns.size()
		
		// QC sample should be the same
		assert assay.acquiredRuns[0].sample == assay.acquiredRuns[1].sample
		
		// other samples different
		assert 	assay.acquiredRuns[2].sample != assay.acquiredRuns[0].sample
		assert assay.acquiredRuns[7].sample != assay.acquiredRuns[2].sample
		assert "Sample_1" == assay.acquiredRuns[2].sample.name
		
		// samples from randomizedRuns and acquiredRuns should be the same
		assert assay.randomizedRuns.get(0).sample == assay.acquiredRuns[0].sample
		assert assay.randomizedRuns.get(1).sample == assay.acquiredRuns[2].sample
		
	}
	
	
	@Test
	public void testAddAcquiredAssayNamesMultiSameRuns() {
		
		def creator = new TestDomainCreator()
		def assay = creator.createRandomizedRunsQCFirst()
		
		// check a QC run
		def assayNames = ["AA_001_QC_tag", "AA_002_QC_tag", "AA_003_Sample_1_tag", "AA_004_Sample_2_tag",
			"AA_005_Sample_3_tag", "AA_006_Sample_4_tag", "AA_007_QC_tag", "AA_008_Sample_5_tag", "AA_008_Sample_5_tag",
			"AA_009_Sample_6_tag", "AA_010_Sample_7_tag", "AA_011_Sample_8_tag", "AA_012_Sample_9_tag", "Pipo", "AA_013_Sample_9_tag"]
		
		def missingNames
		def message
		
		try{
			missingNames = insert.addAcquiredAssayNames(assay, assayNames)
		}catch(Exception e){
			message = e.message
		}

		assert "[AA_008_Sample_5_tag] is used multiple times. Names have to be unique." == message
	}
	
	@Test
	public void testAddAcquiredAssayNamesWitoutUnderscore() {
		
		def creator = new TestDomainCreator()
		def assay = creator.createRandomizedRunsQCFirst()
		
		// check a QC run
		def assayNames = ["AA001_QC_tag", "AA002_QC_tag", "AA003_Sample_1_tag", "AA004_Sample_2_tag",
			"AA005_Sample_3_tag", "AA006_Sample_4_tag", "AA007_QC_tag", "AA008_Sample_5_tag",
			"AA009_Sample_6_tag", "AA010_Sample_7_tag", "AA011_Sample_8_tag", "AA012_Sample_9_tag", "Pipo"]
		
		def missingNames = insert.addAcquiredAssayNames(assay, assayNames)
		
		def qcRun = assay.acquiredRuns[0]
		def qcRun_2 = assay.acquiredRuns[1]
		def run_3 = assay.acquiredRuns[2]
		def run_8 = assay.acquiredRuns[7]
		
		assert "AA001_QC_tag" == qcRun.msAssayName
		assert "AA002_QC_tag" == qcRun_2.msAssayName
		assert "AA003_Sample_1_tag" == run_3.msAssayName
		assert "AA008_Sample_5_tag" == run_8.msAssayName
		
		assert 2 == missingNames[0].size()
		assert 1 == missingNames[1].size()
		assert 12 == assay.acquiredRuns.size()
		
		// QC sample should be the same
		assert qcRun.sample == qcRun_2.sample
		
		// other samples different
		assert 	run_3.sample != qcRun.sample
		assert run_8.sample != run_3.sample
		assert "Sample_1" == run_3.sample.name
		
		// samples from randomizedRuns and acquiredRuns should be the same
		assert assay.randomizedRuns.get(0).sample == qcRun.sample
		assert assay.randomizedRuns.get(1).sample == run_3.sample
		
	}
	

}
