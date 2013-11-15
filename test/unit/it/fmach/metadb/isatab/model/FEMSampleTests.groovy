package it.fmach.metadb.isatab.model

import grails.test.mixin.*
import it.fmach.metadb.isatab.importer.IsatabImporter
import it.fmach.metadb.isatab.importer.IsatabImporterImpl
import it.fmach.metadb.isatab.testHelper.InstrumentCreator
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([AccessCode, Instrument, FEMRun])
@TestFor(FEMSample)
class FEMSampleTests {

	static String rootDir = "test/data/org/isatools/isacreator/io/importisa/"
	String configDir = rootDir + "MetaboLightsConfig20130507"
	String isatabDir = rootDir + "Wine_Storage"
	
    void testSaveAndLoadSample() {
		// create instruments
		def creator = new InstrumentCreator()
		creator.createInstrument()
		
		IsatabImporter importer = new IsatabImporterImpl(configDir)
		def investigation = importer.importIsatabFiles(isatabDir)
		
		def run = investigation.studyList.get(0).assays.get(0).runs.get(0)
		run.save(flush: true)
		
		assert 1 == FEMSample.count()
		def loadedSample = FEMSample.findByOrganismLike("%Vitis%")
		String sampleName = loadedSample.name
		assert "0001_R" == sampleName
    }
	
	
}
