package it.fmach.metadb.isatab.importer

import java.util.List;

import org.isatools.isacreator.model.Investigation;

import it.fmach.metadb.isatab.model.FEMStudy;

/**
 * @author mylonasr
 *
 * Convert Objects from the isatools into our own Objects (typically from Investigation into our own Study)
 */
interface ISAtoolsModelConverter {

	List<FEMStudy> convertInvestigation(Investigation investigation)
	
}
