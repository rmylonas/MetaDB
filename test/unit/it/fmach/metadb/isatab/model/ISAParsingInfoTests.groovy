
package it.fmach.metadb.isatab.model

import it.fmach.metadb.User
import it.fmach.metadb.isatab.importer.FEMInvestigation
import it.fmach.metadb.isatab.importer.IsatabImporter
import it.fmach.metadb.isatab.importer.IsatabImporterImpl
import it.fmach.metadb.isatab.testHelper.TestDbSetup
import it.fmach.metadb.isatab.validator.IsatabValidator
import it.fmach.metadb.isatab.validator.IsatabValidatorImpl
import org.codehaus.groovy.grails.io.support.ClassPathResource
import org.junit.Test

import grails.test.mixin.*

import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([AccessCode, Instrument])
@TestFor(ISAParsingInfo)
class ISAParsingInfoTests {

	static def currentUser = new User(username: 'roman', password: 'namor', workDir: '/home/mylonasr/MetaDB/data/roman')
	static String rootDir = "resources/org/isatools/isacreator/io/importisa/"
	def configDir = new ClassPathResource(rootDir + "MetaboLightsConfig20130507").getFile().getAbsolutePath()
		
	@Test
    void testIsaParsingWithLoadingError() {
		
		// create instruments
		def creator = new TestDbSetup()
		creator.createInstrument()
		
		def isatabDir = File.createTempFile("empty_dir", "")
		isatabDir.delete();
		isatabDir.mkdir();
		
		def workDir = File.createTempFile("test_workdir", "")
		workDir.delete();
		workDir.mkdir();

		IsatabImporter importer = new IsatabImporterImpl(configDir, workDir.getAbsolutePath(), currentUser)
		def investigation = importer.importIsatabFiles(isatabDir.getAbsolutePath())
		def parsingInfo = investigation.isaParsingInfo
		parsingInfo.save(flush: true)
		
		// load domain from the DB
		def loadedParsingInfo = ISAParsingInfo.findBySuccess(false)
		
		assert ! loadedParsingInfo.success
		assert 1 == loadedParsingInfo.nrOfErrors
		assert loadedParsingInfo.errorMessage.contains("file does not exist")
		assert "parsing failed" == loadedParsingInfo.status
			
    }
	
	@Test
	void testIsaParsingWithVerificationError() {
		// create instruments
		def creator = new TestDbSetup()
		creator.createInstrument()
		
		def isatabDir = new ClassPathResource(rootDir + "Invalid_Wine_Storage").getFile().getAbsolutePath()
		
		def workDir = File.createTempFile("test_workdir", "")
		workDir.delete();
		workDir.mkdir();

		IsatabImporter importer = new IsatabImporterImpl(configDir, workDir.getAbsolutePath(), currentUser)
		def investigation = importer.importIsatabFiles(isatabDir)
		def parsingInfo = investigation.isaParsingInfo
		
		assert "parsed" == parsingInfo.status
		assert parsingInfo.success
		
		IsatabValidator validator = new IsatabValidatorImpl(configDir)
		parsingInfo = validator.validateIsatabFile(isatabDir)
		
		assert "validation failed" == parsingInfo.status
		assert ! parsingInfo.success
		
		parsingInfo.save(flush: true)
		
		// load domain from the DB
		def loadedParsingInfo = ISAParsingInfo.findBySuccess(false)
		
		assert ! loadedParsingInfo.success
		assert 9 == loadedParsingInfo.nrOfErrors
		assert loadedParsingInfo.errorMessage.contains("2405_C is a Sample Name")
		assert "validation failed" == loadedParsingInfo.status
			
	}
	
	
}
