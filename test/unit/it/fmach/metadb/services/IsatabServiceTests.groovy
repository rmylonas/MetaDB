package it.fmach.metadb.services



import grails.test.mixin.*
import it.fmach.metadb.isatab.testHelper.TestDomainCreator
import org.junit.*
import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMStudy
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.isatab.model.FEMSample
import it.fmach.metadb.isatab.model.InstrumentMethod
import it.fmach.metadb.isatab.model.Instrument
import it.fmach.metadb.isatab.model.AccessCode

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(IsatabService)
@Mock([FEMStudy, FEMAssay, FEMRun, FEMSample, InstrumentMethod, Instrument, AccessCode])
class IsatabServiceTests {

    void testAddKeywords() {
        
		// create a test study
		def testCreator = new TestDomainCreator()
		def study = testCreator.createStudy()
		
		def isatabService = new IsatabService()
		
		isatabService.addKeywords(study)
		
		assert 27 == study.keywords.split(" ").size()
		assert study.keywords.contains("Sample_8")
		
		
		study.assays.each{
			assert it.keywords.contains("Gris")
		}
    }
}
