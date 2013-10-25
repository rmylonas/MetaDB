package org.isatools.isatab.isaconfigurator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Level;
import org.isatools.isatab.gui_invokers.GUIISATABValidator;
import org.isatools.isatab.gui_invokers.GUIInvokerResult;
import org.isatools.tablib.utils.logging.TabLoggingEventWrapper;
import org.junit.Test;
import static org.junit.Assert.*;

public class ISAConfiguratorValidatorTest {

	final static String rootDir = "test/data/org/isatools/isacreator/io/importisa/";
		
		 @Test
		 public void testInvalidISAtab() {
				String configDir = rootDir + "MetaboLightsConfig20130507";
				String isatabParentDir = rootDir + "Invalid_Wine_Storage";
			 
		        ISAConfigurationSet.setConfigPath(configDir);
		        
                GUIISATABValidator validator = new GUIISATABValidator();
                GUIInvokerResult result = validator.validate(isatabParentDir);      
                
                Set<String> errorMessages = getValidatorReport(validator.getLog());
                assertEquals("ERROR", result.name());
                assertEquals(9, errorMessages.size());
                
                String oneMessage = errorMessages.iterator().next();
                
                assertTrue(oneMessage.contains("study:Wine_Storage"));
                assertTrue(oneMessage.contains("fmt:Sample File file:s_Wine_Storage.txt"));
                assertTrue(oneMessage.contains("study:Wine_Storage"));
                assertTrue(oneMessage.contains("spectrometry-5.txt"));
		    }
		 
		 @Test
		 public void testValidISAtab() {
				String configDir = rootDir + "MetaboLightsConfig20130507";
				String isatabParentDir = rootDir + "Wine_Storage";
			 
		        ISAConfigurationSet.setConfigPath(configDir);
		        
                GUIISATABValidator validator = new GUIISATABValidator();
                GUIInvokerResult result = validator.validate(isatabParentDir);      
                
                Set<String> errorMessages = getValidatorReport(validator.getLog());
                assertEquals("SUCCESS", result.name());
                assertEquals(0, errorMessages.size());   
		    }
		 

		 protected Set<String> getValidatorReport(List<TabLoggingEventWrapper> logResult) {
			 
			 Set<String> messages = new HashSet<String>();

		        for (TabLoggingEventWrapper tlew : logResult) {

		            Level eventLevel = tlew.getLogEvent().getLevel();

		            if (eventLevel.equals(Level.ERROR)) {
		                messages.add(tlew.getFormattedMessage());
		            }
		        }

		        return messages;
		    }

        
        
	
	
}
