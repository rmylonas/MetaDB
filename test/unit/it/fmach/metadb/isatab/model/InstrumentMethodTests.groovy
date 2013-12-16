package it.fmach.metadb.isatab.model


import it.fmach.metadb.isatab.instrument.Polarity;
import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([Instrument, MetaMsDb])
@TestFor(InstrumentMethod)
class InstrumentMethodTests {

    void testSaveAndLoad() {
		def metaMsDb = new MetaMsDb(name: "default", rDataPath: "some/path")
		
		def polarities = [Polarity.POSITIVE.metabolightsName, Polarity.NEGATIVE.metabolightsName, Polarity.ALTERNATING.metabolightsName]
		
		
       def instr = new InstrumentMethod(name: 'positive RP', 
		   tag: 'rp_pos', 
		   startPattern: '1.QC-1.STDmix', 
		   repeatPattern: '1.sample-1.QC', 
		   endPattern: '3.QC', 
		   randomization: true,
		   metaMsDb: metaMsDb)
	   
	   new Instrument(name: "Synapt",
		   metabolightsName: "SYNAPT HDMS (Waters)",
		   methods: instr,
		   polarities: polarities,
		   chromatography: "LC"
		   ).save(failOnError: true)
	   
	   
	   def loadedInstrMethod = InstrumentMethod.findByName('positive RP')
	   assert 'rp_pos' == loadedInstrMethod.tag
	   
	   // get the instrument of this method
	   def loadedInstr = Instrument.withCriteria {
		   methods {
			 idEq(loadedInstrMethod.id)
		   }
		}
	   
	   assert "Synapt" == loadedInstr.get(0).name

    }
}
