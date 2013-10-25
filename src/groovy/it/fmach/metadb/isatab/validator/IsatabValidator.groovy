package it.fmach.metadb.isatab.validator

import it.fmach.metadb.isatab.model.ISAParsingInfo;


interface IsatabValidator {
	ISAParsingInfo validateIsatabFile(String isatabPath);
	
}
