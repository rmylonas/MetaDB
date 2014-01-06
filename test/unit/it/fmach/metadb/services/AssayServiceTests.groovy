package it.fmach.metadb.services



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

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@Mock([FEMStudy, FEMAssay, FEMRun, InstrumentMethod, Instrument, AccessCode, MetaMsSubmission, MetaMsDb])
@TestFor(AssayService)
class AssayServiceTests {

    void testCleanAcquisitionRuns() {
		// setup the test-data
		def creator = new TestDomainCreator()
		def assay = creator.createExtractedRuns()
		
		// create service
    	def assayService = new AssayService()
		
		// clean acquisition runs
		assayService.cleanAcquisitionRuns(assay)
		
		assert 0 == assay.acquiredRuns.size()
    }
}
