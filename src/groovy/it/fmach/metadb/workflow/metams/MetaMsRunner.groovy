package it.fmach.metadb.workflow.metams

import it.fmach.metadb.isatab.model.FEMAssay

class MetaMsRunner {
	
	String metaMsDir
	
	def MetaMsRunner(String metaMsDir){
		this.metaMsDir = metaMsDir
	}
	
	def runMetaMs(String workDir, FEMAssay assay, List<String> selectedMsAssayNames){
		println(workDir)
		def command = 'Rscript ' + this.metaMsDir + '/runMetaMS.R'
		println(command)
		def proc = command.execute()
		
		// > ' + workDir + "/stdout.log
		
		
//		Thread.start{
//			def reader = new BufferedReader(new InputStreamReader(proc.in))
//			def line = "go!"
//		    while ((line = reader.readLine()) != null) {
//		        println(line)
//		    }
//		}
		
		proc.waitFor()
		
		// Obtain status and output
		println "return code: ${ proc.exitValue()}"
		println "stderr: ${proc.err.text}"
		println "stdout: ${proc.in.text}"
	}
	
	
	def selectAcquiredRuns(FEMAssay assay, List<String> selectedMsAssayNames){
		
		// construct a hashmap of selected runs
		def selectedRuns = [:]
		runSelection.each{
			selectedRuns[it] = 1
		}
		
		// select only runs of status "acquired"
		assay.acquiredRuns.each{ run ->
			
			if(run.status == "acquired"){
				if(selectedRuns[run.msAssayName]) selectedRuns[run.msAssayName] = 2
			}
			
			
			run.additionalRuns?.each{ additional ->
				if(additional.status == "acquired"){
					if(selectedRuns[additional.msAssayName]) selectedRuns[additional.msAssayName] = 2
				}
			}
			
		}
		
		// make a list of runs which have a 2
		def returnList = []
		selectedRuns.each{ key, value ->
			if(value == 2) returnList << key
		}
		
		return selectedRuns
		
	}
	
}
