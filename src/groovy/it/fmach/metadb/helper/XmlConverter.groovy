package it.fmach.metadb.helper

import it.fmach.metadb.onthology.Organism;

class XmlConverter {

	String organismToXml(List<Organism> organismList){
		def xml = new StringBuilder()
		
		// start
		xml << "<preferredTerms>" << "\n" 
		
		// loop over organism
		organismList.each{
			xml << '<preferredTerm id="' << it.id << '">' << "\n" 
			xml << '<token>' << it.name << '</token>' << "\n"
			xml << "<comments>\n";
			xml << '	<synonyms>' << it.alternativeNames << '</synonyms>' << "\n"
			xml << '	<description>'
			if(it.description) xml << it.description
			xml << '	</description>' << "\n"
			xml << "</comments>\n";
			xml << '</preferredTerm>' << "\n"
		}
		
		// end
		xml << "</preferredTerms>"
		
		return xml.toString()
	}
		
}
