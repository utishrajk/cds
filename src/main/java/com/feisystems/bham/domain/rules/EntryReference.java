package com.feisystems.bham.domain.rules;

public class EntryReference {
	
	private String entry;
	
	private String reference;	

	public EntryReference() {
		super();
	}

	public EntryReference(String entry, String reference) {
		super();
		this.entry = entry;
		this.reference = reference;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
}
