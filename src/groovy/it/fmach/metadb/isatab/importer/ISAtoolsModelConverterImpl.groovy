package it.fmach.metadb.isatab.importer

import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.isatab.model.FEMSample
import it.fmach.metadb.isatab.model.FEMStudy
import it.fmach.metadb.isatab.model.Instrument



import it.fmach.metadb.isatab.importer.AccessCodeGenerator

/**
 * @author mylonasr
 *
 * Convert Objects from the isatools into our own Objects (typically from Investigation into our own Study)
 */
class ISAtoolsModelConverterImpl {
	def accessCodeGenerator = new AccessCodeGenerator()
	def instrumentMap = [:]
	
	String workDir
	
	// field-names which are parsed
	static final def SAMPLE_NAME = "Sample Name"
	static final def SAMPLE_ORGANISM = "Characteristics[Organism]"
	static final def SAMPLE_ORGANISM_PART = "Characteristics[Organism part]"
	static final def SAMPLE_SOURCE_NAME = "Source Name"
	static final def RUN_MS_ASSAY_NAME = "MS Assay Name"
	static final def RUN_RAW_FILE = "Raw Spectral Data File"
	static final def RUN_DERIVED_FILE= "Derived Spectral Data File"
	static final def RUN_SCAN_POLARITY= "Parameter Value[Scan polarity]"
	
	ISAtoolsModelConverterImpl(String workDir){
		// create map of available instruments and polarities
		Instrument.list().each {inst->
			instrumentMap[inst.metabolightsName] = inst
		}
		
		this.workDir = workDir		
	}
	
		
		
}
