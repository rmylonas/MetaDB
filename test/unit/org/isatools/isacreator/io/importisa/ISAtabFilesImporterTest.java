package org.isatools.isacreator.io.importisa;

import static org.junit.Assert.*;

import org.isatools.errorreporter.model.ErrorMessage;
import org.isatools.errorreporter.model.ISAFileErrorReport;
import org.isatools.isacreator.model.Assay;
import org.isatools.isacreator.model.Factor;
import org.isatools.isacreator.model.Investigation;
import org.isatools.isacreator.model.Study;
import org.isatools.isacreator.model.StudyDesign;
import org.junit.Test;

public class ISAtabFilesImporterTest {

	final static String rootDir = "test/data/org/isatools/isacreator/io/importisa/";
	
	@Test
	public void testWineStorage() {
		String configDir = rootDir + "MetaboLightsConfig20130507";
		String isatabParentDir = rootDir + "Wine_Storage";
		
		ISAtabFilesImporter importer = new ISAtabFilesImporter(configDir);
		assertNotNull(importer);
		importer.importFile(isatabParentDir);
		
		Investigation inv = importer.getInvestigation();
		assertNotNull(inv);
		
		assertEquals("Wine_Storage", inv.getStudies().keySet().iterator().next());
		
		Study study = inv.getStudies().get("Wine_Storage");
		assertEquals("Metabolic changes during wine storage", study.getStudyTitle());
		
		Object[][] sampleMatrix = study.getStudySampleDataMatrix();
		assertEquals(224, sampleMatrix.length-1);

		assertEquals(3, study.getFactors().size());
		
		assertEquals(6, study.getAssays().keySet().size());
		
		Assay assay = study.getAssays().get("a_wine_storage_metabolite profiling_mass spectrometry-1.txt");
		assertEquals("SYNAPT HDMS (Waters)", assay.getAssayPlatform());
		
		Object[][] assayMatrix = assay.getAssayDataMatrix();
		assertEquals(58, assayMatrix.length-1);
		
	}
	
	@Test
	public void testEmptyDirectory() {
		String configDir = rootDir + "MetaboLightsConfig20130507";
		String isatabParentDir = rootDir + "Empty";
		
		ISAtabFilesImporter importer = new ISAtabFilesImporter(configDir);
		assertNotNull(importer);
		importer.importFile(isatabParentDir);
		
		String errorSummary = "";
		String errorMessage = "";
		
		for(ISAFileErrorReport error: importer.getMessages()){
			errorSummary = error.getProblemSummary();
			for(ErrorMessage message : error.getMessages()){
				errorMessage = message.getMessage();
			}
		}
		
		assertEquals("1 problem found", errorSummary);
		assertTrue(errorMessage.contains("file does not exist"));
		
		Investigation inv = importer.getInvestigation();
		assertNull(inv);	
	}
	

}
