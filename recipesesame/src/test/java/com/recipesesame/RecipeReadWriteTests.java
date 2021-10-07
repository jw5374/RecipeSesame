package com.recipesesame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import com.recipesesame.database.*;
import com.recipesesame.functions.*;
import com.recipesesame.utils.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class RecipeReadWriteTests {

    @Test
    public void testInOut() throws FileNotFoundException, RecipeNotFoundException {
        Database database = new FileSystemDatabase("recipesesame/src/main/java/com/recipesesame/recipes");

        Recipe r = new Recipe();
        r.setCookTime(5);
        r.setTitle("Bacon Egg and Cheese");
        r.setSubtitle("Oh Yeah");

        database.writeRecipe(r);

        Recipe newR = database.getRecipe(r.getId());

        assertEquals(r.getId(), newR.getId());
    }

    @Test
    public void getAll() throws FileNotFoundException, RecipeNotFoundException {
        Database database = new FileSystemDatabase("recipesesame/src/main/java/com/recipesesame/recipes");

        ArrayList<Recipe> recipes = database.getAllRecipes();

        for (Recipe r : recipes) r.getDisplayInfo();
    }
}
