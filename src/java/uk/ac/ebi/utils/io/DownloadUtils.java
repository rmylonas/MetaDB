/*
 * __________
 *  CREDITS
 * __________
 *
 * Team page: http://isatab.sf.net/
 *  - Marco Brandizi (software engineer: ISAvalidator, ISAconverter, BII data management utility, BII model)
 *  - Eamonn Maguire (software engineer: ISAcreator, ISAcreator configurator, ISAvalidator, ISAconverter,  BII data management utility, BII web)
 *  - Nataliya Sklyar (software engineer: BII web application, BII model,  BII data management utility)
 *  - Philippe Rocca-Serra (technical coordinator: user requirements and standards compliance for ISA software, ISA-tab format specification, BII model, ISAcreator wizard, ontology)
 *  - Susanna-Assunta Sansone (coordinator: ISA infrastructure design, standards compliance, ISA-tab format specification, BII model, funds raising)
 *
 * Contributors:
 *  - Manon Delahaye (ISA team trainee:  BII web services)
 *  - Richard Evans (ISA team trainee: rISAtab)
 *
 *  ______________________
 * Contacts and Feedback:
 * ______________________
 *
 * Project overview: http://isatab.sourceforge.net/
 *
 * To follow general discussion: isatab-devel@list.sourceforge.net
 * To contact the developers: isatools@googlegroups.com
 *
 * To report bugs: http://sourceforge.net/tracker/?group_id=215183&atid=1032649
 * To request enhancements:  http://sourceforge.net/tracker/?group_id=215183&atid=1032652
 *
 * __________
 * License
 * __________
 *
 * This work is licenced under the Creative Commons Attribution-Share Alike 2.0 UK: England & Wales License. To view a copy of this licence, visit http://creativecommons.org/licenses/by-sa/2.0/uk/ or send a letter to Creative Commons, 171 Second Street, Suite 300, San Francisco, California 94105, USA.
 *
 * __________
 * Sponsors
 * __________
 * This work has been funded mainly by the EU Carcinogenomics (http://www.carcinogenomics.eu) [PL 037712] and in partby the EU NuGO [NoE 503630](http://www.nugo.org/everyone) projects and in part by EMBL-EBI.
 */
package uk.ac.ebi.utils.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * A class to make download of files easier.
 *
 */
public class DownloadUtils {

    private static Logger log = LoggerFactory.getLogger ( DownloadUtils.class );

    public static final String TMP_LOCATION = System.getProperty("java.io.tmpdir");

    // TODO: this default is not generic enough
    public static final String DOWNLOAD_FILE_LOC = TMP_LOCATION + File.separator + "ontologies_matching_";
    public static final String XML_EXT = ".xml";

    /**
     * Provided a URL containing a page/file to download and a download location, will download and create the file.
     * @param urlToDownload - a URL containing the file to download
     * @param downloadLocation -  a place in which to store the downloaded file.
     * @return boolean value to indicate success (true) or failure (false)
     */
    public static boolean downloadFile(String urlToDownload, String downloadLocation) {
        URL url;
        OutputStream os = null;
        InputStream is = null;

        try {

            url = new URL(urlToDownload);

            URLConnection urlConn = url.openConnection();
            urlConn.setReadTimeout(10000);
            urlConn.setUseCaches(true);

            is = urlConn.getInputStream();

            os = new BufferedOutputStream(new FileOutputStream(downloadLocation));

            byte[] inputBuffer = new byte[1024];
            int numBytesRead;

            while ((numBytesRead = is.read(inputBuffer)) != -1) {
                os.write(inputBuffer, 0, numBytesRead);
            }

            return true;
        } catch (MalformedURLException e) {
            log.error("url malformed: " + e.getMessage());
            return false;
        } catch (FileNotFoundException e) {
            log.error("file not found" + e.getMessage());
            return false;
        } catch (IOException e) {
            log.error("io exception caught" + e.getMessage());
            // we allow one retry attempt due to problems with BioPortal not always serving
            // back results on the first attempt!
            return false;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }

                if (is != null) {
                    is.close();
                }
            } catch (IOException ioe) {
                log.error("io exception caught: " + ioe.getMessage());

            }
        }
    }

    /**
     * Remove a file given an absolute file path
     * @param file - String representing the absolute location of the file to be removed.
     */
    public static void deleteFile(String file) {
        File f = new File(file);
        if (f.exists()) {
            f.delete();
        }
    }
}
