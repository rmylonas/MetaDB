package it.fmach.metadb.isatab.model

import grails.test.mixin.*
import it.fmach.metadb.isatab.importer.IsatabImporter
import it.fmach.metadb.isatab.importer.IsatabImporterImpl
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(FEMSample)
class FEMSampleTests {

	static String rootDir = "test/data/org/isatools/isacreator/io/importisa/"
	String configDir = rootDir + "MetaboLightsConfig20130507"
	String isatabDir = rootDir + "Wine_Storage"
	
    void testSaveAndLoadSample() {
		
		IsatabImporter importer = new IsatabImporterImpl(configDir)
		def investigation = importer.importIsatabFiles(isatabDir)
		
		def sample = investigation.studyList.get(0).samples.get(0)
		sample.save(flush: true)	
			
		def loadedSample = FEMSample.findByOrganismLike("%Vitis%")
		assertEquals("QC_H", loadedSample.name)
    }
	
	
}
