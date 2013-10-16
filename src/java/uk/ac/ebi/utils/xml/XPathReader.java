/*
 * __________
 * CREDITS
 * __________
 *
 * Team page: http://isatab.sf.net/
 * - Marco Brandizi (software engineer: ISAvalidator, ISAconverter, BII data management utility, BII model)
 * - Eamonn Maguire (software engineer: ISAcreator, ISAcreator configurator, ISAvalidator, ISAconverter,  BII data management utility, BII web)
 * - Nataliya Sklyar (software engineer: BII web application, BII model,  BII data management utility)
 * - Philippe Rocca-Serra (technical coordinator: user requirements and standards compliance for ISA software, ISA-tab format specification, BII model, ISAcreator wizard, ontology)
 * - Susanna-Assunta Sansone (coordinator: ISA infrastructure design, standards compliance, ISA-tab format specification, BII model, funds raising)
 *
 * Contributors:
 * - Manon Delahaye (ISA team trainee:  BII web services)
 * - Richard Evans (ISA team trainee: rISAtab)
 *
 *
 * ______________________
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
 *
 * __________
 * License:
 * __________
 *
 * This work is licenced under the Creative Commons Attribution-Share Alike 2.0 UK: England & Wales License. To view a copy of this licence, visit http://creativecommons.org/licenses/by-sa/2.0/uk/ or send a letter to Creative Commons, 171 Second Street, Suite 300, San Francisco, California 94105, USA.
 *
 * __________
 * Sponsors
 * __________
 * This work has been funded mainly by the EU Carcinogenomics (http://www.carcinogenomics.eu) [PL 037712] and in part by the
 * EU NuGO [NoE 503630](http://www.nugo.org/everyone) projects and in part by EMBL-EBI.
 */
package uk.ac.ebi.utils.xml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by the ISA team Modified from example here:
 * http://www.javabeat.net/tips/182-how-to-query-xml-using-xpath.html
 * 
 * @author Eamonn Maguire (eamonnmag@gmail.com)
 */
public class XPathReader
{

	private Document xmlDocument;
	private XPath xPath;

	public XPathReader ( InputStream xmlStream )
	{
		Exception xmlEx = null;
		try
		{
			xmlDocument = DocumentBuilderFactory.newInstance ().newDocumentBuilder ().parse ( xmlStream );
			xPath = XPathFactory.newInstance ().newXPath ();
		} 
		catch ( SAXException ex ) {
			xmlEx = ex;
		} 
		catch ( IOException ex ){
			xmlEx = ex;
		} 
		catch ( ParserConfigurationException ex ) {
			xmlEx = ex;
		} 
		finally {
			// TODO: Better exception handling
			if ( xmlEx != null )
				throw new RuntimeException ( "Internal error with the XPath Reader: " + xmlEx.getMessage (), xmlEx );
		}
	}

	public Object read ( String expression, QName returnType )
	{
		try
		{
			XPathExpression xPathExpression = xPath.compile ( expression );
			return xPathExpression.evaluate ( xmlDocument, returnType );
		} catch ( XPathExpressionException ex )
		{
			// TODO: Better exception handling
			throw new RuntimeException ( "Internal error with the XPath Reader: " + ex.getMessage (), ex );
		}
	}
}
