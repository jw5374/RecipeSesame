package com.recipesesame.functions;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
	
	public static void searchAllRecipes(Database database, String searchKey, BufferedOutputStream out) throws IOException {
		ArrayList<Recipe> recipes = database.getAllRecipes();
		
		recipes.removeIf(r -> !r.getTitle().toLowerCase().contains(searchKey.toLowerCase()));
		
		for (int i = 0; i < recipes.size(); i++) {
			out.write((recipes.get(i).getDisplayInfo() + "\n").getBytes());
		}
	}

	public static void addRecipe(Database database, BufferedOutputStream out, Scanner scan) throws IOException {
		String input = "";
		Recipe recipe = new Recipe();
		while(input.equalsIgnoreCase("finish") != true) {

			out.write("Here you can add a recipe \n".getBytes());
			out.write(("Please fill out all the following fields by typing the number\n or to finish writing the recipe type \"finish\", otherwise type \"abort\": \n" + 
					"1. Title\n" + 
					"2. Subtitle (flavor text)\n" +
					"3. Serving Size\n" +
					"4. Preparation time (a number in minutes)\n" + 
					"5. Cook time (a number in minutes)\n" +
					"6. Ingredients\n" + 
					"7. Instructions\n").getBytes());
			out.flush();
			input = scan.next();
			if(input.equalsIgnoreCase("abort")) {
				return;
			}
			switch(input) {
				case "1":
					System.out.print("What is the title? ");
					recipe.setTitle(scan.next());
					break;
				case "2":
					System.out.print("What is the subtitle? ");
					recipe.setSubtitle(scan.next());
					break;
				case "3":
					System.out.println("What is the total serving size? (e.g. \"5 people\"): "); 
					Quantity serving = new Quantity(scan.nextInt(), scan.next());
					recipe.setServingSize(serving);
					break;
				case "4":
					System.out.print("What is the prep time (in minutes)? ");
					recipe.setPrepTime(scan.nextInt());
					break;
				case "5":
					System.out.print("What is the cooking time (in minutes)? ");
					recipe.setCookTime(scan.nextInt());
					break;
				case "6":
					System.out.println("What are the ingredients? ");
					String ingredientsInput = "";
					ArrayList<Ingredient> ingredientlist = new ArrayList<>();
					while(ingredientsInput.equalsIgnoreCase("done") != true) {
						out.write("Your ingredient is composed of a quantity and material\n".getBytes());
						out.write("What is the quantity? (e.g. \"1 tablespoon\"): ".getBytes());
						out.flush();
						Quantity ingredientQuant = new Quantity(scan.nextInt(), scan.next());
						System.out.print("What is the material? (e.g. 'salt'): ");
						ingredientlist.add(new Ingredient(ingredientQuant, scan.next()));
						System.out.print("Done? ");
						ingredientsInput = scan.next();
					}
					recipe.setIngredients(ingredientlist);
					break;
				case "7":
					System.out.println("What are the instructions? ");
					ArrayList<Step> steps = new ArrayList<>();
					String instructionsinput = "";
					while(!instructionsinput.equalsIgnoreCase("done")) {
						instructionsinput = scan.nextLine();
						Step step = new Step(instructionsinput);
						steps.add(step);
						System.out.print("Done? ");
						instructionsinput = scan.next();
					}
					recipe.setInstructions(steps);
					break;
				default:
					System.out.println("Unrecognized field/command".getBytes());
			}
		}
		database.writeRecipe(recipe);
	}
}
