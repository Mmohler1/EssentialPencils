package com.gcu.business;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gcu.model.LoginModel;


/**
 * Date: 10/12/21
 * Interface for the Login service pages
 * 
 * @author Michael M.
 * @version 1
 */
public interface SecurityBusinessServiceInterface {

	public int pullUserId(LoginModel loginModel);
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}