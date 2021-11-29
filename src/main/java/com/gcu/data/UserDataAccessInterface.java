package com.gcu.data;



/*
 * Date: 10/28/21
 * Interface to be used by User Data Service to specifically check for the user database table
 * @author Michael M.
 * @version 2
 */
public interface UserDataAccessInterface<T>
{
	public T findByUsername(String username);
	public int findId(T t);	
}
