package it.fmach.metadb.services



import grails.test.mixin.*
import org.junit.*
import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMStudy
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.isatab.model.FEMSample
import it.fmach.metadb.isatab.model.InstrumentMethod
import it.fmach.metadb.isatab.model.Instrument
import it.fmach.metadb.isatab.model.AccessCode
import it.fmach.metadb.isatab.testHelper.TestDbSetup
import it.fmach.metadb.isatab.testHelper.TestDomainCreator

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@Mock([FEMStudy, FEMAssay, FEMRun, FEMSample, InstrumentMethod, Instrument, AccessCode])
@TestFor(StudyService)
class StudyServiceTests {

    void testSaveStudy() {
		// create instrument and methods
		// def deSetup = new TestDbSetup()
		// deSetup.createInstrument()		
		
		// create a test study
		def creator = new TestDomainCreator()
		def study = creator.createStudy()
		
		service.saveStudy(study)
		
		assert 1 == FEMStudy.list().size()
		   
    }
}
