
package it.fmach.metadb.isatab.controller

import it.fmach.metadb.isatab.instrument.Polarity;
import it.fmach.metadb.isatab.model.FEMRun;
import it.fmach.metadb.isatab.model.FEMSample;
import it.fmach.metadb.isatab.model.FEMStudy
import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.AccessCode
import it.fmach.metadb.isatab.model.Instrument;
import it.fmach.metadb.isatab.model.InstrumentMethod
import it.fmach.metadb.isatab.testHelper.InstrumentCreator
import grails.test.mixin.*

import org.junit.*
import org.springframework.mock.web.MockMultipartFile

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@Mock( [ FEMStudy, FEMAssay, FEMRun, AccessCode, FEMSample, Instrument] )
@TestFor(UploadIsatabController)
class UploadIsatabControllerTests {

	static String rootDir = "test/data/org/isatools/isacreator/io/importisa/"

	void testUpload() {
		
		// create instruments
		def creator = new InstrumentCreator()
		creator.createInstrument()
		
		// test the upload
		def fis= new FileInputStream(rootDir + "small.zip")
		final file = new MockMultipartFile("isaTabFile", fis)
		request.addFile(file)
		controller.upload()

		assert response.redirectedUrl == '/uploadIsatab/parsing'
		assert flash.message == "ISAtab file was succesfully processed"
		
//		println("investigation: " + session.investigation)
//		session.investigation.studyList.each{ study ->
//			study.assays.each{ assay ->
//				assay.runs.each{
//					println it.sampleName
//					println it.sample
//				}
//				
//			}
//		}
		
		// reset the response to continue testing
		response.reset()
		
		// test the parsing
		params['a_small_metabolite profiling_mass spectrometry.txt'] = "on"
		controller.insert()
		assert response.redirectedUrl == '/uploadIsatab/index'
		assert FEMStudy.count() == 1
		assert FEMAssay.count() == 1
	}
	
	
	
	
	
}
