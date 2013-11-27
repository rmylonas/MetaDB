package it.fmach.metadb.workflow.randomization

import grails.test.mixin.*
import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMStudy
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.isatab.model.InstrumentMethod
import it.fmach.metadb.isatab.model.Instrument
import it.fmach.metadb.isatab.model.AccessCode
import it.fmach.metadb.isatab.testHelper.TestDomainCreator
import it.fmach.metadb.workflow.randomization.RunRandomization;

import org.junit.*

@Mock([FEMStudy, FEMAssay, FEMRun, InstrumentMethod, Instrument, AccessCode])
class RunRandomizationTest {

	@Test
	public void testRandomizeAssay() {
		
		// create a test study
		def creator = new TestDomainCreator()
		def study = creator.createStudy()
		def method = creator.createMethod()
		
		def rand = new RunRandomization()
		def randomizedAssay = rand.randomizeAssay(study.assays.get(0))
		
		assert 4 == randomizedAssay.runs.size()
		assert 20 == randomizedAssay.randomizedRuns.size()
			
		// check some entries
		assert randomizedAssay.randomizedRuns.get(0).msAssayName.contains("001_blank_xev_tar_RP")
		assert randomizedAssay.randomizedRuns.get(5).msAssayName.contains("006_STDmix_xev_tar_RP")
		assert randomizedAssay.randomizedRuns.get(6).msAssayName.contains("007_QC_xev_tar_RP")
		assert randomizedAssay.randomizedRuns.get(8).msAssayName.contains("009_Sample")
		assert randomizedAssay.randomizedRuns.get(19).msAssayName.contains("020_blank_xev_tar_RP")
		
	}

}
