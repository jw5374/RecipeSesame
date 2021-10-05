package com.recipesesame.functions;

import java.util.ArrayList;

import com.recipesesame.database.*;

public class Handlers {
	public static void displayAllRecipes(Database database) {
		ArrayList<Recipe> recipes = database.getAllRecipes();
		
		for (int i = 0; i < recipes.size(); i++) {
			System.out.println(recipes.get(i).getDisplayInfo());
		}
	}
}
