package com.recipesesame.functions;

import java.io.File;

import com.recipesesame.utils.*;

public class Recipe {
	private String id;
	
	public Recipe() {
		this.id = Utils.randomID();
	}
	
	public Recipe(String id) {
		this.id = id;
	}
	
	public String getID() {
		return this.id;
	}
	
    public static Recipe fromFile(String id, File recipeFile) {
    	Recipe newRecipe = new Recipe(id);
    	
    	return newRecipe;
    }
    
    @Override
    public String toString() {
    	return this.id;
    }
}
