package it.fmach.metadb.isatab.controller

import it.fmach.metadb.isatab.model.FEMStudy
import it.fmach.metadb.isatab.model.FEMAssay
import grails.test.mixin.*

import org.junit.*
import org.springframework.mock.web.MockMultipartFile

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@Mock( [ FEMStudy, FEMAssay ] )
@TestFor(UploadIsatabController)
class UploadIsatabControllerTests {

	static String rootDir = "test/data/org/isatools/isacreator/io/importisa/"

	void testUpload() {
		
		// test the upload
		def fis= new FileInputStream(rootDir + "small.zip")
		final file = new MockMultipartFile("isaTabFile", fis)
		request.addFile(file)
		controller.upload()

		assert response.redirectedUrl == '/uploadIsatab/parsing'
		assert flash.message == "ISAtab file was succesfully processed"
		
		// test the parsing
		params['a_small_metabolite profiling_mass spectrometry.txt'] = "on"
		controller.insert()
		assert FEMStudy.count() == 1
		assert FEMAssay.count() == 1
	}
	
	
}
