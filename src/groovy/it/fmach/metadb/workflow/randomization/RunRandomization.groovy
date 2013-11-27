package it.fmach.metadb.workflow.randomization

import java.util.List;

import it.fmach.metadb.helper.DeepCopier
import it.fmach.metadb.isatab.model.FEMAssay;
import it.fmach.metadb.isatab.model.FEMRun;
import it.fmach.metadb.isatab.model.FEMSample
import it.fmach.metadb.isatab.model.InstrumentMethod;

/**
 * @author mylonasr
 *
 *	Generate randomized samples with all the Pre- and Suffixes
 *	Add QC, STDmix and blanks according to rules defined by {@link RandomizeSetting}
 *
 *	following entities are allowed: QC, STDmix, blank, sample
 *	put number of entity followed by point and entity
 *	seperate entities by -
 *
 *	example:
 *
 *	startPattern = "3.blank"
 *	repeatPatter = "1.STDmix-1.QC-2.sample"
 *	endPattern = "1.STDmix-1.QC-1.blank"
 *
 */
class RunRandomization {
	
	def deepCopier = new DeepCopier()

	FEMAssay randomizeAssay(FEMAssay assay){
		assay.randomizedRuns = this.randomizeRuns(assay.runs, assay.method, assay.accessCode.code, assay.instrumentPolarity)
		assay.status = "randomized"
		return assay
	}
	
	List<FEMRun> randomizeRuns(List<FEMRun> runs, InstrumentMethod method, String prefix, String instrumentPolarity){
		def randRuns = []
		
		// create new Samples for QC and STDmix
		def sampleMap = ['QC': new FEMSample(name: 'QC'), 
						'STDmix': new FEMSample(name: 'STDmix'),
						'blank': new FEMSample(name: 'blank')]
		
		// randomize runs
		def tmpRuns = deepCopier.deepCopy(runs)
		Collections.shuffle(tmpRuns, new Random())
		
		// fill in the start part
		randRuns = this.createPatternSet(method.startPattern, sampleMap)
		
		// fill in the sample part
		while(tmpRuns.size() > 0){
			randRuns = randRuns + this.createPatternSet(method.repeatPattern, tmpRuns, sampleMap)
		}
		
		// fill in the end part
		randRuns = randRuns + this.createPatternSet(method.endPattern, sampleMap)
		
		// add prefix, suffix and numeration
		randRuns = this.addInformation(prefix, method.tag, randRuns, instrumentPolarity)
		
		return randRuns
	}
	
	private List<FEMRun> addInformation(String prefix, String suffix, List<FEMRun> runList, String instrumentPolarity){
		
		for(i in 0..(runList.size-1)){
			// change the assay name
			def name = prefix + '_' + sprintf('%03d', i+1) + "_" + runList[i].msAssayName.trim() + "_" + suffix
			runList[i].msAssayName = name
			
			// set correct rowNumber
			runList[i].rowNumber = i
			
			// set instrumentPolarity
			runList[i].scanPolarity = instrumentPolarity
			
			// set the status to "randomized"
			runList[i].status = "randomized"
		}
		
		return runList	
	}
	
	private List<FEMRun> createPatternSet(String pattern, List<FEMRun> runs, Map<String, FEMSample> sampleMap){
		List runList = createPatternSet(pattern, sampleMap)
		List returnList = []
		
		// consume the runs
		for(i in 0..(runList.size-1)){
			// if it's a sample, they're null
			if(runList[i] == null){
				if(runs.size > 0){
					// we set the msAssayName, and then add them
					runs[0].msAssayName = runs[0].sample.name
					returnList << runs[0]
					runs.remove(0)
				}
			}else{
				returnList << runList[i]
			}
		}
		return returnList
	}
	
	private List<String> createPatternSet(String pattern, Map<String, FEMSample> sampleMap){
		def runList = []
		
		for(def pair: this.parsePatternString(pattern)){
			for(i in 1..(pair[0].toInteger())){				
				switch(pair[1]){
					case 'QC':
						runList.add(new FEMRun(msAssayName: 'QC', sample: sampleMap.QC))
						break
					
					case 'STDmix':
						runList.add(new FEMRun(msAssayName: 'STDmix', sample: sampleMap.STDmix))
						break
						
					case 'blank':
						runList.add(new FEMRun(msAssayName: 'blank', sample: sampleMap.blank))
						break
						
					case 'sample':
						runList.add(null)
						break
						
					default:
						throw new RuntimeException("This pattern should only contain [blank, sample, QC or STDmix], but was [" + pair[1] + "] instead")
				}
			}
		}
		
		return runList
	}
	
	private List parsePatternString(String pattern){
		List returnPattern = []
		
		for(String p: pattern.split("-")){
			def pair = p.split("\\.")
			returnPattern.add(pair)
		}
		
		return returnPattern
	}	
	
}
