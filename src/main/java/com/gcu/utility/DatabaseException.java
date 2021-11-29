package com.gcu.utility;

public class DatabaseException extends RuntimeException 
{

	/**
	 * Needed to extend RuntimeExcpetion
	 */
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	
	
	public DatabaseException() 
	{
		
	}


	public DatabaseException(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	
	
	
}
