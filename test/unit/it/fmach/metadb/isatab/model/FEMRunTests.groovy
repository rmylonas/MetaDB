package it.fmach.metadb.isatab.model



import grails.test.mixin.*

import org.junit.*

import it.fmach.metadb.isatab.importer.IsatabImporter
import it.fmach.metadb.isatab.importer.IsatabImporterImpl
import it.fmach.metadb.isatab.testHelper.InstrumentCreator
/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([AccessCode, Instrument, FEMSample])
@TestFor(FEMRun)
class FEMRunTests {

	static String rootDir = "test/data/org/isatools/isacreator/io/importisa/"
	String configDir = rootDir + "MetaboLightsConfig20130507"
	String isatabDir = rootDir + "Wine_Storage"
	
    void testSaveAndLoadRun() {
		// create instruments
		def creator = new InstrumentCreator()
		creator.createInstrument()
		
        IsatabImporter importer = new IsatabImporterImpl(configDir)
		def investigation = importer.importIsatabFiles(isatabDir)
		
		def run = investigation.studyList.get(0).assays.get(0).runs.get(0)
		
		if (!run.validate()){
			run.errors.allErrors.each {
				println it
			}
		}
		
		run.save(flush: true)
		
//		def sample = FEMSample.findByName("0001_R")
//		println("here: "+sample)
//		def loadedRun = FEMRun.findBySample(sample)
		def loadedRun = FEMRun.list().get(0)
		String protocols = loadedRun.protocolJSON
		assert protocols.contains("Phenolics")
    }
}
