package it.fmach.metadb.isatab.controller



import grails.test.mixin.*
import org.junit.*
import it.fmach.metadb.User
import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMStudy
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.isatab.model.InstrumentMethod
import it.fmach.metadb.isatab.model.Instrument
import it.fmach.metadb.isatab.model.AccessCode
import it.fmach.metadb.isatab.testHelper.TestDomainCreator

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@Mock([FEMStudy, FEMAssay, FEMRun, InstrumentMethod, Instrument, AccessCode])
@TestFor(RunsController)
class RunsControllerTests {

	static def currentUser = new User(username: 'roman', password: 'namor', workDir: '/home/mylonasr/MetaDB/data/roman')
	
    void testIndex() {
		// create a test study
		def creator = new TestDomainCreator()
		def study = creator.createStudy()
		study.owner = currentUser
		study.save(flush: true, failOnError: true)
		   
		params['id'] = 1
		controller.index()
		assert 4 == session.assay.randomizedRuns.size()
    }
}
