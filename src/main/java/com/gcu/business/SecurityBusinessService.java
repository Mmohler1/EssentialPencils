 package com.gcu.business;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gcu.data.UserDataAccessInterface;
import com.gcu.model.LoginModel;
import com.gcu.model.RegisterModel;


/**
 * Date: 10/12/21
 * Business Layer Service for the Login portion of the website.
 * This used communicate between the Login controller and the DAO.
 * 
 * @author Michael M.
 * @version 2
 */
public class SecurityBusinessService implements SecurityBusinessServiceInterface,  UserDetailsService {

	@Autowired
	private UserDataAccessInterface<RegisterModel> service;
	
	//Needed to set the session varaibles
	@Autowired 
	 private HttpSession session;
	

	/**
	 * Get the user's id by calling the DAO
	 * 
	 * 
	 * @param loginModel Uses Username to get Id
	 * 
	 * @return int Used to determine what to do
	 */
	@Override
	public int pullUserId(LoginModel loginModel) {
		
		//Convert login model to register model to work with the Data Access Generic type
		RegisterModel registerModel = new RegisterModel();
		registerModel.setUsername(loginModel.getUsername());
		registerModel.setPassword(loginModel.getPassword());
		

		//Return User ID if username is found
		return service.findId(registerModel);

	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		RegisterModel user = service.findByUsername(username);
		
		if(user != null)
		{
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("USER"));
			
			session.setAttribute("username", user.getUsername());
			session.setAttribute("id", user.getId());
			
			return new LoginModel(user.getUsername(), user.getPassword(), authorities);
		}
		else
		{
			
			throw new UsernameNotFoundException("Username not found");
		}
	}


	@Override
	public int authenticateLogin(LoginModel loginModel) {
		// TODO Auto-generated method stub
		return 0;
	}
	


}
