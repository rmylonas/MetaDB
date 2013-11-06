
package it.fmach.metadb.isatab.model

import it.fmach.metadb.isatab.importer.FEMInvestigation
import it.fmach.metadb.isatab.importer.IsatabImporter
import it.fmach.metadb.isatab.importer.IsatabImporterImpl
import it.fmach.metadb.isatab.validator.IsatabValidator
import it.fmach.metadb.isatab.validator.IsatabValidatorImpl
import org.junit.Test

import grails.test.mixin.*

import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([AccessCode])
@TestFor(ISAParsingInfo)
class ISAParsingInfoTests {

	static String rootDir = "test/data/org/isatools/isacreator/io/importisa/"
	String configDir = rootDir + "MetaboLightsConfig20130507"
	
	@Test
    void testIsaParsingWithLoadingError() {
				
		String isatabDir = rootDir + "Empty"
		
        def importer = new IsatabImporterImpl(configDir)
		def investigation = importer.importIsatabFiles(isatabDir)
		def parsingInfo = investigation.isaParsingInfo
		parsingInfo.save(flush: true)
		
		// load domain from the DB
		def loadedParsingInfo = ISAParsingInfo.findBySuccess(false)
		
		assertFalse(loadedParsingInfo.success)
		assertEquals(1, loadedParsingInfo.nrOfErrors)
		assertTrue(loadedParsingInfo.errorMessage.contains("file does not exist"))
		assertEquals("parsing failed", loadedParsingInfo.status)
			
    }
	
	@Test
	void testIsaParsingWithVerificationError() {
				
		String isatabDir = rootDir + "Invalid_Wine_Storage"
		
		def importer = new IsatabImporterImpl(configDir)
		def investigation = importer.importIsatabFiles(isatabDir)
		def parsingInfo = investigation.isaParsingInfo
		
		assertEquals("parsed", parsingInfo.status)
		assertTrue(parsingInfo.success)
		
		IsatabValidator validator = new IsatabValidatorImpl(configDir)
		parsingInfo = validator.validateIsatabFile(isatabDir)
		
		assertEquals("validation failed", parsingInfo.status)
		assertFalse(parsingInfo.success)
		
		parsingInfo.save(flush: true)
		
		// load domain from the DB
		def loadedParsingInfo = ISAParsingInfo.findBySuccess(false)
		
		assertFalse(loadedParsingInfo.success)
		assertEquals(9, loadedParsingInfo.nrOfErrors)
		assertTrue(loadedParsingInfo.errorMessage.contains("2405_C is a Sample Name"))
		assertEquals("validation failed", loadedParsingInfo.status)
			
	}
	
	
}
