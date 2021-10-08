package com.recipesesame.functions;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import com.recipesesame.database.*;

public class Handlers {
	private static void printOutRecipes(ArrayList<Recipe> recipes, BufferedOutputStream out) throws IOException {
		String output = "";
		for (int i = 0; i < recipes.size(); i++) {
			out.write((recipes.get(i).getDisplayInfo() + "\n").getBytes());
		}
	}
	
	public static void displayAllRecipes(Database database, BufferedOutputStream out) throws IOException, ClassNotFoundException {
		ArrayList<Recipe> recipes = database.getAllRecipes();
		printOutRecipes(recipes, out);
	}
	
	public static void displayAllSortedRecipes(Database database, SortedBy sortKey, BufferedOutputStream out) throws IOException, ClassNotFoundException {
		ArrayList<Recipe> recipes = database.getAllRecipes();
		
		switch (sortKey) {
		case COOKTIME:
			recipes.sort(Comparator.comparing(Recipe::getCookTime));
			break;
		case PREPTIME:
			recipes.sort(Comparator.comparing(Recipe::getPrepTime));
			break;
		case TOTALTIME:
			Collections.sort(recipes, (recipe1, recipe2) -> {
				return Integer.compare(
					recipe1.getCookTime() + recipe1.getPrepTime(), 
					recipe2.getCookTime() + recipe1.getPrepTime()
				); 
			});
		case SERVINGSIZE:
			Collections.sort(recipes, (recipe1, recipe2) -> {
				return Integer.compare(
					recipe1.getServingSize().getAmount(),
					recipe2.getServingSize().getAmount()
				); 
			});
			break;
		case TITLE:
			recipes.sort(Comparator.comparing(Recipe::getTitle));
			break;
		default:
			recipes.sort(Comparator.comparing(Recipe::getTitle));
			break;
		}
		
		printOutRecipes(recipes, out);
	}
	
	public static void searchAllRecipes(Database database, String searchKey, BufferedOutputStream out) throws IOException, ClassNotFoundException {
		ArrayList<Recipe> recipes = database.getAllRecipes();
		
		recipes.removeIf(r -> !r.getTitle().toLowerCase().contains(searchKey.toLowerCase()));

		printOutRecipes(recipes, out);
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
