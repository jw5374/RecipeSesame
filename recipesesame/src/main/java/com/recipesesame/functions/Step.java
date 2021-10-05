package com.recipesesame.functions;

import java.util.*;

public class Step {
	private String step;
	private ArrayList<Ingredient> usedIngredients;
	
	public Step(String step, ArrayList<Ingredient> usedIngredients) {
		this.step = step;
		this.usedIngredients = usedIngredients;
	}

	public String getStep() {
		return this.step;
	}

	public ArrayList<Ingredient> getUsedIngredients() {
		return this.usedIngredients;
	}
	
	@Override
	public String toString() {
		return this.step;
	}
}
