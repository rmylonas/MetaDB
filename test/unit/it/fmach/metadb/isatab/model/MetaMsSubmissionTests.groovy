package it.fmach.metadb.isatab.model

import java.util.List
import org.apache.commons.lang.StringUtils
import grails.test.mixin.*

import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(MetaMsSubmission)
class MetaMsSubmissionTests {

   void testInsertAndLoad() {  
	   
       new MetaMsSubmission(workDir: '/some/path', 
		   					status: 'done', 
							command: 'Rscript command', 
							selectedRuns: [],
							name: '1').save(flush: true, failOnError: true)
	   
	   assert MetaMsSubmission.count() == 1
	   def loadedSub = MetaMsSubmission.findByStatus("done")
	   assert loadedSub != null
    }
}
