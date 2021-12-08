package com.gcu.business;


import com.gcu.model.RegisterModel;

/**
 * Date: 10/12/21
 * Interface for the Register service pages
 * 
 * @author Michael M.
 * @version 1
 */
public interface UserBusinessServiceInterface {

	public int processRegister(RegisterModel registerModel);

}
