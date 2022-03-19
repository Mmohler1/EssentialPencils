package com.gcu.controller;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.business.UtensilBusinessInterface;
import com.gcu.model.UtensilModel;

import org.springframework.ui.Model;


/**
 * Date: 10/14/2021
 * Controller for the Home page. Will be the page users are taken to after logging in.
 * From here the user will be able to see the most recent utensils added to their database
 * and can also go to most of the pages seen on the navbar. Such as the product page, search, and random. 
 * 
 * @author Michael M.
 * @version 2
 *
 */
@Controller
@RequestMapping("/home")
public class HomeController 
{
	//For the logger
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

		
	//The list of utensils that will be displayed to the user.
	private List<UtensilModel> utensils = new ArrayList<UtensilModel>();
	
	
	@Autowired
	private UtensilBusinessInterface service;
	
	/**
	 * Displays the main Home page to the user
	 * 
	 * @param model Used to add attributes to the model page.
	 * @param session Used to get the ID of the user to display utensil names
	 * 
	 * @return String of the page name
	 */
	@GetMapping("/")
	public String display(Model model, HttpSession session)
	{

			int id = (int)session.getAttribute("id");
			
			logger.info("User id is " + id);
			
			utensils = service.displayRecentUtensils(id);
			
			
			
			//Display Home Page View
			model.addAttribute("title", "Home");
			model.addAttribute("utensils", utensils);
			
			return "home";


	}
	
	

	/**
	 * Takes user to the case page
	 * 
	 * @param model Unused
	 * 
	 * @return String of the page name
	 */
	@GetMapping("/doCase")
	public String doCase(Model model)
	{
			
			//Display Title
			model.addAttribute("title", "Pencil Case");
			
			//Go to Case page. Uses forward so the utensils list on the case page is used.
			return "forward:/case/";	

	}
	
	
	/**
	 * Take user to the Search page for searching a utensil
	 * 
	 * @param model Used to add attributes to the model page.
	 * 
	 * @return String of the page name
	 */
	@GetMapping("/goSearch")
	public String goSearch(Model model)
	{		
		//Clear list to be used for search function
		List<UtensilModel> utensils = new ArrayList<UtensilModel>();
		
		//Display Search Form View
		model.addAttribute("title", "Search Utensil");
		model.addAttribute("utensils", utensils);
		model.addAttribute("contactModel", new UtensilModel());
		
		return "search";
	}
	
	
	/**
	 * Takes user to the random page by forwarding the get
	 * 
	 * 
	 * @return String of the page name
	 */
	@GetMapping("/doRandom")
	public String doRandom()
	{

		//Go to random page
		return "forward:/random/";

	}
}
