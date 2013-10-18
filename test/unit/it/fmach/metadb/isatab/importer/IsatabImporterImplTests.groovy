package it.fmach.metadb.isatab.importer

import static org.junit.Assert.*
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
		assertEquals("Wine_Storage", studyList.get(0).identifier)
		assertEquals("Metabolic changes during wine storage", studyList.get(0).title)
		assertEquals(isatabDir, studyList.get(0).iSATabFilePath)
		
		// check sample content		
		println("list: " + studyList.get(0))
		
		
    }
}
