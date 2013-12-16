package it.fmach.metadb.workflow.metams

import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.isatab.model.InstrumentMethod
import it.fmach.metadb.isatab.model.MetaMsDb
import it.fmach.metadb.isatab.model.MetaMsSubmission


class MetaMsRunner {
	
	String metaMsDir
	
	private String fileListPath
	
	def MetaMsRunner(String metaMsDir){
		this.metaMsDir = metaMsDir
	}
	
	def runMetaMs(String workDir, FEMAssay assay, List<String> selectedMsAssayNames, InstrumentMethod method, Double rtMin, Double rtMax){
				
		def selectedRuns = selectAcquiredRuns(assay, selectedMsAssayNames)
		
		// prepare the fileList used by runMetaMS.R
		this.fileListPath = workDir + "/fileList.csv"
		prepareFileList(selectedRuns)
		
		// create and save this metaMsSubmission
		def metaMsSubmission = new MetaMsSubmission(workDir: workDir, status: "running", selectedRuns: selectedRuns)
		metaMsSubmission.save(flush: true)
		
		// construct the command
		def command = 'Rscript ' + this.metaMsDir + '/runMetaMS.R '
		command += ""
		
		
		// we execute the script in a separate thread
		Thread.start{
			def proc = command.execute()
			proc.waitFor()
			
			// write stdout and stderr
			new File(workDir + "/stdout.log").withWriter{ it << proc.in.text }
			new File(workDir + "/stderr.log").withWriter{ it << proc.err.text }
			
			// save the right status once we're finished
			def status = (proc.exitValue() == 0) ? ("done") : ("failed")
			metaMsSubmission.status = status
			metaMsSubmission.save(flush: true)
		}

	}
	
	def prepareFileList(List<FEMRun> selectedRuns){
		new File(this.fileListPath).withWriter { out ->
			selectedRuns.each() { run ->
				out.writeLine(run.msAssayName)
			}
		}
	}
	
	def selectAcquiredRuns(FEMAssay assay, List<String> selectedMsAssayNames){
		
		// construct a hashmap of selected runs
		def selectedRuns = []
		def selectedNamesHash = [:]
		
		selectedMsAssayNames.each{
			selectedNamesHash[it] = 1
		}
		
		// select only runs of status "extracted"
		assay.acquiredRuns.each{ run ->
			
			if(run.status == "extracted"){
				if(selectedNamesHash[run.msAssayName]) selectedRuns << run
			}
			
			run.additionalRuns?.each{ additional ->
				if(additional.status == "extracted"){
					if(selectedNamesHash[additional.msAssayName]) selectedRuns << additional
				}
			}
			
		}
		
		return selectedRuns
		
	}
	
}
