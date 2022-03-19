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

import com.gcu.model.RegisterModel;
import com.gcu.utility.DatabaseException;

/**
 * Date: 10/28/21
 * Data Service class for dealing with functions relating to the Users Database
 * @author Michael M.
 * @version 2
 */
@Service
public class UsersDataService implements UserDataAccessInterface<RegisterModel>, DataAccessInterface<RegisterModel> 
{
	//For the logger
	private static final Logger logger = LoggerFactory.getLogger(UsersDataService.class);

	//Initialize the Data Source and JDBC
	@SuppressWarnings("unused")
	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	/**
	 * Constructor
	 * Set up the Data Source when service is called.
	 * 
	 * @param DataSource Used to setup the JDBC Template 
	 * 
	 */
	public UsersDataService(DataSource dataSource)
	{
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
		
	}
	
	//Currently an empty list of users.
	@Override
	public List<RegisterModel> findAll(int id) 
	{
		
		List<RegisterModel> users = new ArrayList<RegisterModel>();

		return users;
	}
	
	
	/**
	 * Logs the user in by checking for a matching username and password in the database.
	 * 
	 * 
	 * @param registerModel Used to check for matching credentials in database
	 * 
	 * @return int Used to determine what to do
	 */
	@Override
	public RegisterModel findByUsername(String username) 
	{	
		
		//SQL String for finding a row with a matching username and password.
		String sql = "SELECT * FROM users WHERE USERNAME = '" + username + "'";
		logger.info("SQL string is: " + sql);
		
		RegisterModel user = new RegisterModel(); 
		
		
		//Try run the SQL query
		try
		{
			//If a table is found return 0 for success. If 1 then the credentials failed
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);
			if(srs.next())
			{
				user.setId(srs.getInt("ID"));
				user.setUsername(srs.getString("USERNAME"));
				user.setPassword(srs.getString("PASSWORD"));
				logger.info("User details found");
				
				//Send user back with username and password
				return user;
			}
			else
			{
				//Send user with default credentials
				logger.warn("No User Found");
				return user;
			}
			
		}
		catch (Exception e)
		{
			//Throw Database error
			logger.error("Database Error");
			throw new DatabaseException("The Database is currently down. Login was unsuccessful");
		}
		

	}

	
	/**
	 * Registers the user and returns an int, relating to a code to display to the user.
	 * 
	 * @param registerModel Used to add details of user to database 
	 * 
	 * @return int Used to determine what to do
	 */
	@Override
	public int create(RegisterModel registerModel)
	{
		//SQL string that inserts the user into the database
		String sql = "INSERT INTO `users` (`ID`, `USERNAME`, `PASSWORD`, `FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PHONE`) VALUES (NULL, '"
				+ registerModel.getUsername() + "', '" + registerModel.getPassword() +"', '"
				+ registerModel.getFirstName()+"', '"+ registerModel.getLastName() 
				+"', '"+ registerModel.getEmail() +"', '"+ registerModel.getPhone() +"');";
		
		
		logger.info("SQL string is: " + sql);
		//Try to add user to the database 
		try
		{
			//Call method that checks if username or email is available
			if(checkAvailability(registerModel) == false)
			{
				//Username and Email are free so insert user to database
				jdbcTemplateObject.update(sql);
				logger.info("User added");
				
				//Return 0 to represent the insert worked
				return 0;
			}
			else
			{
				logger.warn("Cannot register user with taken credentials");
				//Return 1 to represent email or username taken
				return 1;
			}
			
		}
		catch (Exception  e)
		{
			//Throw Database error
			logger.error("Database Error");
			throw new DatabaseException("The Database is currently down. Registration was unsuccessful");
		}
		

	}

	@Override
	public int update(RegisterModel t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(RegisterModel t) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/**
	 * Method used within the layer to check if a username or email is taken
	 * 
	 * @param registerModel Used to pull email and username
	 * 
	 * @return boolean Used to determine what to do
	 */
	public boolean checkAvailability(RegisterModel registerModel)
	{
		//SQL String that finds any row with the same username or email
		String sql = "SELECT * FROM users WHERE USERNAME = '" + registerModel.getUsername() + "' OR Email = '" + registerModel.getEmail() + "'";
		logger.info("SQL string is: " + sql);
		
		//Try to check row
		try
		{
			//If row is found then return true that credentials were taken
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);
			if(srs.next())
			{
				//Username or Email is taken
				logger.warn("Email and Username taken");
				return true;
			}
			else
			{
				//Username and Email are not taken
				logger.info("Username and Email have not been taken");
				return false;
			}
			
		}
		catch (Exception e)
		{
			///Throw Database error
			logger.error("Database Error");
			throw new DatabaseException("The Database is currently down. Registration was unsuccessful");
		}
		

	}


	
	/**
	 * Return Id by checking for username in database. Will 
	 * be used to create a session variable.
	 * 
	 * @param registerModel Pull username to get id from database
	 * 
	 * @return int Used to determine what to do
	 */
	@Override
	public int findId(RegisterModel registerModel) {
		
		//SQL String that finds rows for 
		String sql = "SELECT * FROM USERS WHERE USERNAME = '" + registerModel.getUsername() + "'";
		logger.info("SQL string is: " + sql);
		
		//Try to run SQL Query
		try
		{
			//If a row is found then return the id
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);
			if(srs.next())
			{
				logger.info("Id Found");
				//Returns users id for session
				return srs.getInt("ID");
			}
			else
			{
				//Return a -1 if no user is found.
				logger.warn("No user found");
				return -1;
			}
			
		}
		catch (Exception e)
		{
			//Throw Database error
			logger.error("Database Error");
			throw new DatabaseException("The Database is currently down. Login was unsuccessful");
		}
		

	}
	
	
}
