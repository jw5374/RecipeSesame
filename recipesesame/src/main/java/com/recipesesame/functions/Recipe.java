package com.recipesesame.functions;

import java.io.*;
import java.util.*;

import com.recipesesame.utils.*;

public class Recipe {
	private String id;

	private String title;
	private String subtitle;

	private ArrayList<Ingredient> ingredients;
	private Quantity servingSize;
	private int prepTime;
	private int cookTime;

	private ArrayList<Step> instructions;
	private int stepNum = 0;
	public Recipe() {
		this.id = Utils.randomID();
	}

	public Recipe(String id) {
		this.id = id;
	}

	public static Recipe fromFile(String id, File recipeFile) {
		Recipe newRecipe = new Recipe(id);

		// parse recipe here

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

	public ArrayList<Ingredient> getIngredients() {
		return this.ingredients;
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

	public void displayAll(){
		System.out.println(this.title);
		System.out.println();
		System.out.println(this.subtitle);
		System.out.println();
		System.out.println("Serves: "+this.servingSize+"		Prep Time: "+this.prepTime+"		Cook Time: "+this.cookTime);
		System.out.println();
		System.out.println("Ingredients:");
		for(int i=0; i<this.ingredients.size(); i++){
			System.out.println(this.ingredients.get(i).toString()+',');
		}
		System.out.println();
		System.out.println("Instructions: ");
		for(int i=0; i<this.instructions.size(); i++){
			System.out.println((i+1)+ ". " + this.instructions.get(i).toString());
		}
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
		output += "Yields: " + this.servingSize + "\n";
		output += "Time: " + (this.prepTime + this.cookTime) + " minutes\n";

		return output;

	}

	@Override
	public String toString() {
		return this.id;
	}
}
