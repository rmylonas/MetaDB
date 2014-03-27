package it.fmach.metadb.workflow.metams

import static org.junit.Assert.*

import org.junit.*

class PlotPCAIntegrationTests {

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

	static String metaMsConfDir = "resources/conf/metaMS"
	static String rootDir = "resources/it/fmach/metadb/workflow/metams/pipeline/5"
	
	@Test
	public void plotPCATest() {		
		def plotter = new PCAPlotter(metaMsConfDir)
		def origDir = absRootDir + "/"
		
		def workDir = File.createTempFile("pipeline_res", "")
		workDir.delete();
		workDir.mkdir();
		
		def plotFileList = plotter.plotPCA(workDir, "Bottling Type")
		
		assert plotFileList.get(0).contains("PCA")
	}
}
