package com.gcu.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
/**
 * Date:10/15/2021
 * Essential Pencils is a site that let's users catalogue there art supplies. The Utensils are the product for this site that will be saved 
 * in a database. The user can add, delete, edit, and get more details on their utensils. They will also be able to search their database for
 * certain supplies. Finally there will be an option to get a random choice of utensils. 
 * @author Michael M.
 * @version 2.
 */
@ComponentScan({"com.gcu"})
@SpringBootApplication
public class EssentialPencils {

	public static void main(String[] args) {
		SpringApplication.run(EssentialPencils.class, args);
	}

}
