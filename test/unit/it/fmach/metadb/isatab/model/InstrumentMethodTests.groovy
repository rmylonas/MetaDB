package it.fmach.metadb.isatab.model



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(InstrumentMethod)
class InstrumentMethodTests {

    void testSaveAndLoad() {
       def instr = new InstrumentMethod(name: 'positive RP', tag: 'rp_pos', startPattern: '1.QC-1.STDmix', repeatPattern: '1.sample-1.QC', endPattern: '3.QC')
	   instr.save()
	   
	   def loadedInstr = InstrumentMethod.findByName('positive RP')
	   assert 'rp_pos' == loadedInstr.tag
    }
}
