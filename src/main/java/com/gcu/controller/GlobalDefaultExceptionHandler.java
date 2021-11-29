package com.gcu.controller;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.gcu.utility.DatabaseException;

/**
 * Date: 11/19/2021
 * Global Exception handler that will handler any exception or DatabaseException 
 * that gets sent to or happens in the controllers.
 * 
 * 
 * @author Michael Mohler.
 * @since 11/17/2021
 * @version 1.
 *
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler 
{
	
	/**
	 * Handler for DatabaseExceptions. Will take user to error page.
	 * 
	 * @param ex Custom exception class that stores details from thrown
	 * exceptions. 
	 * 
	 * @return ModelAndView determines model attributes of the next page
	 */
	@ExceptionHandler(DatabaseException.class)
	public ModelAndView handleDatabaseException(DatabaseException ex)
	{
		
		//Create ModelAndView
		ModelAndView model = new ModelAndView();
		
		//Add the Title, error message, and determine what page to go to. 
		model.addObject("title", "Database Error");
		model.addObject("errorMessage", "Error: " + ex.getErrorMessage());
		model.setViewName("error");
		
		
		return model;
	}
	
	/**
	 * Handler for any exception. Will take user to error page with default message.
	 * 
	 * @return ModelAndView determines model attributes of the next page
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException()
	{
		//Create ModelAndView
		ModelAndView model = new ModelAndView();
		
		
		//Add the Title, error message, and determine what page to go to. 
		model.addObject("title", "Unknown Error");
		model.addObject("errorMessage", "Error: An unidentified error has occurred.");
		model.setViewName("error");
		
		return model;
	}
}
