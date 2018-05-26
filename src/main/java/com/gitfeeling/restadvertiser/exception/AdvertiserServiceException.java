package com.gitfeeling.restadvertiser.exception;

public class AdvertiserServiceException extends Exception {
	
	private final int status;

	public AdvertiserServiceException(int status, String message) {
		super(message);
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

}
