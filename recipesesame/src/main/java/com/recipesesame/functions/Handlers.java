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
}
