package com.gcu.business;

import java.util.List;

import com.gcu.model.UtensilModel;

/*
 * Date: 11/02/21
 * Interface for the business layer of the Utensil Product
 * 
 * @author Michael M.
 * @version 1
 */
public interface UtensilBusinessInterface 
{
	public int insertUtensil(UtensilModel utsenislModel);
	public List<UtensilModel> displayAllUtensils(int id);
	public List<UtensilModel> displayRecentUtensils(int id);
	int changeUtensil(UtensilModel utsenislModel);
	int eraseUtensil(UtensilModel utsenislModel);
	public List<UtensilModel> displaySearchedUtensil(UtensilModel utensilModel);
	public List<UtensilModel> getRandomizedUtensil(List<UtensilModel> utensilList, int amount);
}
