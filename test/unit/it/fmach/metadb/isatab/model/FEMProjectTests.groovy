package it.fmach.metadb.isatab.model



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(FEMProject)
class FEMProjectTests {

    void testSomething() {
       def proj = new FEMProject(name: 'wine', description: 'nice')
	   proj.save()
	   
	   def loadedProj = FEMProject.findByName('wine')
	   assert 'nice' == loadedProj.description
    }
}
