package it.fmach.metadb.workflow.metams

import java.util.List;

import grails.test.mixin.*
import it.fmach.metadb.isatab.testHelper.TestDomainCreator

import org.junit.*

import it.fmach.metadb.isatab.model.FEMAssay
import it.fmach.metadb.isatab.model.FEMStudy
import it.fmach.metadb.isatab.model.FEMRun
import it.fmach.metadb.isatab.model.InstrumentMethod
import it.fmach.metadb.isatab.model.Instrument
import it.fmach.metadb.isatab.model.AccessCode
import it.fmach.metadb.isatab.model.MetaMsSubmission
import it.fmach.metadb.isatab.model.MetaMsDb

@Mock([FEMStudy, FEMAssay, FEMRun, InstrumentMethod, Instrument, AccessCode, MetaMsSubmission, MetaMsDb])
class PCAPlotterTest {
	
	static String metaMsConfDir = "resources/conf/metaMS"
	static String rootDir = "test/data/it/fmach/metadb/workflow/metams/"
	static String absRootDir = new File(rootDir).getAbsolutePath().toString()
	
	@Test
	public void plotPCATest() {		
		def plotter = new PCAPlotter(metaMsConfDir)
		def workDir = absRootDir + "/pipeline/5"
		
		def plotFileList = plotter.plotPCA(workDir, "Bottling Type")
		
		assert plotFileList.get(0).contains("PCA")
	}
	
}

