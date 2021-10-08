package com.recipesesame.database;

import java.io.IOException;
import java.util.*;

import com.recipesesame.functions.*;
import com.recipesesame.utils.*;

public abstract class Database {
	public abstract ArrayList<Recipe> getAllRecipes() throws ClassNotFoundException, IOException;

	public abstract Recipe getRecipe(String id) throws RecipeNotFoundException, ClassNotFoundException, IOException;

	public abstract boolean writeRecipe(Recipe recipe);
}
