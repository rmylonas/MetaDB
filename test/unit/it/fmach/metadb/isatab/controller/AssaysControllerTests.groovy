package it.fmach.metadb.isatab.controller

import grails.test.mixin.*
import it.fmach.metadb.isatab.testHelper.TestDomainCreator
import org.junit.*
import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMStudy
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.isatab.model.InstrumentMethod
import it.fmach.metadb.isatab.model.Instrument
import it.fmach.metadb.isatab.model.AccessCode

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@Mock([FEMStudy, FEMAssay, FEMRun, InstrumentMethod, Instrument, AccessCode])
@TestFor(AssaysController)
class AssaysControllerTests {

    void testIndex() {
		// create a test study
		def creator = new TestDomainCreator()
		def study = creator.createStudy()
		study.save(flush: true)
		   
		controller.index()
		
		assert 3 == session.assays.size()
    }
}
