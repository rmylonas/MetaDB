package it.fmach.metadb.export

import it.fmach.metadb.export.StudyZipExporter;
import it.fmach.metadb.isatab.testHelper.TestDomainCreator

import grails.test.mixin.*

import org.junit.*


class StudyZipExporterTest {

	@Test
	public void testCreateTempZip() {
		
		// prepare test data
		File tempFile = File.createTempFile("test_zipper_", "")
		tempFile.delete()
		
		// create a folder in temp folder
		File newDir = new File(tempFile.getAbsolutePath() + "/toto")
		newDir.delete()
		newDir.mkdirs()
		
		// and a file in this new folder
		File newFile = new File(newDir.getPath() + "/hoho.txt")
		newFile.createNewFile()
		
		// zip the test folder
		def zipper = new StudyZipExporter()
		def zipFilePath = zipper.createTempZip(tempFile.getAbsolutePath())
		
		// verify that the ZIP was created
		def zipFile = new File(zipFilePath)
		assert zipFile.exists()
		assert 0 < zipFile.size()
		
	}
	
}
