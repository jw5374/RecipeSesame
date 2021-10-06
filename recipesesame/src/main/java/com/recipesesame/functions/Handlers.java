package com.recipesesame.functions;

import java.util.ArrayList;

import com.recipesesame.database.*;

public class Handlers {
	public static String displayAllRecipes(Database database) {
		ArrayList<Recipe> recipes = database.getAllRecipes();
		String output = "";
		for (int i = 0; i < recipes.size(); i++) {
			output += recipes.get(i).getDisplayInfo() + "\n";
		}
		return output;
	}
	
	public static void searchAllRecipes(Database database, String searchKey) {
		ArrayList<Recipe> recipes = database.getAllRecipes();
		ArrayList<Recipe> foundRecipes = new ArrayList<Recipe>();
		
		for (int i = 0; i < recipes.size(); i++) {
			if (recipes.get(i).getTitle().toLowerCase().contains(searchKey.toLowerCase())) {
				foundRecipes.add(recipes.get(i));
			}
		}
		
		for (int i = 0; i < foundRecipes.size(); i++) {
			System.out.println(foundRecipes.get(i).getDisplayInfo());
		}
	}
}
