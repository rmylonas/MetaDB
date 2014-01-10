package it.fmach.metadb.helper

import java.util.Date;

import it.fmach.metadb.isatab.model.FEMRun;
import it.fmach.metadb.isatab.model.FEMSample;

class DeepCopier {

	List<FEMRun> deepCopy(List<FEMRun> runs){
		def newRuns = []
		
		runs.each{
			newRuns.add(this.deepCopy(it))	
		}
		
		return newRuns
	}
	
	FEMRun deepCopy(FEMRun run){
		def newRun = new FEMRun()
		
		// create new objects
		newRun.rowNumber = new Integer(run.rowNumber)
		// newRun.protocolJSON = new String(run.protocolJSON)
		newRun.msAssayName = new String(run.msAssayName)
		newRun.rawSpectraFilePath = new String(run.rawSpectraFilePath)
		newRun.derivedSpectraFilePath = new String(run.derivedSpectraFilePath)
		newRun.status = new String(run.status)
		newRun.scanPolarity = new String(run.scanPolarity)
		
		// but we take the same sample
		newRun.sample = run.sample
		
		return newRun
	}
	
}
