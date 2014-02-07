package it.fmach.metadb.helper

import it.fmach.metadb.onthology.Organism;
import grails.test.mixin.*

import org.junit.*


class XmlConverterTest {

	@Test
	public void testOrganismToXml() {
		
		// prepare the data
		def organismList = []
		organismList << new Organism(name: 'shark', alternativeNames: 'deadly')
		organismList << new Organism(name: 'panda', alternativeNames: 'sweet', description: 'so sweet!')
		
		def xmlConverter = new XmlConverter()
		def organismXml = xmlConverter.organismToXml(organismList)
		assert organismXml.contains("<description>so sweet!</description>")
		
	}
	
}
