package com.gcu.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Date:10/01/2021
 * Model to handle data for logging in. Each variable is private and getters and setters are used for each one.
 * Validation is on all variables but id, as that is there for a future database. 
 * 
 * @author Michael M.
 * @version 2.
 *
 */
public class LoginModel
{


	//Instance Variables
	private int id;
	
	@NotNull(message="User name is a required field")
	@Size(min=2, max=32, message="User name must be between 1 and 32 characters")
	private String username;
	
	@NotNull(message="Password is a required field")
	@Size(min=5, max=32, message="Password must be between 5 and 32 characters")
	private String password;

	


	public LoginModel(int id,
			@NotNull(message = "User name is a required field") @Size(min = 1, max = 32, message = "User name must be between 1 and 32 characters") String username,
			@NotNull(message = "Password is a required field") String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	//Default constructor
	public LoginModel()
	{
		id = 0;
		username = "Username";
		password = "P@S5w0Rd";
		
	}
	 
	//Getters and Setters
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
