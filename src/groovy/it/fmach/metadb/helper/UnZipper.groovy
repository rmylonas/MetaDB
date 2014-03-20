package it.fmach.metadb.helper

import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class UnZipper {

	def unzip(String zipFile, String extractionDir){
		byte[] buffer = new byte[1024];
		
			   //throw an exception if extractionDir does not exist
			   File folder = new File(extractionDir);
			   if(!folder.exists()){
				   throw new RuntimeException("extraction folder does not exist: [" + extractionDir + "]")
			   }
		
			   //get the zip file content
			   ZipInputStream zis =
				   new ZipInputStream(new FileInputStream(zipFile));
			   //get the zipped file list entry
			   ZipEntry ze = zis.getNextEntry();
		
			   while(ze!=null){
		
				  String fileName = ze.getName();
				  File newFile = new File(extractionDir + File.separator + fileName);
				  
				   //create all non exists folders
				   //else you will hit FileNotFoundException for compressed folder
				   new File(newFile.getParent()).mkdirs();
				   
				   if (ze.isDirectory()){
					   newFile.mkdirs()
				   }else{   
					   FileOutputStream fos = new FileOutputStream(newFile);
			
					   int len;
					   while ((len = zis.read(buffer)) > 0) {
						  fos.write(buffer, 0, len);
					   }
			
					   fos.close();
				   }
				   
				   ze = zis.getNextEntry();
			   }
		
			   zis.closeEntry();
			   zis.close();
		
	}
	
}
