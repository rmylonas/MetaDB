package it.fmach.metadb.isatab.importer

import it.fmach.metadb.isatab.model.AccessCode

class AccessCodeGenerator {
	
	AccessCode getNewCode() {
		// create a random number
		def newCode = this.createNewCode()
		
		// recreate a code, if it's already in use
		// to prevent endless loop we throw exception after 100 trials
		def trial = 0
		while(! this.saveNewCode(newCode)){
			if(trial > 100) throw new RuntimeException("unable to create unique Code after 100 trials")
			newCode = this.createNewCode()
			trial ++
		}
		
		return AccessCode.findByCode(newCode)
	}
	
	String createNewCode(){
		Random random = new Random()
		return (random.nextInt(10 ** 4)).toString()
	}
	
	Boolean saveNewCode(String code){
		// check if it already exists
		
		def loadedAc = AccessCode.findByCode(code)
		
		if(loadedAc != null){
			return false
		}
		
		def newAccessCode = new AccessCode(code: code)
		newAccessCode.save(flush: true)
		
		return true
	}
}
