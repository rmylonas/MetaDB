package it.fmach.metadb.helper

class JsonConverter {
	
	def parseFactorJson(String factorJSON){
		def names = []
		def vals = []
		
		//remove {} and ""
		def clean = factorJSON.replace('{', '').replace('}', '').replace('"', '')
		
		// if there are no factors we return an empty list
		if(! clean) return null
		
		def groups = clean.split(",")
		
		groups.each{
			def entry = it.split(":")
			names << entry[0]
			def valToAdd = (entry.size() > 1) ? (entry[1]) : ('')
			vals << valToAdd
		}
		
		return [names, vals]
	}
	
}
