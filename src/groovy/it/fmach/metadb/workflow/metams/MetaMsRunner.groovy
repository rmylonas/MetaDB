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
		
		
		Thread.start{
			def reader = new BufferedReader(new InputStreamReader(proc.in))
			def line = "go!"
		    while ((line = reader.readLine()) != null) {
		        println(line)
		    }
		}
		
		//proc.waitFor()
		
	/*	// Obtain status and output
		println "return code: ${ proc.exitValue()}"
		println "stderr: ${proc.err.text}"
		println "stdout: ${proc.in.text}"*/
	}
	
}
