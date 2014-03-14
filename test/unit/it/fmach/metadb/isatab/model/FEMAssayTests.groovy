package it.fmach.metadb.isatab.model


import grails.test.mixin.*
import it.fmach.metadb.User
import it.fmach.metadb.isatab.importer.IsatabImporter
import it.fmach.metadb.isatab.importer.IsatabImporterImpl
import it.fmach.metadb.isatab.testHelper.TestDbSetup
import org.codehaus.groovy.grails.io.support.ClassPathResource
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([AccessCode, Instrument, InstrumentMethod, FEMRun])
@TestFor(FEMAssay)
class FEMAssayTests {
	
	static def currentUser = new User(username: 'roman', password: 'namor', workDir: '/home/mylonasr/MetaDB/data/roman')
	static String rootDir = "resources/org/isatools/isacreator/io/importisa/"
	String configDir = rootDir + "MetaboLightsConfig20130507"
	String isatabDir = rootDir + "Wine_Storage"

    void testSaveAndLoadAssay() {

		def isatabFilePath = new ClassPathResource(isatabDir).getFile().getAbsolutePath()
		def configFilePath = new ClassPathResource(configDir).getFile().getAbsolutePath()

		// create instruments
		def creator = new TestDbSetup()
		creator.createInstrument()
		
		def workDir = File.createTempFile("test_workdir", "")
		workDir.delete();
		workDir.mkdir();

		IsatabImporter importer = new IsatabImporterImpl(configFilePath, workDir.getAbsolutePath(), currentUser)
		def investigation = importer.importIsatabFiles(isatabFilePath)
		
		def assay = investigation.studyList.get(0).assays.get(0)
		assay.accessCode = new AccessCode(code: "UniqueCode")
		
		if (!assay.validate()){
			assay.errors.allErrors.each {
				println it
			}
		}
		
		assay.save(flush: true)
		
		def loadedAssay = FEMAssay.findByName("a_wine_storage_metabolite profiling_mass spectrometry-5.txt")
		assert "a_wine_storage_metabolite profiling_mass spectrometry-5.txt" == loadedAssay.name
		
		// sometimes it works, sometimes not -> most probably a problem with lazy loading / saving
		
/*		def instrument = Instrument.findByMetabolightsNameLike("%Xevo%")
		def loadedAssay2 = FEMAssay.findByInstrument(instrument)
		assert loadedAssay2*/
		
    }
}
