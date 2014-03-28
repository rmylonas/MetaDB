package it.fmach.metadb.user

import org.junit.*
import grails.test.mixin.*

class UserWorkDirGeneratorTest {

	@Test
	void createWorkDirTest(){
		File tempDir = File.createTempFile("createWorkDirTest_", Long.toString(System.nanoTime()))
		tempDir.delete()
		
		def dirGenerator = new UserWorkDirGenerator()
		def workDir = dirGenerator.createWorkDir(tempDir.absolutePath)
		
		// check if isatab directory was created
		def isatabDir = new File(workDir + "/data")
		assert isatabDir.exists()
	}
	
	@Test
	void createWorkDirExceptionTest(){
		File tempDir = File.createTempFile("createWorkDirTest_", Long.toString(System.nanoTime()))
		tempDir.delete()
		
		// put something into this directory to provoke a RuntimeException
		def someDir = new File(tempDir.absolutePath + "/something")
		someDir.mkdirs()
		
		def dirGenerator = new UserWorkDirGenerator()
		
		String message
		
		try{
			dirGenerator.createWorkDir(tempDir.absolutePath)
		}catch(Exception e){
			message = e.message
		}
		
		assert message.contains("already")

	}
	
}
