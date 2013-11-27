package it.fmach.metadb.isatab.validator

import it.fmach.metadb.isatab.importer.FEMInvestigation;
import it.fmach.metadb.isatab.importer.IsatabImporter
import it.fmach.metadb.isatab.importer.IsatabImporterImpl
import it.fmach.metadb.isatab.model.ISAParsingInfo;

import org.junit.*

class IsatabValidatorTest {

	static String rootDir = "test/data/org/isatools/isacreator/io/importisa/"
	String configDir = rootDir + "MetaboLightsConfig20130507";
	
	@Test
	void testValidIsatabFile() {
		
		String isatabParentDir = rootDir + "Wine_Storage";
		
		// validate
		IsatabValidator validator = new IsatabValidatorImpl(configDir)
		ISAParsingInfo parsingInfo = validator.validateIsatabFile(isatabParentDir)
		
		assert parsingInfo.success
		assert 0 == parsingInfo.nrOfErrors
		assert "validated" == parsingInfo.status
		
	}
	
	@Test
	void testInvalidIsatabFile() {
		
		String isatabParentDir = rootDir + "Invalid_Wine_Storage";
		
		// validate
		IsatabValidator validator = new IsatabValidatorImpl(configDir)
		ISAParsingInfo parsingInfo = validator.validateIsatabFile(isatabParentDir)
		
		assert ! parsingInfo.success
		assert 9 == parsingInfo.nrOfErrors
		assert "validation failed" == parsingInfo.status
		println(parsingInfo.errorMessage)
		
	}
	
}
