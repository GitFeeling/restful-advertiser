package com.gitfeeling.restadvertiser.model;

public class ErrorResponse {

	private final int status;
	private final String description;
	
	public ErrorResponse(int status, String description) {
		this.status = status;
		this.description = description;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

}
