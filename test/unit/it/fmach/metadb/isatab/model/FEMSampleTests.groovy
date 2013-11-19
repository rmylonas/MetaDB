package it.fmach.metadb.isatab.model

import grails.test.mixin.*
import it.fmach.metadb.isatab.importer.IsatabImporter
import it.fmach.metadb.isatab.importer.IsatabImporterImpl
import it.fmach.metadb.isatab.testHelper.TestDbSetup
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([AccessCode, Instrument, FEMRun])
@TestFor(FEMSample)
class FEMSampleTests {
	
    void testSaveAndLoadSample() {
		def sample = new FEMSample(name: "0001_R", organism: "Vitis vinae")
		sample.save(flush: true)
		
		assert 1 == FEMSample.count()
		def loadedSample = FEMSample.findByOrganismLike("%Vitis%")
		String sampleName = loadedSample.name
		assert "0001_R" == sampleName
    }
	
	
}
