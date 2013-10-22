

package it.fmach.metadb.isatab.importer

import it.fmach.metadb.isatab.model.FEMAssay;
import it.fmach.metadb.isatab.model.FEMSample;
import it.fmach.metadb.isatab.model.FEMStudy;

import java.util.List;

import org.junit.*

class IsatabImporterImplTests {

	static String rootDir = "test/data/org/isatools/isacreator/io/importisa/"

	@Test
    void testImportIsatabFiles() {
		
		String configDir = rootDir + "MetaboLightsConfig20130507"
		String isatabDir = rootDir + "Wine_Storage"
		
        IsatabImporter importer = new IsatabImporterImpl(configDir)
		List<FEMStudy> studyList = importer.importIsatabFiles(isatabDir)
		
		// check study content
		assertEquals(1, studyList.size())
		FEMStudy study = studyList.get(0)
		assertEquals("Wine_Storage", study.identifier)
		assertEquals("Metabolic changes during wine storage", study.title)
		assertEquals(isatabDir, study.iSATabFilePath)
		
		// check sample content
		assertEquals(224, study.samples.size())
		
		FEMSample sample_1 = study.samples.get(0)
		assertEquals("QC_H", sample_1.name)
		assertEquals(1, sample_1.rowNumber)
		assertEquals("NCBITaxon:Vitis vinifera", sample_1.organism)
		assertEquals("Wine", sample_1.organismPart)
		assertTrue(sample_1.factors.contains("QC"))
		
		FEMSample sample_3 = study.samples.get(2)
		assertEquals("2401_H", sample_3.name)
		assertEquals(3, sample_3.rowNumber)
		assertTrue(sample_3.factors.contains("House"))
		
		// check assay content
		assertEquals(6, study.assays.size)
		FEMAssay assay = study.assays.get(0)
		assertEquals("a_wine_storage_metabolite profiling_mass spectrometry-5.txt", assay.name)
		assertEquals("Xevo TQ MS (Waters)", assay.instrument)
		
		// check run content
		
		
		
    }
}
