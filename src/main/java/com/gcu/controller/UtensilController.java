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
import com.gcu.utility.DatabaseException;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

/**
 * Date: 10/14/21
 * Controller for the case page, for displaying utensils. Will call a list of utensils from the database to display on the page.
 * Users can also add to the utensil database
 * 
 * In the future will have actions that allow for editing, deleting, getting details on each product.
 * 
 * @author Michael M.
 * @since 10/1/2021
 * @version 3.
 *
 */
@Controller
@RequestMapping("/case")
public class UtensilController 
{
	//For the logger
	private static final Logger logger = LoggerFactory.getLogger(UtensilController.class);

	
	//The list of utensils that will be displayed to the user.
	private List<UtensilModel> utensils = new ArrayList<UtensilModel>();
	@Autowired
	private UtensilBusinessInterface service;
	
	
	/**
	 * Displays the main case page to the user
	 * 
	 * @param model Used to add attributes to the model page.
	 * @param session Controls session data throughout the site
	 * 
	 * @return String of the page name
	 */
	@GetMapping("/")
	public String display(Model model, HttpSession session)
	{


			int id = (int)session.getAttribute("id");
			logger.info("User id is: " + id );
			
			utensils = service.displayAllUtensils(id);
			//Loads utensil list into the pages view. 
			model.addAttribute("title", "Pencil Case");
			model.addAttribute("utensils", utensils);
			model.addAttribute("utensilModel", new UtensilModel());
			
			return "case";
	}
	
	
	
	/**
	 * Take user to the Add page for adding a product
	 * 
	 * @param model Used to add attributes to the model page.
	 * 
	 * @return String of the page name
	 */
	@GetMapping("/goAdd")
	public String goAdd(Model model)
	{
		
			//Display Add Form View
			model.addAttribute("title", "Add Utensil");
			model.addAttribute("utensilModel", new UtensilModel());
			
			return "add";

	}
	
	/**
	 * Post method to go to update page with the same details as utensil that was clicked
	 * 
	 * @param utensilModel Object to forward details to update page
	 * @param model for page attributes
	 * @param binding Results for error page
	 * 
	 * @return Update page name string
	 */
	@PostMapping("/goUpdate")
	public String goUpdate(UtensilModel utensilModel, Model model, BindingResult bindingResult)
	{
		
			//Display Update Form View
			model.addAttribute("title", "Update Utensil");
			model.addAttribute("utensilModel", utensilModel);
			
			return "update";
	}
	
	
	/**
	 * Take user to the Detailed page for showing all details of a utensil
	 * 
	 * @param model Used to add attributes to the model page.
	 * 
	 * @return String of the page name
	 */
	@PostMapping("/goDetail")
	public String goDetail(UtensilModel utensilModel, Model model)
	{
		
			//Display Add Form View
			model.addAttribute("title", "Detailed Utensil");
			model.addAttribute("utensilModel", utensilModel);
			
			return "detail";

	}
	
	
	/**
	 * Post method that checks for validation. If data is valid take user 
	 * to the case page. If not, take the user back to add page.
	 * 
	 * @param utensilModel from form
	 * @param bindingResults for error page
	 * @param model for page attributes
	 * @param session data to use for users id.
	 * 
	 * @return Update page name string
	 */
	@PostMapping("/doAdd")
	public String doAdd(@Valid UtensilModel utensilModel, BindingResult bindingResult, Model model, HttpSession session)
	{
		UtensilModel newUtensil = utensilModel;
		int id = (int)session.getAttribute("id");
		newUtensil.setUserId(id);
		
		//If no validation errors are found add to list and go to case page.
		//Check for Validation errors.
		if(bindingResult.hasErrors())
		{
			logger.warn("Validation Errors Found");
			
			//If Errors were found take the user back to the add page.
			model.addAttribute("title", "Add Utensil");
			return "add";
		}
		else
		{
			logger.info("No Validation Errors Found");
			
			//If there were no errors then try to add utensil to the product list
			
			String errorMessage; //Initialize error message
			//Call service once and save number to determine the action
			int utensilNumber = service.insertUtensil(newUtensil);
		
			logger.info("Utensil Number is " + utensilNumber);
			
			//Based on number, either take user to case page or display error.
			if (utensilNumber == 0)
			{
				logger.info("Utensil Was Created");
				
				//Refresh the utensil list
				utensils = service.displayAllUtensils(id); 
				
				//Take the user back to the case page.
				model.addAttribute("title", "Pencil Case");
				model.addAttribute("utensils", utensils);
				return "case";
			}
			else if (utensilNumber == 1)
			{
				errorMessage = "Cannot Access Database"; //Error Message

			}
			else if (utensilNumber == 2)
			{
				errorMessage = "Erorr in Server"; //Error Message

			}
			else
			{
				errorMessage = "Error in Application"; //Error Message
				
			}
			
			//If Errors were found take the user back to the add page.
			model.addAttribute("title", "Add Utencil");
			model.addAttribute("errorMessage", errorMessage);
			
			logger.warn("Error Found: " + errorMessage );
			
			return "add";

		}

	}
	
	/**
	 * Post method that checks for validation. If data is valid take user 
	 * to the case page. If not, take the user back to update page.
	 * 
	 * @param utensilModel from form
	 * @param bindingResults for error page
	 * @param model for page attributes
	 * @param session data to use for users id.
	 * 
	 * @return Update page name string
	 */
	@PostMapping("/doUpdate")
	public String doUpdate(@Valid UtensilModel utensilModel, BindingResult bindingResult, Model model, HttpSession session)
	{
		UtensilModel newUtensil = utensilModel;
		int id = (int)session.getAttribute("id");
		newUtensil.setUserId(id);

		logger.info("Utensil Id is: " + utensilModel.getUtensilId());
		
		//If no validation errors are found add to list and go to case page.
		//Check for Validation errors.
		if(bindingResult.hasErrors())
		{
			logger.warn("Validation Errors Found");
			
			//If Errors were found take the user back to the add page.
			model.addAttribute("title", "Update Utencil");
			return "update";
		}
		else
		{
			logger.info("No Validation Errors Found");
			
			//If there were no errors then try to add utensil to the product list
			
			String errorMessage; //Initialize error message
			//Call service once and save number to determine the action
			int utensilNumber = service.changeUtensil(newUtensil);
		
			logger.info("Utensil Number is " + utensilNumber);
			
			//Based on number, either take user to case page or display error.
			if (utensilNumber == 0)
			{
				logger.info("Utensil Was Updated");
				
				//Refresh the utensil list
				utensils = service.displayAllUtensils(id); 
				
				//Take the user back to the case page.
				model.addAttribute("title", "Pencil Case");
				model.addAttribute("utensils", utensils);
				return "case";
			}
			else if (utensilNumber == 1)
			{
				errorMessage = "Cannot Access Database"; //Error Message

			}
			else if (utensilNumber == 2)
			{
				errorMessage = "Erorr in Server"; //Error Message

			}
			else
			{
				errorMessage = "Error in Application"; //Error Message
				
			}
			
			//If Errors were found take the user back to the add page.
			model.addAttribute("title", "Update Utencil");
			model.addAttribute("errorMessage", errorMessage);
			
			logger.warn("Error Found: " + errorMessage );
			
			return "update";

		}



	}
	
	/**
	 * Post method that checks for validation. If data is valid take user 
	 * to the case page. If not, take the user back to update page.
	 * 
	 * @param utensilModel from form
	 * @param bindingResults for error page
	 * @param model for page attributes
	 * @param session data to use for users id.
	 * 
	 * @return Update page name string
	 */
	@PostMapping("/doDelete")
	public String doDelete(UtensilModel utensilModel, Model model, HttpSession session)
	{
		UtensilModel newUtensil = utensilModel;
		int id = (int)session.getAttribute("id");
		newUtensil.setUserId(id);
		
		logger.info("Utensil Id is: " + utensilModel.getUtensilId());

		//Call service once and save number to determine the action
		int utensilNumber = service.eraseUtensil(newUtensil);
		
		//Based on number, refresh case page
		if (utensilNumber == 0)
		{
			logger.info("Utensil Deleted");
			
			//Refresh the utensil list
			utensils = service.displayAllUtensils(id); 
			
		}
		else
		{
			logger.error("Database Error");
			throw new DatabaseException("Database is currently down. Could not delete utensil.");
		}
		
		//Take the user back to the case page.
		model.addAttribute("title", "Pencil Case");
		model.addAttribute("utensils", utensils);
		return "case";



	}
}
