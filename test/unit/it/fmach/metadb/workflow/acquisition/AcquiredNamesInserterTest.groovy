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
	public void testAddAcquiredAssayNamesMultiSameRuns() {
		
		def creator = new TestDomainCreator()
		def assay = creator.createRandomizedRunsQCFirst()
		
		// check a QC run
		def assayNames = ["AA_001_QC_tag", "AA_002_QC_tag", "AA_003_Sample_1_tag", "AA_004_Sample_2_tag",
			"AA_005_Sample_3_tag", "AA_006_Sample_4_tag", "AA_007_QC_tag", "AA_008_Sample_5_tag",
			"AA_009_Sample_6_tag", "AA_010_Sample_7_tag", "AA_011_Sample_8_tag", "AA_012_Sample_9_tag", "Pipo", "AA_013_Sample_9_tag"]
		
		def missingNames = insert.addAcquiredAssayNames(assay, assayNames)
		
		def qcRun = assay.acquiredRuns[0]
		def qcRun_2 = assay.acquiredRuns[1]
		def run_3 = assay.acquiredRuns[2]
		def run_8 = assay.acquiredRuns[7]
		def run_11 = assay.acquiredRuns[11]
		
		assert "AA_001_QC_tag" == qcRun.msAssayName
		assert "AA_002_QC_tag" == qcRun_2.msAssayName
		assert "AA_003_Sample_1_tag" == run_3.msAssayName
		assert "AA_008_Sample_5_tag" == run_8.msAssayName
		
		assert "AA_012_Sample_9_tag" == run_11.msAssayName
		assert 1 == run_11.additionalRuns.size
		assert "AA_013_Sample_9_tag" == run_11.additionalRuns.get(0).msAssayName
		
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
