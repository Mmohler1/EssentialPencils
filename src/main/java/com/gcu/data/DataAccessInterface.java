package com.gcu.data;

import java.util.List;



/*
 * Date: 10/28/21
 * Interface to be used by the Product Data Service and User Data Service
 * @author Michael M.
 * @version 2
 */
public interface DataAccessInterface<T>
{
	public List<T> findAll(int id);
	public int create(T t);
	public int update(T t);
	public int delete(T t);
	
	
}
