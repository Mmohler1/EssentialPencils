package com.gcu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.gcu.business.UtensilBusinessInterface;
import com.gcu.model.UtensilModel;

import org.springframework.ui.Model;


/**
 * Date: 11/26/21
 * Controller which handles the random pick feature
 * 
 * 
 * @author Michael M.
 * @version 1.
 *
 */
@Controller
@RequestMapping("/random")
public class RandomController 
{
	//For the logger
	private static final Logger logger = LoggerFactory.getLogger(RandomController.class);


	//Full list of utensils that will be picked from at random
	private List<UtensilModel> utensils = new ArrayList<UtensilModel>();
	
	//List of utensils that was picked from random
	private List<UtensilModel> randomUtensils = new ArrayList<UtensilModel>();
	
	@Autowired
	private UtensilBusinessInterface service;
	
	
	/**
	 * Displays the main random page to the user
	 * 
	 * @param model Used to add attributes to the model page.
	 * 
	 * @return String of the page name
	 */
	@GetMapping("/")
	public String display(Model model)
	{
		utensils = new ArrayList<UtensilModel>();
		
		//Loads utensil list into the pages view. 
		model.addAttribute("title", "Random Page");
		model.addAttribute("utensils", utensils);
		model.addAttribute("utensilModel", new UtensilModel());  
		
		return "random";

	}
	
	
	/**
	 * Post method that checks for validation. If data is valid print to console 
	 * and take user to home page. If not, take the user back to login page.
	 * 
	 * @param utensilModel Object model, will be used to carry the random number to the controller
	 * @param model Used to add attributes to the model's page
	 * @param session Controls session data throughout the site
	 * 
	 * @return String of the page name the use will be taken to
	 */
	@PostMapping("/doRandom")
	public String doRandom(@Valid UtensilModel utensilModel, Model model, HttpSession session)
	{
		
		//Save user's id
		int id = (int)session.getAttribute("id");

		logger.info("User id is " + id);
		
		//Pulls quantity from utensil which represents the amount of random picks
		int amount = utensilModel.getQuantity();
		
		logger.info("Amount is " + amount);
		
		if(amount <= 0)
		{
			String errorMessage = "Please pick a number more then 0.";
			utensils = new ArrayList<UtensilModel>();
			
			//Loads utensil list into the pages view. 
			model.addAttribute("title", "Random Page");
			model.addAttribute("utensils", utensils);
			model.addAttribute("utensilModel", new UtensilModel());
			model.addAttribute("errorMessage", errorMessage);
			
			logger.warn("Number was less then 1");
			
			return "random";
		}
		
		//Add every utensil from user to list
		utensils = service.displayAllUtensils(id);
		
		randomUtensils = service.getRandomizedUtensil(utensils, amount);
		
		//Loads utensil list into the pages view. 
		model.addAttribute("title", "Random Page");
		model.addAttribute("utensils", randomUtensils);
		model.addAttribute("utensilModel", new UtensilModel());
		
		logger.info("Randomized List Returned");
		return "random";
 
	}
}
