package com.gcu.model;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Date:10/01/2021
 * Model to handle data for logging in. Each variable is private and getters and setters are used for each one.
 * Validation is on all variables but id, as that is there for a future database. 
 * 
 * @author Michael M.
 * @version 2.
 *
 */
public class LoginModel implements UserDetails
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Instance Variables
	private int id;
	
	@NotNull(message="User name is a required field")
	@Size(min=1, max=32, message="User name must be between 1 and 32 characters")
	private String username;
	
	@NotNull(message="Password is a required field")
	private String password;
	
	private List<GrantedAuthority> authorities;

	
	public LoginModel(int id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
	
	//Constructor to handle the security
	public LoginModel(
			@NotNull(message = "User name is a required field") @Size(min = 1, max = 32, message = "User name must be between 1 and 32 characters") String username,
			@NotNull(message = "Password is a required field") String password,
			List<GrantedAuthority> authorities) {
		super();
		this.username = username;
		this.password = password;
		this.authorities = authorities;
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


	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}


	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}


	//For User Details
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	
	
}
