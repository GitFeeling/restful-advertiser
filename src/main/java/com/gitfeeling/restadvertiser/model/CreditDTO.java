package com.gitfeeling.restadvertiser.model;

public class CreditDTO {
	
	private Operation operation;
	
	private int amount;
	
	public CreditDTO() { }
	
	public CreditDTO(Operation operation, int amount) {
		super();
		this.operation = operation;
		this.amount = amount;
	}

	/**
	 * @return the operation
	 */
	public Operation getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public enum Operation {
		
		CREDIT("CREDIT"),
		
		DEBIT("DEBIT"),
		
		CHECK("CHECK");

        private final String value;

        Operation(final String value) {
        		this.value = value;
        }
        
        public String value() {
        		return this.value;
        }
	
	}

}
