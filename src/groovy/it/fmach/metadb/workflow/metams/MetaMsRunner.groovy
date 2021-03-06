package it.fmach.metadb.workflow.metams

import org.springframework.transaction.annotation.Transactional;

import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.isatab.model.InstrumentMethod
import it.fmach.metadb.isatab.model.Instrument
import it.fmach.metadb.isatab.model.MetaMsDb
import it.fmach.metadb.isatab.model.MetaMsSubmission


class MetaMsRunner {

	String metaMsDir
	String metaMsDbDir
	String metaMsSettingsDir
	
	String dataDir
	String workDir

	private String fileListPath
	private def factorExporter = new FactorExporter()

	// constructer for tests
	def MetaMsRunner(String metaMsDir){
		this.metaMsDir = metaMsDir
	}

	// complete constructor
	def MetaMsRunner(String metaMsDir, String metaMsDbDir, String metaMsSettingsDir, String dataDir){
		this.metaMsDir = metaMsDir
		this.metaMsDbDir = metaMsDbDir
		this.metaMsSettingsDir = metaMsSettingsDir
		this.dataDir = dataDir
	}
	
	/**
	 * 
	 * Main function to run MetaMS
	 * an object MetaMsSubmission will be added to the assay provided
	 * 
	 * @param assay
	 * @param selectedMsAssayNames
	 * @param rtMin
	 * @param rtMax
	 * @return
	 */
	def runMetaMs(FEMAssay assay, List<String> selectedMsAssayNames, String rtMin, String rtMax, String comment, Boolean identification){
		def workDirRelative = createWorkDir(this.dataDir, assay)
		this.workDir = this.dataDir + "/" + workDirRelative
		
		def selectedRuns = selectAcquiredRuns(assay, selectedMsAssayNames)

		// prepare the fileList used by runMetaMS.R
		this.fileListPath = this.workDir + "/fileList.csv"
		this.prepareFileList(selectedRuns)
		
		// construct the command and save it in a file
		def command = constructCommand(assay, rtMin, rtMax, identification)
		new File(this.workDir + "/command.sh").withWriter{ it << command }
		
		// create a csv file containing the factors
		factorExporter.exportFactorTable(selectedRuns, this.workDir + "/factors.csv")
		

		// create and save this metaMsSubmission
		def submissionName = this.currentMetaMsSubmissionName(assay)
		def metaMsSubmission = new MetaMsSubmission(workDir: this.workDir,
													status: "running",
													selectedRuns: selectedRuns,
													command: '',
													comment: comment,
													name: submissionName)

		// set retention times, if they are available
		if(rtMin && rtMax){
			metaMsSubmission.rtMin = rtMin as Double
			metaMsSubmission.rtMax = rtMax as Double
		}
		
		metaMsSubmission.save(flush: true, failOnError: true)
		assay.addToMetaMsSubmissions(metaMsSubmission)
		assay.save(flush: true, failOnError: true)
		metaMsSubmission.discard()
		def submissionId = metaMsSubmission.id	
		
		// STDout file
		def fwOut = new FileWriter(this.workDir + "/stdout.log")
		def fwErr = new FileWriter(this.workDir + "/stderr.log")
		
		// we execute the script in a separate thread
		Thread.start{
			def proc = command.execute()
			
			BufferedReader stdOut = new BufferedReader(new InputStreamReader(proc.getInputStream()))
			BufferedReader stdErr = new BufferedReader(new InputStreamReader(proc.getErrorStream()))
			
			// create a Thread which writes the StdOut
			Thread.start{
				def line
				
				try{
					while ((line = stdOut.readLine()) != null) {
						fwOut.append(line + "\n")
						fwOut.flush()
					}
					
				}catch(Exception e){/* everything is ok, since stream is closed before finishing*/}
			
				fwOut.close()
			}
			
			// create a Thread which writes the StdErr
			Thread.start{
				def line
				
				try{
					while ((line = stdErr.readLine()) != null) {
						fwErr.append(line + "\n")
						fwErr.flush()
					}
					
				}catch(Exception e){/* everything is ok, since stream is closed before finishing*/}
			
				fwErr.close()
			}
			
			proc.waitFor()

			// save the right status once we're finished
			//metaMsSubmission.refresh()
			def reloadedSubmission = MetaMsSubmission.get(submissionId)
			def status = (proc.exitValue() == 0) ? ("done") : ("failed")
			reloadedSubmission.status = status
			
			MetaMsSubmission.withTransaction {	
				reloadedSubmission.save(flush: true)
			}
			
		}

	}


	def createWorkDir(String dataPath, FEMAssay assay){
		// get the name of the current submission
		def newId = this.currentMetaMsSubmissionName(assay)

		// create the directory(s)
		File newDir = new File(dataPath + "/" + assay.workDir + "/pipeline/" + newId)
		newDir.delete()
		newDir.mkdirs()

		this.workDir = assay.workDir + "/pipeline/" + newId
	}


	def currentMetaMsSubmissionName(FEMAssay assay){
		// construct the current id of metaMsSubmission
		def newId = assay.metaMsSubmissions?.size()
		newId = newId ? (newId + 1) : 1

		return newId
	}


	def constructCommand(FEMAssay assay, String minRt, String maxRt, Boolean identification){
		// call the rscript
		def command = 'Rscript ' + this.metaMsDir + '/runMetaMS.R'

		// instrument parameter (GC or LC)
		command += " -i " + this.getInstrumentChromatography(assay)

		// provide polarity (only if we're in LC mode and if it's negative)
		// the point is that MetaMS only handles pos and neg, but there is also "alternating"
		if(assay.instrumentPolarity == "negative") command += " -p " + assay.instrumentPolarity

		// add the fileList
		command += " -f " + this.fileListPath

		// add the database if user asks for DB identification and DB is not null
		if(identification && assay.method.metaMsDb) command += " -d " + assay.method.metaMsDb.rDataPath

		// add the instrument settings (throw exception if not available)		
		if(! assay.method.metaMsParameterFile) throw new RuntimeException("missing metaMsSetting information in assay method")
		command += " -s " + assay.method.metaMsParameterFile

		// output to workdir
		command += " -o " + this.workDir

		// set minRt and maxRt if available
		if(minRt && maxRt) command += " -m " + minRt + " -x " + maxRt

		return command
	}


	def getInstrumentChromatography(FEMAssay assay){
		// throw an exception if it's neither GC or LC
		if(! assay.instrument.chromatography =~ /[GC|LC]/){
			throw new RuntimeException("illagel instrument chromatography [" + instrument.get(0).chromatography
			+ "]: should be GC or LC")
		}

		return assay.instrument.chromatography
	}


	def prepareFileList(List<FEMRun> selectedRuns){
		new File(this.fileListPath).withWriter { out ->
			selectedRuns.each() { run ->
				out.writeLine(run.derivedSpectraFilePath)
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

		}

		return selectedRuns

	}

}
