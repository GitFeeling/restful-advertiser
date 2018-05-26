package com.gitfeeling.restadvertiser.model;

public class Advertiser {

	private String name;
	
	private String contactName;
	
	private int creditLimit;

	public Advertiser() { }

	public Advertiser(String name, String contactName, int creditLimit) {
		super();
		this.name = name;
		this.contactName = contactName;
		this.creditLimit = creditLimit;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return the creditLimit
	 */
	public int getCreditLimit() {
		return creditLimit;
	}

	/**
	 * @param creditLimit the creditLimit to set
	 */
	public void setCreditLimit(int creditLimit) {
		this.creditLimit = creditLimit;
	}
	
}
