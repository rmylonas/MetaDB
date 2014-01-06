package it.fmach.metadb.isatab.controller



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(MetaMSController)
class MetaMSControllerTests {

    void testIndex() {
       controller.index()
	   assert "No assay is selected" == flash.error
    }
}
