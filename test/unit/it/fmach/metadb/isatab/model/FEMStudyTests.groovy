
package it.fmach.metadb.isatab.model

import it.fmach.metadb.isatab.importer.IsatabImporter;
import it.fmach.metadb.isatab.importer.IsatabImporterImpl;
import it.fmach.metadb.isatab.model.FEMStudy;
import grails.test.mixin.*

import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(FEMStudy)
class FEMStudyTests {
	
	static String rootDir = "test/data/org/isatools/isacreator/io/importisa/"
	String configDir = rootDir + "MetaboLightsConfig20130507"
	String isatabDir = rootDir + "Wine_Storage"

    void testSaveAndLoadStudy() {

		IsatabImporter importer = new IsatabImporterImpl(configDir)
		def investigation = importer.importIsatabFiles(isatabDir)
		def study = investigation.studyList.get(0)
		
//		if (!study.validate()){
//			study.errors.allErrors.each {
//				println it
//			}
//		}
		
		study.save(flush: true)		
		def loadedStudy = FEMStudy.findByDescriptionLike("%red wine%")
		
		assertEquals("Metabolic changes during wine storage", loadedStudy.title)
    }
	
}
