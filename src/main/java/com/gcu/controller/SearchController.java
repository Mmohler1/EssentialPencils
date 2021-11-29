package com.gcu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.business.UtensilBusinessInterface;
import com.gcu.model.UtensilModel;

import org.springframework.ui.Model;

/**
 * Date: 11/24/21
 * Controller for searching utensils in the database
 * 
 * Users can use actions that allow for editing, deleting, getting details on each product.
 * 
 * @author Michael M.
 * @since 10/1/2021
 * @version 3.
 *
 */
@Controller
@RequestMapping("/search")
public class SearchController 
{
	
	//The list of utensils that will be displayed to the user.
	private List<UtensilModel> utensils = new ArrayList<UtensilModel>();
	@Autowired
	private UtensilBusinessInterface service;
	
	
	/**
	 * Displays the main search page to the user
	 * 
	 * @param model Used to add attributes to the model page.
	 * @param session Controls session data throughout the site
	 * 
	 * @return String of the page name
	 */
	@GetMapping("/")
	public String display(Model model, HttpSession session)
	{
		//Clear list to be used for search function
		List<UtensilModel> utensils = new ArrayList<UtensilModel>();
		
		//Display Search Form View
		model.addAttribute("title", "Search Utensil");
		model.addAttribute("utensils", utensils);
		model.addAttribute("utensilModel", new UtensilModel());
		
		return "search";
	}
	
	/**
	 * Post method for searching a users utensil. Takes users
	 * back to same page with table filed out. 
	 * 
	 * @param utensilModel from form. Used to send information from the form to the Utensil DAO. Type is the search term
	 * @param bindingResults for error page
	 * @param model for page attributes
	 * @param session data to use for users id.
	 * 
	 * @return String that is the name of the HTML page.
	 */
	@PostMapping("/doSearch")
	public String doSearch(UtensilModel utensilModel, Model model, HttpSession session)
	{
		//New Utensil is made to add variables to more easily get from the 
		UtensilModel newUtensil = utensilModel;
		
		//Pull user ID from session and add to the utensil model
		int id = (int)session.getAttribute("id");
		newUtensil.setUserId(id);
		
		//Pull the searchTerm from the firstName variable
		String searchTerm = utensilModel.getType();		
		
		//If user enters nothing then prevent their entire utensil list from showing
		if(searchTerm == "")
		{
			//Add error message to tell user to enter something
			String errorMessage = "Please enter something in the search bar.";
			model.addAttribute("errorMessage", errorMessage);
		}
		else
		{
			//Call service and try to find searched item
			utensils = service.displaySearchedUtensil(newUtensil);
		}
		
		
	
		//Take the user back to the Utensil page with attributes.
		model.addAttribute("title", "Search Utensil");
		model.addAttribute("utensils", utensils);
		model.addAttribute("utensilModel", new UtensilModel());
		return "search";



	}
	
}
