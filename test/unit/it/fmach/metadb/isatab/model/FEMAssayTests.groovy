package it.fmach.metadb.isatab.model


import grails.test.mixin.*
import it.fmach.metadb.isatab.importer.IsatabImporter
import it.fmach.metadb.isatab.importer.IsatabImporterImpl
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(FEMAssay)
class FEMAssayTests {
	
	static String rootDir = "test/data/org/isatools/isacreator/io/importisa/"
	String configDir = rootDir + "MetaboLightsConfig20130507"
	String isatabDir = rootDir + "Wine_Storage"

    void testSaveAndLoadAssay() {
       	IsatabImporter importer = new IsatabImporterImpl(configDir)
		def investigation = importer.importIsatabFiles(isatabDir)
		
		def assay = investigation.studyList.get(0).assays.get(0)
		assay.accessCode = "UniqueCode"
		
		if (!assay.validate()){
			assay.errors.allErrors.each {
				println it
			}
		}
		
		assay.save(flush: true)
		
		def loadedAssay = FEMAssay.findByInstrumentLike("%Xevo%")
		assertEquals("a_wine_storage_metabolite profiling_mass spectrometry-5.txt", loadedAssay.name)
    }
}
