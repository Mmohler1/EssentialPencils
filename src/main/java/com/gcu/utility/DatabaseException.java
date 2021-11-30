package com.gcu.utility;

/**
 * Date: 11/14/21
 * 
 * Custom Exception to handle database errors. 
 * 
 * @author Michael M.
 *
 */
public class DatabaseException extends RuntimeException 
{

	//Initialize variables
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	
	
	/**
	 * Default Constructor 
	 */
	public DatabaseException() 
	{
		
	}


	/**
	 * Parameterized Constructor 
	 */
	public DatabaseException(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	
	//Getters and setters
	
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
