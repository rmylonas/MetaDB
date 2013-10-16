package org.isatools.errorreporter.model;

import org.apache.log4j.spi.LoggingEvent;

/**
 * Created by the ISA team
 *
 * @author Eamonn Maguire (eamonnmag@gmail.com)
 *         <p/>
 *         Date: 18/04/2012
 *         Time: 10:10
 */
public class ErrorUtils {

    public static String extractFileInformation(LoggingEvent loggingEvent) {

        String ndc = loggingEvent.getNDC();

        if (ndc != null && ndc.lastIndexOf("file:")!=-1) {
            System.out.println(ndc);
            ndc = ndc.substring(ndc.lastIndexOf("file:")).replaceAll("file:|]|\\[sect:ASSAYS", "").trim();
            return ndc;
        }
        return null;

    }

}
