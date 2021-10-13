package com.recipesesame.functions;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;

import com.recipesesame.database.*;
import com.recipesesame.utils.RecipeNotFoundException;

public class Handlers {
	private static void printOutRecipes(ArrayList<Recipe> recipes, BufferedOutputStream out) throws IOException {
		out.write(("--------------------\n").getBytes());
		out.write(("-     (￣﹃￣)     -\n").getBytes());
		out.write(("--------------------\n").getBytes());
		for (int i = 0; i < recipes.size(); i++) {
			out.write(("\n"+ recipes.get(i).getDisplayInfo() + "\n").getBytes());
			out.write(("--------------------\n").getBytes());
		}
		out.write(("-     (￣﹃￣)     -\n").getBytes());
		out.write(("--------------------\n").getBytes());
		out.flush();
	}
	
	public static void displayAllRecipes(Database database,	ArrayList<Recipe> recipes, BufferedOutputStream out, Scanner scan) throws IOException, ClassNotFoundException {
		out.write("\nYou can display recipes sorted by TITLE, PREPTIME, COOKTIME, TOTALTIME, SERVINGSIZE or simply get all of them.\n".getBytes());
		out.write("Type the corresponding sort key or \"all\": ".getBytes());
		out.flush();
		String sortKey = scan.next();
		if(sortKey.equalsIgnoreCase("all")) {
			printOutRecipes(recipes, out);
		} else {
			try {
				SortedBy value = SortedBy.valueOf(sortKey.toUpperCase());
				displayAllSortedRecipes(database, recipes, value, out);
			} catch (IllegalArgumentException e) {
				System.out.println("Wrong sort value.\n" + e.toString());
				displayAllRecipes(database, recipes, out, scan);
			}
		}
		return;
	}
	
	public static void displayAllSortedRecipes(Database database, ArrayList<Recipe> recipes, SortedBy sortKey, BufferedOutputStream out) throws IOException, ClassNotFoundException {
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
		recipes.removeIf(r -> !(
			(Objects.nonNull(r.getTitle()) && r.getTitle().toLowerCase().contains(searchKey.toLowerCase()))
			|| (Objects.nonNull(r.getSubtitle()) && r.getSubtitle().toLowerCase().contains(searchKey.toLowerCase()))
			|| (Objects.nonNull(r.getIngredients()) && r.getIngredients().removeIf(i -> i.getMaterial().toLowerCase().contains(searchKey.toLowerCase())))
			|| (Objects.nonNull(r.getTags()) && r.getTags().contains(searchKey))
		));

		printOutRecipes(recipes, out);
	}

	public static void getRandomRecipe(Database database, ArrayList<Recipe> recipes) throws IOException, ClassNotFoundException {
		int random = (int) ((double) (recipes.size() - 1)  * Math.random());
		String randomId = recipes.get(random).getId();

		System.out.println("\nHere's a random recipe id: " + randomId + "\nEnter it in below and have fun!\n");
	}

	public static void exploreRecipes(Database database, Scanner scan) throws IOException, ClassNotFoundException, RecipeNotFoundException {
		System.out.print("Type \"abort\" or recipeID to select a recipe: ");
		String input = scan.next();
		if(input.equalsIgnoreCase("abort")) {
			return;
		}
		try {
			Recipe foundRecipe = database.getRecipe(input);
			while(!input.equalsIgnoreCase("abort")) {
				System.out.print("\n" + foundRecipe.displayAll() + "\n" + "Step through instructions by typing \"step\"\nModify recipe with \"modify\"\nor exit to main menu by \"abort\": ");
				input = scan.next();
				switch(input.toLowerCase()) {
					case "step":
						recipeStepThrough(foundRecipe, scan);
						break;
					case "modify":
						modifyRecipe(scan, foundRecipe);
						break;
					case "abort":
						break;
					default:
						System.out.println("Unrecognized command.");
						break;
				}
			}
			database.writeRecipe(foundRecipe);
		} catch (RecipeNotFoundException e) {
			System.out.println("Invalid recipeID.");
			exploreRecipes(database, scan);
		}
		return;

	}

	public static void addRecipeFromFile(Database database, BufferedOutputStream out, Scanner scan) throws IOException {
		out.write("File path: ".getBytes());
		out.flush();

		String filePath = scan.nextLine();
		
		try{
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			Recipe newRecipe = new Recipe();

			newRecipe.setTitle(reader.readLine());
			newRecipe.setSubtitle(reader.readLine());

			String[] readServing = reader.readLine().split("[ ]");
			Quantity servingSize = new Quantity(Integer.parseInt(readServing[0]), readServing[1]);
			newRecipe.setServingSize(servingSize);

			newRecipe.setPrepTime(Integer.parseInt(reader.readLine()));
			newRecipe.setCookTime(Integer.parseInt(reader.readLine()));

			String readerInput = "";
			ArrayList<Ingredient> ingredientlist = new ArrayList<>();
			while(!readerInput.equalsIgnoreCase("done")) {
				String[] readQuantity = reader.readLine().split("[ ]", 2);
				Quantity quantity = new Quantity(Integer.parseInt(readQuantity[0]), readQuantity[1]);
				ingredientlist.add(new Ingredient(quantity, reader.readLine()));
				readerInput = reader.readLine();
			}

			newRecipe.setIngredients(ingredientlist);
			readerInput = "";
			ArrayList<Step> steps = new ArrayList<>();
			while(!readerInput.equalsIgnoreCase("done")) {
				Step step = new Step(reader.readLine());
				steps.add(step);
				readerInput = reader.readLine();
			}
			newRecipe.setInstructions(steps);
			readerInput = "";
			while(!readerInput.equalsIgnoreCase("done")) {
				newRecipe.addTag(reader.readLine());
				readerInput = reader.readLine();
			}

			database.writeRecipe(newRecipe);

			reader.close();
		} catch (FileNotFoundException e){
			System.out.println("Could not find file... try again.");
			addRecipe(database, out, scan);
		} catch (IllegalArgumentException e) {
			System.out.println("Error reading file... try again.");
			addRecipe(database, out, scan);
		}

	}

	public static void addRecipe(Database database, BufferedOutputStream out, Scanner scan) throws IOException {
		String answer = "";
		scan.nextLine();
		while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")){
			out.write("Do you want to submit a file? (yes/no) \n".getBytes());
			out.flush();
			answer = scan.nextLine();
		}

		if (answer.equalsIgnoreCase("yes")){
			addRecipeFromFile(database, out, scan);
		}
		else{
			String input = "";
			Recipe recipe = new Recipe();
			while(!input.equalsIgnoreCase("finish")) {
				out.write("\nHere you can add a recipe \n".getBytes());
				out.write(("Please fill out all the following fields by typing the number\nor to finish writing the recipe type \"finish\", otherwise type \"abort\": \n" + 
						"1. Title\n" + 
						"2. Subtitle (flavor text)\n" +
						"3. Serving Size\n" +
						"4. Preparation time (a number in minutes)\n" + 
						"5. Cook time (a number in minutes)\n" +
						"6. Ingredients\n" + 
						"7. Instructions\n" + 
						"8. Tags\n").getBytes());
				out.flush();
				input = scan.next();
				if(input.equalsIgnoreCase("abort")) {
					return;
				}
				switch(input) {
					case "1":
						System.out.print("What is the title? ");
						scan.nextLine();
						recipe.setTitle(scan.nextLine());
						break;
					case "2":
						System.out.print("What is the subtitle? ");
						scan.nextLine();
						recipe.setSubtitle(scan.nextLine());
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
						while(!ingredientsInput.equalsIgnoreCase("done")) {
							out.write("Your ingredient is composed of a quantity and material\n".getBytes());
							out.write("What is the quantity? (e.g. \"1 tablespoon\"): ".getBytes());
							out.flush();
							Quantity ingredientQuant = new Quantity(scan.nextInt(), scan.next());
							scan.nextLine();
							System.out.print("What is the material? (e.g. 'salt'): ");
							String material = scan.nextLine();
							ingredientlist.add(new Ingredient(ingredientQuant, material));
							System.out.print("\nPress enter/return to continue inputting the next ingredient.\nOtherwise, type \"done\" to finish. ");
							ingredientsInput = scan.nextLine();
						}
						recipe.setIngredients(ingredientlist);
						break;
					case "7":
						System.out.println("What are the instructions? ");
						System.out.println("The first step: ");
						ArrayList<Step> steps = new ArrayList<>();
						String instructionsinput = "";
						scan.nextLine();
						while(!instructionsinput.equalsIgnoreCase("done")) {
							instructionsinput = scan.nextLine();
							Step step = new Step(instructionsinput);
							steps.add(step);
							System.out.print("\nPress enter/return to continue inputting the next step.\nOtherwise, type \"done\" to finish. ");
							instructionsinput = scan.nextLine();
						}
						recipe.setInstructions(steps);
						break;
					case "8":
						System.out.println("What are the tags? ");
						String tagsinput = "";
						scan.nextLine();
						while(!tagsinput.equalsIgnoreCase("done")) {
							tagsinput = scan.nextLine();
							recipe.addTag(tagsinput);
							System.out.print("\nPress enter/return to continue inputting the next tag.\nOtherwise, type \"done\" to finish. ");
							tagsinput = scan.nextLine();
						}
						break;
					default:
						System.out.println("Unrecognized field/command".getBytes());
				}
			}
			database.writeRecipe(recipe);
		}
	}

	public static void modifyRecipe(Scanner scan, Recipe recipe) {
		String input = "";
		while(!input.equalsIgnoreCase("abort")) {
			System.out.print("\nYou can modify all displayed fields.\nPlease type \"title\", \"subtitle\", \"servingsize\", \"preptime\", \"cooktime\" \n\"tags\", \"instructions\", \"ingredients\", or \"abort\": ");
			input = scan.next();
			switch(input.toLowerCase()) {
				case "title":
					System.out.print("\nType new title: ");
					scan.nextLine();
					recipe.setTitle(scan.nextLine());
					break;
				case "subtitle":
					System.out.print("\nType new sub title: ");
					scan.nextLine();
					recipe.setSubtitle(scan.nextLine());
					break;
				case "servingsize":
					System.out.print("\nType new serving size (e.g. 5 people): ");
					recipe.setServingSize(new Quantity(scan.nextInt(), scan.next()));
					break;
				case "preptime":
					System.out.print("\nType new preparation time (in minutes): ");
					recipe.setPrepTime(scan.nextInt());
					break;
				case "cooktime":
					System.out.print("\nType new cooking time (in minutes): ");
					recipe.setCookTime(scan.nextInt());
					break;
				case "tags":
					String taginput = "";
					scan.nextLine();
					while(!taginput.equalsIgnoreCase("done")) {
						System.out.print("\nType the number of the tag to remove, a new tag to add, or \"cleartags\" to clear all tags\n(e.g. 1, or \"delicious\"): ");
						taginput = scan.nextLine();
						if(taginput.equalsIgnoreCase("cleartags")) {
							recipe.clearTags();
							break;
						}
						try {
							int index = Integer.parseInt(taginput) - 1;
							recipe.getTags().get(index);
							recipe.removeTag(index);
							System.out.println(recipe.displayAll());
						} catch (NumberFormatException nfe) {
							recipe.addTag(taginput);
						} catch (IndexOutOfBoundsException e){
							System.out.println("\nNo tag here.");
						}
						System.out.print("\nType \"done\" if finished: ");
						taginput = scan.nextLine();
					}
					break;
				case "instructions":
					System.out.print("To add an instruction type \"add\", otherwise type the number to edit: ");
					String instructionsinput = scan.next();
					if(instructionsinput.equalsIgnoreCase("add")) {
						scan.nextLine();
						while(!instructionsinput.equalsIgnoreCase("done")) {
							System.out.print("Type instruction to add: ");
							instructionsinput = scan.nextLine();
							recipe.addInstruction(new Step(instructionsinput));
							System.out.print("Type done if finished: ");
							instructionsinput = scan.nextLine();
						}
					} else {
						scan.nextLine();
						int index = Integer.parseInt(instructionsinput) - 1;
						try {
							recipe.getInstructions().get(index);
							System.out.print("\nTo modify type the new instruction, otherwise type \"remove\": ");
							instructionsinput = scan.nextLine();
							if(instructionsinput.equalsIgnoreCase("remove")) {
								recipe.removeInstruction(index);
							} else {
								recipe.getInstructions().set(index, new Step(instructionsinput));
							}
						} catch (IndexOutOfBoundsException e){
							System.out.println("\nNo instructions here.");
						}
						
					}
					break;
				case "ingredients":
					System.out.print("To add an ingredient type \"add\", otherwise type the number to edit: ");
					String ingredientsinput = scan.next();
					if(ingredientsinput.equalsIgnoreCase("add")) {
						while(!ingredientsinput.equalsIgnoreCase("done")) {
							System.out.print("Your ingredient is composed of a quantity and material\nWhat is the quantity? (e.g. \"1 tablespoon\"): ");
							Quantity ingredientQuant = new Quantity(scan.nextInt(), scan.next());
							System.out.print("What is the material? (e.g. 'salt'): ");
							recipe.addIngredient(new Ingredient(ingredientQuant, scan.next()));
							scan.nextLine();
							System.out.print("Type \"done\" if finished: ");
							ingredientsinput = scan.nextLine();
						}
					} else {
						scan.nextLine();
						int index = Integer.parseInt(ingredientsinput) - 1;
						try {
							recipe.getIngredients().get(index);
							System.out.print("\nTo modify type the new ingredient, otherwise type \"remove\": ");
						ingredientsinput = scan.nextLine();
						if(ingredientsinput.equalsIgnoreCase("remove")) {
							recipe.removeIngredient(index);
						} else {
							System.out.print("Your ingredient is composed of a quantity and material\nWhat is the quantity? (e.g. \"1 tablespoon\"): ");
							Quantity ingredientQuant = new Quantity(scan.nextInt(), scan.next());
							System.out.print("What is the material? (e.g. 'salt'): ");
							recipe.getIngredients().set(index, new Ingredient(ingredientQuant, scan.next()));
						}
						} catch (IndexOutOfBoundsException e){
							System.out.println("\nNo ingredient here.");
						}
					}
					break;
				case "abort":
					break;
				default:
					System.out.println("Unrecognized command.");
			}
		}
	}

	public static void recipeStepThrough(Recipe recipe, Scanner scan) {
		String stepinput = "";
		scan.nextLine();
		do {
			recipe.displayNextStep();
			System.out.print("\nPress enter/return to step through, type \"done\" to exit: ");
			stepinput = scan.nextLine();
		} while(!stepinput.equalsIgnoreCase("done"));
	}
	
}
