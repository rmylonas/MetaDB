package it.fmach.metadb.isatab.controller

import static org.junit.Assert.*

import org.junit.*
import org.springframework.mock.web.MockMultipartFile

class UploadIsatabControllerIntegrationTests extends GroovyTestCase{

	static String rootDir = "test/data/org/isatools/isacreator/io/importisa/"

	void testUpload() {
		
		fail "Implement me"	
	
/*		println("before")
		def uc = new UploadIsatabController()
		println("after")
		// test the upload
		def fis= new FileInputStream(rootDir + "small.zip")
		final file = new MockMultipartFile("isaTabFile", fis)
		println("here")
		uc.request.addFile(file)
		println("here 2")
		uc.upload()
		println("here 3")

		assert uc.response.redirectedUrl == '/uploadIsatab/parsing'*/
//		assert uc.flash.message == "ISAtab file was succesfully processed"
//		
//		// test the parsing
//		uc.params['a_small_metabolite profiling_mass spectrometry.txt'] = "on"
//		uc.insert()
//		assert FEMStudy.count() == 1
		//assert FEMAssay.count() == 1
	}
}
