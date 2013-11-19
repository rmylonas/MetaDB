package it.fmach.metadb.isatab.model



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(FEMGroup)
class FEMGroupTests {

    void testSaveAndLoad() {
		FEMGroup group = new FEMGroup(name: 'Fulvio', description: 'ok')
		group.save(flush: true)
		
		def loadedGroup = FEMGroup.findByName('Fulvio')
		assert "ok" == loadedGroup.description	   
    }
}
