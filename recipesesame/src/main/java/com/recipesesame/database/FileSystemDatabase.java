package com.recipesesame.database;

import java.io.*;
import java.util.*;

import com.recipesesame.functions.*;
import com.recipesesame.utils.*;

public class FileSystemDatabase extends Database {
	private File folder;

	public FileSystemDatabase(String directory) throws FileNotFoundException {
		this.folder = new File(directory);
		if (!this.folder.exists()) {
			System.out.println("Error opening directory.");
			throw new FileNotFoundException();
		}
	}

	@Override
	public ArrayList<Recipe> getAllRecipes() {
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		
		for (File regularFile : folder.listFiles()) {
			if (!regularFile.isDirectory()) {
				recipes.add(Recipe.fromFile(regularFile.getName(), regularFile));
			}
		}

		return recipes;
	}

	@Override
	public Recipe getRecipe(String id) throws RecipeNotFoundException {
		File recipe = new File(folder, id + ".txt");
		if (!recipe.exists()) {
			System.out.println("Error opening file.");
			throw new RecipeNotFoundException();
		}

		return Recipe.fromFile(id, recipe);
	}

	@Override
	public boolean writeRecipe(Recipe recipe) {
		String filename = "";

		// create if not exists
		try {
			File recipeFile = new File(folder, recipe.getID() + ".txt");
			recipeFile.createNewFile();

			filename = recipeFile.getAbsolutePath();
		} catch (IOException e) {
			System.out.println("Error creating file.");
			e.printStackTrace();
			return false;
		}

		try {
			// overwrite
			FileWriter myWriter = new FileWriter(filename);
			myWriter.write(recipe.toString());
			myWriter.close();
		} catch (IOException e) {
			System.out.println("Error writing to file.");
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
