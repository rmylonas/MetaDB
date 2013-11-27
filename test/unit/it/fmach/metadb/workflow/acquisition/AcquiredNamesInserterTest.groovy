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
		def assayNames = ["QC_1", "QC_2", "AA_Sample_2_2", "AA_Sample_3_3", "AA_Sample_4_4", "AA_Sample_5_5",
							"QC_6", "AA_Sample_6_7", "AA_Sample_7_8", "AA_Sample_8_9", "AA_Sample_9_10", 
							"AA_Sample_10_11", "Pipo"]
		
		def missingNames = insert.addAcquiredAssayNames(assay, assayNames)
		
		assert 2 == missingNames[0].size()
		assert 1 == missingNames[1].size()
		assert 12 == assay.acquiredRuns.size()
		
		def qcRun = assay.acquiredRuns[0]
		def qcRun_2 = assay.acquiredRuns[1]
		def run_3 = assay.acquiredRuns[2]
		def run_8 = assay.acquiredRuns[7]
		
		assert "QC_1" == qcRun.msAssayName
		assert "QC_2" == qcRun_2.msAssayName
		assert "AA_Sample_2_2" == run_3.msAssayName
		assert "AA_Sample_6_7" == run_8.msAssayName
		
		// QC sample should be the same
		assert qcRun.sample == qcRun_2.sample
		
		// other samples different
		assert 	run_3.sample != qcRun.sample
		assert run_8.sample != run_3.sample
		assert "Sample_2" == run_3.sample.name
		
		// samples from randomizedRuns and acquiredRuns should be the same
		assert assay.randomizedRuns.get(0).sample == qcRun.sample
		assert assay.randomizedRuns.get(1).sample == run_3.sample
		
	}

}
