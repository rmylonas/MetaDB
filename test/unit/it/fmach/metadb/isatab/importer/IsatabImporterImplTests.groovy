package it.fmach.metadb.isatab.importer

import it.fmach.metadb.isatab.model.AccessCode;
import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMRun;
import it.fmach.metadb.isatab.model.FEMSample;
import it.fmach.metadb.isatab.model.FEMStudy;
import it.fmach.metadb.isatab.model.ISAParsingInfo;
import it.fmach.metadb.isatab.model.Instrument;
import it.fmach.metadb.isatab.model.InstrumentPolarity;
import it.fmach.metadb.isatab.model.InstrumentMethod;

import java.util.List;

import org.junit.*

import grails.test.mixin.*

@Mock([AccessCode, Instrument, InstrumentPolarity])
class IsatabImporterImplTests {

	static String rootDir = "test/data/org/isatools/isacreator/io/importisa/"

	@Test
	void testImportIsatabFile() {

		createInstrument().save(flush: true)

		String configDir = rootDir + "MetaboLightsConfig20130507"
		String isatabDir = rootDir + "Wine_Storage"

		IsatabImporter importer = new IsatabImporterImpl(configDir)
		FEMInvestigation investigation = importer.importIsatabFiles(isatabDir)
		Boolean success = investigation.isaParsingInfo.success
		assert success

		List<FEMStudy> studyList = investigation.studyList

		// check study content
		assert 1 == studyList.size()
		FEMStudy study = studyList.get(0)
		assert "Wine_Storage" == study.identifier
		assert "Metabolic changes during wine storage" == study.title
		assert isatabDir == study.iSATabFilePath

		// check assay content
		assert 6 == study.assays.size
		FEMAssay assay = study.assays.get(0)
		assert "a_wine_storage_metabolite profiling_mass spectrometry-5.txt" == assay.name
		assert "Xevo TQ MS (Waters)" == assay.instrument

		//		// check sample content
		//		assert 224 == assay.samples.size()
		//
		//		FEMSample sample_1 = assay.samples.get(0)
		//		assert "QC_H" == sample_1.name
		//		assert 1 == sample_1.rowNumber
		//		assert "NCBITaxon:Vitis vinifera" == sample_1.organism
		//		assert "Wine" == sample_1.organismPart
		//		assert sample_1.factorJSON.contains("QC")
		//
		//		FEMSample sample_3 = assay.samples.get(2)
		//		assert "2401_H" == sample_3.name
		//		assert 3 == sample_3.rowNumber
		//		assert sample_3.factorJSON.contains("House")

		// check run content
		assert 90 == assay.runs.size()
		FEMRun run = assay.runs.get(0)
		assert run.protocolJSON.contains("MS:ACQUITY UPLC")
		assert "Q034_01_00_R_MRM" == run.msAssayName
		assert "0001_R" == run.sampleName
		assert "T:experimentsXevo_Stefania_Maggio2013.PROData" == run.rawSpectraFilePath
		assert "" == run.derivedSpectraFilePath

		// check sample content
		FEMSample sample = run.sample
		assert "0001_R" == sample.name
		assert "NCBITaxon:Vitis vinifera" == sample.organism
		assert "Wine" == sample.organismPart
		assert '{"Sample type":"Sample","Storage type":"Reference","Storage time":"0"}' == sample.factorJSON
	}


	@Test
	void testImportEmpty() {

		String configDir = rootDir + "MetaboLightsConfig20130507"
		String isatabDir = rootDir + "Empty"

		IsatabImporter importer = new IsatabImporterImpl(configDir)
		FEMInvestigation investigation = importer.importIsatabFiles(isatabDir)

		def ISAParsingInfo parsingInfo = investigation.isaParsingInfo
		Boolean success = parsingInfo.success
		assert !parsingInfo.success
		assert 1 == parsingInfo.nrOfErrors
		assert parsingInfo.errorMessage.contains("file does not exist")

	}


	@Test
	void testImportZip() {

		String configDir = rootDir + "MetaboLightsConfig20130507"
		String isatabDir = rootDir + "winecellar_archive.zip"

		IsatabImporter importer = new IsatabImporterImpl(configDir)
		FEMInvestigation investigation = importer.importIsatabZip(isatabDir)

		Boolean success = investigation.isaParsingInfo.success
		assert success
		assert 1 == investigation.studyList.size
		assert "Metabolic changes during wine storage" == investigation.studyList[0].title
		assert "a_wine_storage_metabolite profiling_mass spectrometry-5.txt" == investigation.studyList[0].assays[0].name
	}

	Instrument createInstrument(){
		def pos = new InstrumentPolarity(
				name: 'alternating',
				tag: 'RP_pos',
				startPattern: '1.blank-1.STDmix-4.QC',
				repeatPattern: '6.sample-1.QC',
				endPattern: '1.STDmix-1.blank'
				)

		def neg = new InstrumentPolarity(
				name: 'negative',
				tag: 'RP_neg',
				startPattern: '1.blank-1.STDmix-4.QC',
				repeatPattern: '6.sample-1.QC',
				endPattern: '1.STDmix-1.blank'
				)

		def polarities = [pos, neg]


		def methods = [new InstrumentMethod(name: 'untargeted', polarities: polarities)]

		new Instrument(name: "Xevo", metabolightsName: "Xevo TQ MS (Waters)", methods: methods).save(failOnError: true)
		new Instrument(name: "Synapt", metabolightsName: "SYNAPT HDMS (Waters)", methods: methods).save(failOnError: true)

	}

}
