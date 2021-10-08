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
	public ArrayList<Recipe> getAllRecipes() throws ClassNotFoundException, IOException {
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();

		for (File regularFile : folder.listFiles()) {
			if (!regularFile.isDirectory()) {
				recipes.add(Recipe.fromFile(regularFile.getName(), regularFile));
			}
		}

		return recipes;
	}


	@Override
	public Recipe getRecipe(String id) throws RecipeNotFoundException, ClassNotFoundException, IOException {
		File recipe = new File(folder, id + ".txt");
		if (!recipe.exists()) {
			System.out.println("Error opening file.");
			throw new RecipeNotFoundException();
		}
		return Recipe.fromFile(id, recipe);
	}

	@Override
	public boolean writeRecipe(Recipe recipe) {
		try {
			File file = new File(folder, recipe.getId() + ".txt");
			FileOutputStream fos = new FileOutputStream(file);
			if (!file.exists()) { file.createNewFile(); }


			ObjectOutputStream objOut = new ObjectOutputStream(fos);
			// Writes objects to the output stream
			objOut.writeObject(recipe);
			objOut.close();
			return true;

		}
		catch (Exception e) {
			e.getStackTrace();
			System.out.println("Could not write to file :(");
			return false;
		}

	}
}
