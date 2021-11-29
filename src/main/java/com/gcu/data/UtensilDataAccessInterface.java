package com.gcu.data;

import java.util.List;


/*
 * Date: 11/24/21
 * Interface to be used by Utensil Data Service to specifically check for the user database table
 * 
 * @author Michael M.
 * @version 2
 */
public interface UtensilDataAccessInterface<T>
{
	public List<T> findBySearchTerm(T t);
	List<T> findRecentUtensils(int id);
}
