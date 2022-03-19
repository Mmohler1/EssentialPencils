package com.gcu.data;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.gcu.model.UtensilModel;
import com.gcu.utility.DatabaseException;

/**
 * Date: 11/02/21
 * DAO for the Utensil Database.
 * Will be used to communicate between the Utensil Business Layer and the Database.
 * 
 * @author Michael M.
 * @version 1
 */
@Service
public class UtensilDataService implements DataAccessInterface<UtensilModel>, UtensilDataAccessInterface<UtensilModel> {

	//For the logger
	private static final Logger logger = LoggerFactory.getLogger(UtensilDataService.class);

	//Initialize the Data Source and JDBC
	@SuppressWarnings("unused")
	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	
	/**
	 * Constructor
	 * Set up the Data Source when service is called.
	 * 
	 * @param dataSource Used to setup the JDBC Template 
	 * 
	 */
	public UtensilDataService(DataSource dataSource)
	{
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	
	
	/**
	 * Returns a list of all the utensil in a database that matches the user's id.
	 * 
	 * @param id that determines what utensils are pulled.
	 * 
	 * @return List<UtensilModel> List of Utensils
	 */
	@Override
	public List<UtensilModel> findAll(int id) {

		
		//SQL String that creates a view of every row that matches the user's id. 
		String sql = "SELECT * FROM utensils WHERE USER_ID = '" + id + "'";
		logger.info("SQL string is: " + sql);
		
		//Initialize the list for the Utensil Models
		List<UtensilModel> utensils = new ArrayList<UtensilModel>();
		
		//Try to make a list of utensils
		try
		{
			//For every row in the table make a utensil and add it to a list.
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);
			while(srs.next())
			{
				utensils.add(new UtensilModel(srs.getInt("UTENSIL_ID"),
											srs.getInt("USER_ID"),
											srs.getString("TYPE"),
											srs.getString("BRAND"),
											srs.getString("COLOR"),
											srs.getInt("QUANTITY"),
											srs.getString("SIZE")));		
			}	
			
			logger.info("Utensils list created for find");
			return utensils;
		}
		catch (Exception e)
		{
			//Throw Database error
			logger.error("Database Error");
			throw new DatabaseException("The Database is currently down.");
		}
		

	}


	
	/**
	 * Create a utensil for the database. Returns an int so a
	 * proper error message can be displayed.
	 * 
	 * @param utensilModel Used to add utensil to database 
	 * 
	 * @return int Used to determine what to do
	 */
	@Override
	public int create(UtensilModel utensilModel) 
	{
		
		//SQL string that inserts the variables for the utensil class.
		String sql = "INSERT INTO `utensils` (`UTENSIL_ID`, `USER_ID`, `BRAND`, `TYPE`, `COLOR`, `QUANTITY`, `SIZE`) VALUES (NULL, '"
												+ utensilModel.getUserId() + "', '" 
												+ utensilModel.getBrand() + "', '" 
												+ utensilModel.getType() + "', '" 
												+ utensilModel.getColor() + "', '" 
												+ utensilModel.getQuantity() + "', '" 
												+ utensilModel.getSize() + "')";
		logger.info("SQL string is: " + sql);
		
		//Try to create the utensil in the database. 
		//Return 0 if update is successful, 1 if there was an error in the database.
		try
		{
			//Adds the utensil to the database
			jdbcTemplateObject.update(sql);
			logger.info("Utensil Added");
			return 0; 
		}
		catch(Exception e)
		{
			
			//Throw Database error
			logger.error("Database Error");
			throw new DatabaseException("The Database is currently down. Adding utensil was unsuccessful");
		}
		

	}

	
	/**
	 * Change a utensil from the database. Returns an int so a
	 * proper error message can be displayed.
	 * 
	 * @param utensilModel Used to change utensil from database 
	 * 
	 * @return int Used to determine what to do
	 */
	@Override
	public int update(UtensilModel utensilModel) 
	{
		//SQL string that inserts the variables for the utensil class.
		String sql = "UPDATE `utensils` SET "
				+ "`BRAND`='" + utensilModel.getBrand() + 
				"',`TYPE`='" + utensilModel.getType() +
				"',`COLOR`='" + utensilModel.getColor() + 
				"', `QUANTITY`='" + utensilModel.getQuantity() + 
				"',`SIZE`='" + utensilModel.getSize() +
				"' WHERE UTENSIL_ID = " + utensilModel.getUtensilId() +
				" AND USER_ID = " + utensilModel.getUserId() + ";";
		logger.info("SQL string is: " + sql);
		
		//Try to update the utensil in the database. 
		//Return 0 if update is successful, 1 if there was an error in the database.
		try
		{
			//Adds the utensil to the database
			
			jdbcTemplateObject.update(sql);
			logger.info("Utensil Updated");
			return 0; 
		}
		catch(Exception e)
		{
			
			//Throw Database error
			logger.error("Database Error");
			throw new DatabaseException("The Database is currently down. Updating the utensil was unsuccessful");
		}
				

	}

	
	/**
	 * Delete the product from the database.
	 * 
	 * @param utensilModel Used to find out which utensil to delete from database
	 * 
	 * @return int Used to determine what happened
	 */
	@Override
	public int delete(UtensilModel utensilModel) 
	{
		
		String sql = "DELETE FROM `utensils` WHERE UTENSIL_ID = " + utensilModel.getUtensilId() + " AND USER_ID = " + utensilModel.getUserId() + ";";
		logger.info("SQL string is: " + sql);
		
		try
		{
			//If one row is effected then the utensil was deleted. If not, throw DatabaseException.
			if(jdbcTemplateObject.update(sql) == 1)
			{	
				logger.info("Utensil Deleted");
				return 0; 
			}
			else
			{
				logger.warn("Utensil not deleted");
				return 1;
				
			}
		}
		catch(Exception e)
		{
			//Throw Database error
			logger.error("Database Error");
			throw new DatabaseException("The Database is currently down. Deleting utensil was unsuccessful");
		}
	}


	/**
	 * Search utensil in database
	 * 
	 * @param utensilModel Holds UserId properly, but also uses a type as a search term.
	 * 
	 * @return int Used to determine what happened
	 */
	@Override
	public List<UtensilModel> findBySearchTerm(UtensilModel utensilModel) 
	{
		//Pull the information from the utensilModel so it is easier to read.
		int id = utensilModel.getUserId();
		String searchTerm = utensilModel.getType();
		
		
		//SQL String that creates a view that searches every column
		String sql = "SELECT * FROM utensils WHERE USER_ID = '" + id + "' AND (BRAND LIKE '%" + searchTerm + "%' OR TYPE LIKE '%" + searchTerm + "%' "
				+ "OR COLOR LIKE '%" + searchTerm + "%' OR QUANTITY LIKE '%"+ searchTerm +"%' OR SIZE LIKE '%"+ searchTerm + "%');";
		logger.info("SQL string is: " + sql);
		
		//Initialize the list for the Utensil Models
		List<UtensilModel> utensils = new ArrayList<UtensilModel>();
		
		//Try to make a list of utensils
		try
		{
				
			//For every row in the table make a utensil and add it to a list.
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);
			while(srs.next())
			{
				utensils.add(new UtensilModel(srs.getInt("UTENSIL_ID"),
											srs.getInt("USER_ID"),
											srs.getString("BRAND"),
											srs.getString("TYPE"),
											srs.getString("COLOR"),
											srs.getInt("QUANTITY"),
											srs.getString("SIZE")));		
			}	
			logger.info("Utensils list created for search");
			return utensils;
		}
		catch (Exception e)
		{
			
			//Throw Error
			logger.error("Database Error");
			throw new DatabaseException("The Database is currently down. Utensils could not be searched");

		}
	}



	/**
	 * Returns a list of the last 3 utensil in descending order from the database that matches the user's id.
	 * 
	 * @param id that determines what utensils are pulled.
	 * 
	 * @return List<UtensilModel> List of Utensils
	 */
	@Override
	public List<UtensilModel> findRecentUtensils(int id) 
	{
		//SQL String that creates a view of every row that matches the user's id. 
		String sql = "SELECT * FROM utensils WHERE USER_ID = '" + id + "' ORDER BY UTENSIL_ID DESC LIMIT 3;";
		logger.info("SQL string is: " + sql);
		
		//Initialize the list for the Utensil Models
		List<UtensilModel> utensils = new ArrayList<UtensilModel>();
		
		
		//Try to make a list of utensils
		try
		{
			//For every row in the table make a utensil and add it to a list.
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);
			while(srs.next())
			{
				utensils.add(new UtensilModel(srs.getInt("UTENSIL_ID"),
											srs.getInt("USER_ID"),
											srs.getString("TYPE"),
											srs.getString("BRAND"),
											srs.getString("COLOR"),
											srs.getInt("QUANTITY"),
											srs.getString("SIZE")));		
			}	
			
			logger.info("Utensils list created for last 3 utensils");
			return utensils;
		}
		catch (Exception e)
		{
			//Throw Database error
			logger.error("Database Error");
			throw new DatabaseException("The Database is currently down.");
		}
	}



	/**
	 * Pull Utensil from database based on id.
	 * 
	 * @param id that matches utensil id in database
	 * 
	 * @return Utensil model of utensil from database.
	 */
	@Override
	public UtensilModel findById(int id) {
		
		String sql = "SELECT * FROM utensils WHERE UTENSIL_ID = '" + id + "';";
		logger.info("SQL string is: " + sql);
		
		UtensilModel utensil;
		
		
		//Try to make a list of utensils
		try
		{
			//For every row in the table make a utensil and add it to a list.
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);
			if(srs.next())
			{
				utensil = new UtensilModel(srs.getInt("UTENSIL_ID"),
											srs.getInt("USER_ID"),
											srs.getString("TYPE"),
											srs.getString("BRAND"),
											srs.getString("COLOR"),
											srs.getInt("QUANTITY"),
											srs.getString("SIZE"));		
			
				logger.info("Utensil created from id");
				return utensil;
			}
			else
			{
				logger.warn("No Utensils Found");
				return null;
			}
			
			
		}
		catch (Exception e)
		{
			//Throw Database error
			logger.error("Database Error");
			throw new DatabaseException("The Database is currently down.");
		}
	}


	/**
	 * Pulls every utensil in the database and returns it.
	 * 
	 * @return List of Utensil of every utensil found in database.
	 */
	@Override
	public List<UtensilModel> findAllUtensils() 
	{


		
		//SQL String that creates a view of every row that matches the user's id. 
		String sql = "SELECT * FROM utensils";
		logger.info("SQL string is: " + sql);
		
		//Initialize the list for the Utensil Models
		List<UtensilModel> utensils = new ArrayList<UtensilModel>();
		
		
		//Try to make a list of utensils
		try
		{
			//For every row in the table make a utensil and add it to a list.
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);
			while(srs.next())
			{
				utensils.add(new UtensilModel(srs.getInt("UTENSIL_ID"),
											srs.getInt("USER_ID"),
											srs.getString("TYPE"),
											srs.getString("BRAND"),
											srs.getString("COLOR"),
											srs.getInt("QUANTITY"),
											srs.getString("SIZE")));		
			}	
			
			logger.info("Utensils list created for find all");
			return utensils;
		}
		catch (Exception e)
		{
			//Throw Database error
			logger.error("Database Error");
			throw new DatabaseException("The Database is currently down.");
		}
		

	}

}
