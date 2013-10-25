package it.fmach.metadb.isatab.validator

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Level;
import org.isatools.isatab.gui_invokers.GUIISATABValidator;
import org.isatools.isatab.gui_invokers.GUIInvokerResult;
import org.isatools.isatab.isaconfigurator.ISAConfigurationSet;
import org.isatools.tablib.utils.logging.TabLoggingEventWrapper;

import it.fmach.metadb.isatab.model.ISAParsingInfo


class IsatabValidatorImpl implements IsatabValidator {

	GUIISATABValidator validator

	/**
	 * @param configDir Directory containing xml files describing valid ISAtab structures (https://github.com/ISA-tools/ISAconfigurator)
	 */
	def IsatabValidatorImpl(String configDir){
		ISAConfigurationSet.setConfigPath(configDir)
		validator = new GUIISATABValidator()
	}


	@Override
	public ISAParsingInfo validateIsatabFile(String isatabPath) {
		def parsingInfo = new ISAParsingInfo()

		GUIInvokerResult result = validator.validate(isatabPath);

		// fill in the parsing-info
		if("SUCCESS" == result.name()){
			parsingInfo.success = true
			parsingInfo.status = "validated"
		}else{
			parsingInfo.success = false
			parsingInfo.status = "validation failed"
		}

		// add errorMessage as JSON string
		def errorSet = getValidatorReport(validator.getLog())
		def builder = new groovy.json.JsonBuilder()
		builder(errorSet)
		parsingInfo.errorMessage = builder.toString()
		parsingInfo.nrOfErrors = errorSet.size()

		return parsingInfo
	}


	protected List<String> getValidatorReport(List<TabLoggingEventWrapper> logResult) {
		def messages = []

		for (TabLoggingEventWrapper tlew : logResult) {
			Level eventLevel = tlew.getLogEvent().getLevel()
			if (eventLevel.equals(Level.ERROR)) {
				messages.add(tlew.getFormattedMessage())
			}
		}

		return messages
	}

}
