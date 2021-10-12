package com.recipesesame.functions;

import java.io.*;
import java.util.*;

import com.recipesesame.utils.*;

public class Recipe implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String title;
	private String subtitle;
	private ArrayList<String> tags = new ArrayList<>();

	private ArrayList<Ingredient> ingredients;
	private Quantity servingSize;
	private int prepTime;
	private int cookTime;

	private ArrayList<Step> instructions;

	private int stepNum = 0;
	public Recipe() { this.id = Utils.randomID(); }


	public Recipe(String id) {
		this.id = id;
	}

	public static Recipe fromFile(String id, File recipeFile) throws IOException, ClassNotFoundException {		
		FileInputStream fileIn = new FileInputStream(recipeFile);
		ObjectInputStream objIn = new ObjectInputStream(fileIn);

		Recipe newRecipe = (Recipe) objIn.readObject();

		objIn.close();

		return newRecipe;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return this.subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public void addTag(String tag) {
		this.tags.add(tag);
	}

	public void removeTag(int i) {
		this.tags.remove(i);
	}

	public void clearTags() {
		this.tags.clear();
	}

	public ArrayList<String> getTags() {
		return this.tags;
	}

	public ArrayList<Ingredient> getIngredients() {
		return this.ingredients;
	}

	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
	}

	public void removeIngredient(int i) {
		this.ingredients.remove(i);
	}

	public void setIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public Quantity getServingSize() {
		return this.servingSize;
	}

	public void setServingSize(Quantity servingSize) {
		this.servingSize = servingSize;
	}

	public int getPrepTime() {
		return this.prepTime;
	}

	public void setPrepTime(int prepTime) {
		this.prepTime = prepTime;
	}

	public int getCookTime() {
		return this.cookTime;
	}

	public void setCookTime(int cookTime) {
		this.cookTime = cookTime;
	}

	public ArrayList<Step> getInstructions() {
		return this.instructions;
	}

	public void setInstructions(ArrayList<Step> instructions) {
		this.instructions = instructions;
	}

	public void addInstruction(Step instruction) {
		this.instructions.add(instruction);
	}

	public void removeInstruction(int i) {
		this.instructions.remove(i);
	}

	public String displayAll() {
		String output = "";
		output += (this.id + "\n");
		output += (this.title + "\n");
		output += (this.subtitle + "\n");
		output += ("Serves: " + 
				this.servingSize + 
				"		Prep Time: " + 
				this.prepTime + 
				"		Cook Time: " + 
				this.cookTime + "\n");
		output += "Ingredients: \n";
		for(int i=0; i<this.ingredients.size(); i++){
			output += ((i + 1) + ". " + this.ingredients.get(i).toString() + "\n");
		}
		output += "Instructions: \n";
		for(int i=0; i<this.instructions.size(); i++){
			output += ((i + 1) + ". " + this.instructions.get(i).toString() + "\n");
		}
		if(Objects.nonNull(this.tags)) {
			output += "Tags: \n";
			for(int i=0; i<this.tags.size(); i++){
				output += ((i + 1) + ". " + this.tags.get(i).toString() + "\n");
			}
		}
		return output;
  }
	public void displayNextStep(){
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println(this.instructions.get(stepNum).toString());
		this.stepNum++;
		if(this.stepNum >= this.instructions.size()){
			this.stepNum = 0;
		}
	}
	public String getDisplayInfo() {
		String output = "";

		output += this.id + "\n";
		output += this.title + "\n";
		output += this.subtitle + "\n\n";
		output += "Serves: " + this.servingSize + "\n";
		output += "Time: " + (this.prepTime + this.cookTime) + " minutes\n";

		return output;

	}

	@Override
	public String toString() {
		return this.id;
	}
}
