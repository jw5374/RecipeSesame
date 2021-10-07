package com.recipesesame.functions;

import java.io.Serializable;

// import java.util.*;

public class Step implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String step;
	// private ArrayList<Ingredient> usedIngredients;
	
	public Step(String step) {
		this.step = step;
	}

	public String getStep() {
		return this.step;
	}

	// public ArrayList<Ingredient> getUsedIngredients() {
	// 	return this.usedIngredients;
	// }
	
	@Override
	public String toString() {
		return this.step;
	}
}
