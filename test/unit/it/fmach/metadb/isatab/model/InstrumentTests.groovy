


package it.fmach.metadb.isatab.model

import grails.test.mixin.*

import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
//@Mock([AccessCode, Instrument])
@TestFor(Instrument)
class InstrumentTests {

    void testSaveAndLoad() {
       def instr = new Instrument(name: 'Xevo', 
		   metabolightsName: 'Xevo Metabolights',
		   chromatography: "LC")
	   instr.save()
	   
	   def loadedInstr = Instrument.findByName('Xevo')
	   assert 'Xevo Metabolights' == loadedInstr.metabolightsName
    }
}
