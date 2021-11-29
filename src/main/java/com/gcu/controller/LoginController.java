package com.gcu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import com.gcu.business.SecurityBusinessService;
import com.gcu.business.UtensilBusinessInterface;
import com.gcu.model.LoginModel;
import com.gcu.model.UtensilModel;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

/**
 * Date: 11/12/21
 * Controller which handles the login page, security, and setting up the user's sessions.
 * 
 * 
 * @author Michael M.
 * @version 4.
 *
 */
@Controller
public class LoginController 
{
	//Initialize the Business service for the Login Module
	@Autowired
	private SecurityBusinessService service;
	
	//Needed to setup the home page with a utensil model
	private List<UtensilModel> utensils = new ArrayList<UtensilModel>();
	@Autowired
	private UtensilBusinessInterface utensilService;
	
	
	/**
	 * Displays the main login page to the user
	 * 
	 * @param model Used to add attributes to the model page.
	 * 
	 * @return String of the page name
	 */
	@GetMapping("/login")
	public String display(Model model)
	{

			//Display Login Form View
			model.addAttribute("title", "Login Form");
			return "login";

	}
	
	
	/**
	 * Post method that checks for validation. If data is valid print to console 
	 * and take user to home page. If not, take the user back to login page.
	 * 
	 * @param loginModel Object model of the login credentials
	 * @param bindingResult Displays errors to the user
	 * @param model Used to add attributes to the model's page
	 * @param session Controls session data throughout the site
	 * 
	 * @return String of the page name the use will be taken to
	 */
	@PostMapping("/doLogin")
	public String doLogin(@Valid LoginModel loginModel, BindingResult bindingResult, Model model, HttpSession session)
	{
		//Attempts to login to the website. If there are  errors or credentials issues stay on login page.
		//Otherwise, go to the home page.

			//Check for Validation errors.
			if(bindingResult.hasErrors())
			{
				//Since Errors were found take the user back to the login page.
				model.addAttribute("title", "Login Form");
				model.addAttribute("loginModel", loginModel);
				return "login";
			}
			
			String errorMessage =""; //Initialize error message
			
			//Call service once and save number to determine the action
			int loginNumber = service.authenticateLogin(loginModel); 
			
			//The user's login information is being taken to the login business layer to check for authentication.
			if(loginNumber == 0)
			{
				//User has successfully logged in
				
				//Used for error handling, not implemented yet
				int id = service.pullUserId(loginModel);
				
				//Create Sessions with Username and ID for use around the website
				session.setAttribute("username", loginModel.getUsername());
				session.setAttribute("id", id); 
				
				
				//Finds list of utensil for home page to use.
				utensils = utensilService.displayRecentUtensils(id);
				
				//Take the user to the home page with the utensil list added.
				model.addAttribute("title", "Pencil Case");
				model.addAttribute("utensils", utensils);
				return "home";
				
			}
			else if(loginNumber == 1)
			{
				errorMessage = "Username or Password incorrect. Try Again."; //Error Message
			}

			
			
			//Stay on login page and display error
			model.addAttribute("title", "Login Form");
			model.addAttribute("errorMessage", errorMessage);
			return "login";
		
			

 
	}
}
