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
package uk.ac.ebi.utils.collections;

/**
 * An implementation of the famouse Pair interface, as it is found in some package and in discussions about wether it is
 * necessary or not to have it in java.util.
 *  
 * <dl>
 * <dt>date</dt>
 * <dd>Sep 19, 2011</dd>
 * </dl>
 * 
 * @author Eamonn Macguire
 * 
 * @param <V>
 *          the type for the first element in the pair
 * @param <T>
 *          the type for the second element in the pair
 * 
 */
public class Pair<V, T>
{

	public V fst;
	public T snd;

	public Pair ( V fst, T snd )
	{
		this.fst = fst;
		this.snd = snd;
	}

	public V getFst () {
		return fst;
	}

	public T getSnd () {
		return snd;
	}
}
