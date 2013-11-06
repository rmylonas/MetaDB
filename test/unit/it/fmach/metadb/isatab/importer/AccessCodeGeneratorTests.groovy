package it.fmach.metadb.isatab.importer


import it.fmach.metadb.isatab.model.AccessCode
import org.junit.*
import grails.test.mixin.*

@Mock([AccessCode])
class AccessCodeGeneratorTests {
	
	@Test
	void testGetNewCode() {
        def acg = new AccessCodeGenerator()
		AccessCode ac = acg.getNewCode()
		assert ac.code.length() >= 1
    }
}
