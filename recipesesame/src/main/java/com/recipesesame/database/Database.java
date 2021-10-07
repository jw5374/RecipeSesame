package com.recipesesame.database;

import java.util.*;

import com.recipesesame.functions.*;
import com.recipesesame.utils.*;

public abstract class Database {
	public abstract ArrayList<Recipe> getAllRecipes() throws RecipeNotFoundException;

	public abstract Recipe getRecipe(String id) throws RecipeNotFoundException;

	public abstract boolean writeRecipe(Recipe recipe);
}
