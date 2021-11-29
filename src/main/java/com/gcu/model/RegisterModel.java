package com.gcu.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Date: 10/27/2021
 * Model to handle data for registering and possible User data. Each variable is private and getters and setters are used for each one.
 * Validation is on all variables but will be changed for Phone and Email in the future;
 * Extends Login model so it can also use id, username, and password.
 * 
 * @author Michael M.
 * @version 2.
 *
 */
public class RegisterModel
{

	@NotNull(message="First name is a required field")
	@Size(min=2, max=12, message="A first name can only be between 2 and 15 characters")
	private String firstName;
	
	@NotNull(message="Last name is a required field")
	@Size(min=2, max=12, message="A last name can only be between 2 and 15 characters")
	private String lastName;
	
	@NotNull(message="Email is a required field")
	@Size(min=7, max=50, message="An email can only be between 7 and 50 characters")
	private String email;
	
	@NotNull(message="Phone Number is a required field")
	@Pattern(regexp="^[0-9]{10}",message="A phone number only have numbers and are 10 digits long") 
	private String phone;
	
	@Valid
	private LoginModel loginCred = new LoginModel();
	
	
	//Constructor for the main values. Used to handle user data
	public RegisterModel(
			@NotNull(message = "First name is a required field") @Size(min = 2, max = 12, message = "A first name can only be between 2 and 15 characters") String firstName,
			@NotNull(message = "Last name is a required field") @Size(min = 2, max = 12, message = "A last name can only be between 2 and 15 characters") String lastName,
			@NotNull(message = "Email is a required field") @Size(min = 7, max = 50, message = "An email can only be between 7 and 50 characters") String email,
			@NotNull(message = "Phone Number is a required field") @Pattern(regexp = "^[0-9]{10}", message = "A phone number only have numbers and are 10 digits long") String phone,
			@Valid LoginModel loginCred) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		
		this.loginCred = loginCred;
	}
	
	//Default constructor
	public RegisterModel()
	{
		this.firstName = "First Name";
		this.lastName = "Last Name";
		this.email = "Something@Some.com";
		this.phone = "1111111111";
		
		//Login Credentials being changed
		loginCred.setId(0);
		
		
		loginCred.setUsername("Username");
		loginCred.setPassword("P@S5w0Rd");
		
	}

	//Getters and Setters for all the private variables. 
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LoginModel getLoginCred() {
		return loginCred;
	}

	public void setLoginCred(LoginModel loginCred) {
		this.loginCred = loginCred;
	}

	
	//Getters and Setters for individual loginCred fields
	
	public int getId() {
		return loginCred.getId();
	}
	public void setId(int id) {
		loginCred.setId(id);
	}
	public String getUsername() {
		return loginCred.getUsername();
	}
	public void setUsername(String username) {
		loginCred.setUsername(username);
	}
	public String getPassword() {
		return loginCred.getPassword();
	}
	public void setPassword(String password) {
		
		loginCred.setPassword(password);
	}



	
	
}