package it.fmach.metadb.isatab.importer


import it.fmach.metadb.isatab.model.AccessCode
import org.junit.*
import grails.test.mixin.*

@Mock([AccessCode])
class AccessCodeGeneratorTests {
	
	@Test
	void testGetNewCode() {
		def acg = new AccessCodeGenerator()
		
		// create 5 codes
		for(def i=0; i<5; i++){
			AccessCode ac = acg.getNewCode()
			ac.save(flush: true)
		}
		
		AccessCode ac2 = acg.getNewCode()
		// the 6th should be 0006
		assert "0006" == ac2.code
    }
}
