package com.gcu.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Date: 10/05/21
 * Model to handle data for each drawing utensil. Every variable is private and getters and setters are used for each one.
 * Validation is on all variables but utensil and user id, as that is there for a future database. 
 * 
 * @author Michael M.
 * @version 1.
 *
 */
public class UtensilModel 
{
	private int utensilId;
	
	private int userId;
	
	@NotNull(message="Type is a required field")
	@Size(min=3, max=20, message="Type must be between 1 and 32 characters")	
	private String type;
	
	@NotNull(message="Brand is a required field")
	@Size(min=3, max=20, message="Brand must be between 3 and 20 characters")
	private String brand;
	
	@NotNull(message="Color is a required field")
	@Size(min=3, max=20, message="Color must be between 3 and 20 characters")
	private String color;
	
	@NotNull(message="Quantity is a required field")
	private int quantity;
	
	@NotNull(message="Size is a required field")
	@Size(min=4, max=25, message="Size must be between 3 and 20 characters")
	private String size;


	//Constructor
	public UtensilModel(int utensilId, int userId, String type, String brand, String color, int quantity, String size) {
		
		this.utensilId = utensilId;
		this.userId = userId;
		this.type = type;
		this.brand = brand;
		this.color = color;
		this.quantity = quantity;
		this.size = size;
	}
	
	
	
	
	//Default constructor
	public UtensilModel() {
		this.utensilId = 0;
		this.userId = 0;
		this.type = "type";
		this.brand = "brand";
		this.color = "color";
		this.quantity = 0;
		this.size = "size";
	}




	//Getters and Setters
	public String getType() {
		return type;
	}

	

	public void setType(String type) {
		this.type = type;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getUtensilId() {
		return utensilId;
	}

	public void setUtensilId(int utensilId) {
		this.utensilId = utensilId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	
	
	
}
