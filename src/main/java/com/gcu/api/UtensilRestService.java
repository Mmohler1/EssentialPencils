package com.gcu.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.gcu.business.UtensilBusinessInterface;
import com.gcu.model.UtensilModel;


/**
 * Date: 12/06/21
 * REST API for the utensil database. Will return either a single utensil or every utensil as a JSON.
 * 
 * @author Michael M.
 * @version 1
 */
@RestController
@RequestMapping("/service")
public class UtensilRestService 
{
	//Initialize the utensil service to return values.
	@Autowired
	private UtensilBusinessInterface service;
	
	
	/**
	 * API to retrieve every utensil in the database. If found will return it as a JSON.
	 * 
	 * @return response entity with a status and possibly a list.
	 */
	@GetMapping(path="/getUtensil")
	public ResponseEntity<?> getUtensils()
	{
		//Try to return every utensil in the database.
		try
		{
			//Call Utensil service and save it as a list
			List<UtensilModel> utensils = service.getEveryUtensil();
			
			//If list is null then return not found status.
			if(utensils == null)
			{	
				
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			else
			{
				//Return list and ok status if found.
				return new ResponseEntity<>(utensils, HttpStatus.OK);
			}
				
		}
		catch (Exception e)
		{
			//Return server error status if exception happen. 
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * API to call a utensil in the database that matches the utensil id in the path variables. 
	 * 
	 * @param id matches the number utensil id in the database
	 * @return response entity with a http status and maybe a single utensil
	 */
	@GetMapping(path="/getUtensil/{id}")
	public ResponseEntity<?> getUtensil(@PathVariable("id") int id)
	{
		//Try to return every utensil in the database.
		try
		{
			//Call Utensil service and save it as a utensil
			UtensilModel utensil = service.getUtensilById(id);
			
			//If utensil in null then return not found status, if found then return ok with utensil
			if(utensil == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			else
				return new ResponseEntity<>(utensil, HttpStatus.OK);
		}
		catch(Exception e)
		{
			//Return server error status if exception happen. 
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
