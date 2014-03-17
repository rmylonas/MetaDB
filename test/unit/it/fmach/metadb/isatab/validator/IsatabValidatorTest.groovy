package it.fmach.metadb.isatab.validator

import it.fmach.metadb.isatab.importer.FEMInvestigation;
import it.fmach.metadb.isatab.importer.IsatabImporter
import it.fmach.metadb.isatab.importer.IsatabImporterImpl
import it.fmach.metadb.isatab.model.ISAParsingInfo;

import org.codehaus.groovy.grails.io.support.ClassPathResource
import org.junit.*

class IsatabValidatorTest {

	static String rootDir = "resources/org/isatools/isacreator/io/importisa/"
	def configDir = new ClassPathResource(rootDir + "MetaboLightsConfig20130507").getFile().getAbsolutePath()
	
	@Test
	void testValidIsatabFile() {
		
		def isatabParentDir = new ClassPathResource(rootDir + "Wine_Storage").getFile().getAbsolutePath()
		
		// validate
		IsatabValidator validator = new IsatabValidatorImpl(configDir)
		ISAParsingInfo parsingInfo = validator.validateIsatabFile(isatabParentDir)
		
		assert parsingInfo.success
		assert 0 == parsingInfo.nrOfErrors
		assert "validated" == parsingInfo.status
		
	}
	
	@Test
	void testInvalidIsatabFile() {
		
		def isatabParentDir = new ClassPathResource(rootDir + "Invalid_Wine_Storage").getFile().getAbsolutePath()
		
		// validate
		IsatabValidator validator = new IsatabValidatorImpl(configDir)
		ISAParsingInfo parsingInfo = validator.validateIsatabFile(isatabParentDir)
		
		assert ! parsingInfo.success
		assert 9 == parsingInfo.nrOfErrors
		assert "validation failed" == parsingInfo.status
		println(parsingInfo.errorMessage)
		
	}
	
}
