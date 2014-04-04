package it.fmach.metadb.workflow.metams

import it.fmach.metadb.isatab.model.FEMAssay;

class PCAPlotter {
	String metaMsDir
	String workDir
	
	private def factorExporter = new FactorExporter()
	
	def PCAPlotter(String metaMsDir){
		this.metaMsDir = metaMsDir
	}
	
	def plotPCA(String workDir, String factor, Boolean sqrtScaling, Boolean sumNorm){
		
		// construct command line and write to file
		def command = this.constructCommand(workDir, factor, sqrtScaling, sumNorm)
		new File(workDir + "/PCA_plot_command.txt").withWriter{ it << command }
		
		// execute command
		def proc = command.execute()
		proc.waitFor()
		
		// through a runtime exception if return value is not 0
		if(proc.exitValue() != 0){
			throw new RuntimeException("plotPCA failed: " + proc.err.text)
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
	
	
	def constructCommand(String workDir, String factor, Boolean sqrtScaling, Boolean sumNorm){
		// call the rscript
		def command = 'Rscript ' + this.metaMsDir + '/plotPCA.R'

		// set workDir
		command += " -w " + workDir

		// replace spaces by "." for factors
		factor = factor.replace(' ', '.')
		
		// set factor
		command += " -f " + factor
		
		// set scaling and normalization if necessary
		if(sqrtScaling) command += " -s"
		if(sumNorm) command += " -n"

		return command
	}
	
}
