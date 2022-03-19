package com.gcu.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gcu.data.DataAccessInterface;
import com.gcu.data.UtensilDataAccessInterface;
import com.gcu.model.UtensilModel;

/**
 * Date: 11/03/21
 * Business Layer Service for the Utensil portion of the website.
 * Used to communicate between the Utensil controller and the DAO.
 * 
 * @author Michael M.
 * @version 2
 */
public class UtensilBusinessService implements UtensilBusinessInterface {

	//For the logger
	private static final Logger logger = LoggerFactory.getLogger(UtensilBusinessService.class);
		
	@Autowired
	private DataAccessInterface<UtensilModel> service;
	
	@Autowired
	private UtensilDataAccessInterface<UtensilModel> serviceUtensil;
	

	/**
	 * Create a utensil by calling DAO. Returns an int so a
	 * proper error message can be displayed.
	 * 
	 * @param utensilModel Used to add utensil
	 * 
	 * @return int Used to determine what to do
	 */
	@Override
	public int insertUtensil(UtensilModel utsenislModel) 
	{

		//Call Service and return a number based on the result
		int errorNumber = service.create(utsenislModel);
		logger.info("Error Number is " + errorNumber);
		return errorNumber;


	}

	
	/**
	 * Returns a list of all the utensil by calling the DAO service
	 * 
	 * @param id that determines what utensils are pulled.
	 * 
	 * @return List<UtensilModel> List of Utensils
	 */
	@Override
	public List<UtensilModel> displayAllUtensils(int id) 
	{

		//Return service
		logger.info("Utensil List Created");
		return service.findAll(id);

	}
	

	/**
	 * Change a utensil by calling the DAO. Returns an int so a
	 * proper error message can be displayed.
	 * 
	 * @param utensilModel Used to change utensil
	 * 
	 * @return int Used to determine what to do
	 */
	@Override
	public int changeUtensil(UtensilModel utsenislModel) 
	{

		//Call Service and return a number based on the result
		int errorNumber = service.update(utsenislModel);
		logger.info("Error Number is " + errorNumber);
		
		return errorNumber;


	}
	
	/**
	 * Delete the product by calling DAO service
	 * 
	 * @param utensilModel Used to find out which utensil to delete
	 * 
	 * @return int Used to determine what happened
	 */
	@Override
	public int eraseUtensil(UtensilModel utsenislModel) 
	{
	
		//Call Service and return a number based on the result
		int errorNumber = service.delete(utsenislModel);
		logger.info("Error Number is " + errorNumber);
		
		return errorNumber;
	}
	
	
	/**
	 * Search product by calling DAO service
	 * 
	 * @param utensilModel carries the search time and userid for the search
	 * 
	 * @return int Used to determine what happened
	 */
	@Override
	public List<UtensilModel> displaySearchedUtensil(UtensilModel utensilModel)
	{
	
		return serviceUtensil.findBySearchTerm(utensilModel);
		
	}
	
	/**
	 * Display the three most recent utensils
	 * 
	 * @param id of the user
	 * 
	 * @return List<UtensilModel> List of recent Utensils
	 */
	public List<UtensilModel> displayRecentUtensils(int id)
	{
		logger.info("Utensil List Created");
		return serviceUtensil.findRecentUtensils(id);
	}
	
	
	
	
	/**
	 * Display random utensil from the users list that gives them an idea of what utensil to use.
	 * Refactored from a previous project. All the code used is my own.
	 * 
	 * @param utensilList The full users utensil list that will be randomized
	 * @param amount How many utensils the user wants in the randomized list
	 * 
	 * @return List<UtensilModel> List of recent Utensils
	 */
	public List<UtensilModel> getRandomizedUtensil(List<UtensilModel> utensilList, int amount)
	{
		//List of utensils
		List<UtensilModel> randomList = new ArrayList<UtensilModel>();
		Random rand = new Random(); //For picking a random utensil
		
		
		//If the size of the list is 0 then don't continue
		if (utensilList.size() > 0)
		{
			logger.info("Utensil List is larger then 0");
			int[] randomNum = new int[utensilList.size()];
			int randomNumber;
			
			int count = 0;
			//While loop that adds to the list as long as count is less the number of utensils the user wanted
			//AND as long as there are that many Utensils in the list.
			while ((count < amount) && (count < utensilList.size()))
			{
				randomNumber = rand.nextInt(utensilList.size());
				int count2 = 0;
				
				//Prevents the same utensil from being added twice
				while(count2 < count)
				{
					
					
					if (randomNum[count2] == randomNumber)
					{
						randomNumber = rand.nextInt(utensilList.size());
						count2 = 0;
					}
					else
					{
						count2++; //Only add to the counter if a new number is found
					}
				}
				
				randomNum[count] = randomNumber;
				randomList.add(utensilList.get(randomNumber)); //Need to make a list of numbers not to repeat
				count++;
			}
			
		}
		
		logger.info("Random List Created");
		return randomList;
		
	}


	/**
	 * Calls DAO to pull utensil from database.
	 * 
	 * @param id of the utensil in database 
	 * 
	 * @return utensil model of the utensil that matches the id. 
	 */
	@Override
	public UtensilModel getUtensilById(int id) 
	{
		logger.info("Pulled One Utensil");
		return serviceUtensil.findById(id);
	}
	
	
	/**
	 * Calls DAO to pull every utensil from database.
	 * 

	 * @return List of utensils. 
	 */
	@Override
	public List<UtensilModel> getEveryUtensil() 
	{
		logger.info("Pulled Every Utensil");
		return serviceUtensil.findAllUtensils();
	}

}
