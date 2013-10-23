package it.fmach.metadb.isatab.model



import grails.test.mixin.*

import org.junit.*

import it.fmach.metadb.isatab.importer.IsatabImporter
import it.fmach.metadb.isatab.importer.IsatabImporterImpl
/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(FEMRun)
class FEMRunTests {

	static String rootDir = "test/data/org/isatools/isacreator/io/importisa/"
	String configDir = rootDir + "MetaboLightsConfig20130507"
	String isatabDir = rootDir + "Wine_Storage"
	
    void testSaveAndLoadRun() {
        IsatabImporter importer = new IsatabImporterImpl(configDir)
		def investigation = importer.importIsatabFiles(isatabDir)
		
		def run = investigation.studyList.get(0).assays.get(0).runs.get(0)
		
		if (!run.validate()){
			run.errors.allErrors.each {
				println it
			}
		}
		
		run.save(flush: true)
		
		def loadedRun = FEMRun.findBySampleName("0001_R")
		assertTrue(loadedRun.protocols.contains("Phenolics"))
    }
}
