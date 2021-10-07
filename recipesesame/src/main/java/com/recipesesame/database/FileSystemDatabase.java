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
	public ArrayList<Recipe> getAllRecipes() throws RecipeNotFoundException {
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();

		for (File regularFile : folder.listFiles()) {
			if (!regularFile.isDirectory()) {
				String fileName = regularFile.getName();
				if (fileName.equals("recipes.txt")) continue;
					//get rid of .txt in fileName
				else recipes.add(getRecipe(fileName.substring(0, fileName.length() - 4)));
			}
		}

		return recipes;
	}


	@Override
	public Recipe getRecipe(String id) throws RecipeNotFoundException {
		try{
			File file = new File("recipesesame/src/main/java/com/recipesesame/recipes/" + id + ".txt");
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream objIn = new ObjectInputStream(fileIn);

			Recipe newRecipe = (Recipe) objIn.readObject();

			//testing
			System.out.println("Recipe ID: " + newRecipe.getId());
			System.out.println("Recipe Title: " + newRecipe.getTitle());

			objIn.close();

			return newRecipe;
		} catch (Exception e){
			System.out.println("No recipe found :(");
			return null;
		}
	}

	@Override
	public boolean writeRecipe(Recipe recipe) {
		try {
			File file = new File("recipesesame/src/main/java/com/recipesesame/recipes/" + recipe.getId() + ".txt");
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
