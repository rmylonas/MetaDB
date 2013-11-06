package it.fmach.metadb.isatab.model



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(AccessCode)
class AccessCodeTests {

    void testInsertAndLoad() {
       def ac = new AccessCode()
	   ac.code = "myCode"
	   ac.save(flush: true)
	   
	   assert AccessCode.count() == 1
	   def loadedAc = AccessCode.findByCode("myCode")
	   assert loadedAc != null
    }
}
