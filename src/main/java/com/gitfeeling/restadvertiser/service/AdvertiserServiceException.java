package com.gitfeeling.restadvertiser.service;

public class AdvertiserServiceException extends Exception {
	
	private Status status;

	public AdvertiserServiceException(Status status) {
		this.status = status;
	}
	
	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	public enum Status {
		
		ADVERTISER_NOT_FOUND(404, "Advertiser not found"),
		
		ADVERTISER_ALREADY_EXISTS(409, "Advertiser already exists"),
		
		ADVERTISER_MISSING_NAME(422, "Advertiser does not contain mandatory property - name");
			
        private final int code;

        private final String description;

        Status(final int code, final String description) {
            this.code = code;
            this.description = description;
        }

		public int code() {
			return code;
		}

		public String description() {
			return description;
		}
	
	}	

}
