package it.fmach.metadb.isatab.model



import grails.test.mixin.*

import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(MetaMsDb)
class MetaMsDbTests {

    void testSaveAndLoad() {
		new MetaMsDb(name: "database", rDataPath: "/some/path.RData").save(flush: true)
		
		def metaMsDb = MetaMsDb.get(1)
		assert metaMsDb.name == "database"
    }
}
