package com.gcu.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gcu.business.SecurityBusinessService;
import com.gcu.business.UserBusinessService;
import com.gcu.business.UtensilBusinessService;


/*
 * Date:10/15/2021
 * Spring configuration file for the spring boot application. Specifies which service to user for login and register.
 * @author Michael M.
 * @version 2.
 */
@Configuration
public class SpringConfig {

	
	//Setup our Business Server Layer for the Security Service
	@Bean(name="SecurityBusinessService")
	public SecurityBusinessService getSecurityBusiness()
	{
		return new SecurityBusinessService();
	}
	
	//Setup our Business Server Layer for the User Service
	@Bean(name="UserBusinessService")
	public UserBusinessService getUserBusiness()
	{
		return new UserBusinessService();
	}
	
	//Setup our Business Server Layer for the utensil Service
	@Bean(name="UtensilBusinessService")
	public UtensilBusinessService getUtensilBusiness()
	{
		return new UtensilBusinessService();
	}
	
	@Bean
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}
}
