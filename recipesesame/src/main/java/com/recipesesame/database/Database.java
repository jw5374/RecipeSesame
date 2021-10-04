package com.recipesesame.database;

import com.recipesesame.functions.*;

public abstract class Database {
	public abstract Recipe[] getAllRecipes();
	public abstract Recipe getRecipe();
	public abstract boolean writeRecipe(Recipe recipe);
}
