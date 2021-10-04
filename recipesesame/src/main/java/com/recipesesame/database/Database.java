package com.recipesesame.database;

import com.recipesesame.functions.*;
import com.recipesesame.utils.*;

public abstract class Database {
	public abstract Recipe[] getAllRecipes();

	public abstract Recipe getRecipe(String id) throws RecipeNotFoundException;

	public abstract boolean writeRecipe(Recipe recipe);
}
