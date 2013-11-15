package it.fmach.metadb.isatab.instrument

enum Polarity {
	POSITIVE("positive", "pos"),
	NEGATIVE("negative", "neg"),
	ALTERNATING("alternating", "alt")
	
	private final String metabolightsName
	private final String tag
	
	Polarity(String metabolightsName, String tag) {
		this.metabolightsName = metabolightsName
		this.tag = tag
	}
	
	public String metabolightsName(){return metabolightsName}
	public String tag(){return tag}
	
	String toString(){ metabolightsName }
	String getKey() { name() }
}
