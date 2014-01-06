package it.fmach.metadb.workflow.metams

import it.fmach.metadb.isatab.model.FEMAssay;

class PCAPlotter {
	String metaMsDir
	String workDir
	
	private def factorExporter = new FactorExporter()
	
	def PCAPlotter(String metaMsDir){
		this.metaMsDir = metaMsDir
	}
	
	def plotPCA(String workDir, String factor){
		def command = this.constructCommand(workDir, factor)
		
		def proc = command.execute()
		proc.waitFor()
		
		// through a runtime exception if return value is not 0
		if(proc.exitValue() != 0){
			throw new RuntimeException(proc.err.text)
		}
		
		// otherwise return a list of all png-files in workDir
		return this.getPlotFiles(workDir)
	}
	
	
	def getPlotFiles(String workDir){
		def pattern = ~/.*\.png/
		def list = []
		
		def dir = new File(workDir)
		dir.eachFileMatch (pattern) { file ->
		  list << file.getAbsolutePath()
		}
		
		return list
	}
	
	
	def constructCommand(String workDir, String factor){
		// call the rscript
		def command = 'Rscript ' + this.metaMsDir + '/plotPCA.R'

		// set workDir
		command += " -w " + workDir

		// replace spaces by "." for factors
		factor = factor.replace(' ', '.')
		
		// set factor
		command += " -f " + factor

		return command
	}
	
}
