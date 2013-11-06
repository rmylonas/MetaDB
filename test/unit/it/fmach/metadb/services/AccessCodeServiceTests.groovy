package it.fmach.metadb.services

import it.fmach.metadb.isatab.model.AccessCode
import grails.test.mixin.*

import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@Mock( [ AccessCode ] )
@TestFor(AccessCodeService)
class AccessCodeServiceTests {

    void testGetNewCode() {
        def acService = new AccessCodeService()
		AccessCode ac = acService.getNewCode()
		assert ac.code.length() >= 1
    }
	
}
