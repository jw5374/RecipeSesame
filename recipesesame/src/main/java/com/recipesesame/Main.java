package com.recipesesame;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedOutputStream;
import java.io.IOException;

import com.recipesesame.database.*;
import com.recipesesame.functions.*;
import com.recipesesame.utils.RecipeNotFoundException;

public class Main {
    public static void main( String[] args ) throws IOException, ClassNotFoundException, RecipeNotFoundException {
        System.out.println(System.getProperty("user.dir"));
        Database database = new FileSystemDatabase("RecipeSesame/recipesesame/src/main/java/com/recipesesame/recipes");
        ArrayList<Recipe> recipes = database.getAllRecipes();
    	
        Scanner scan = new Scanner(System.in);
        BufferedOutputStream out = new BufferedOutputStream(System.out);
        String input = "";
        while(input.equalsIgnoreCase("exit") != true) {
            out.write("Welcome to Recipe Sesame!\n".getBytes());
            out.write("1. Write a recipe           2. Search for a recipe\n".getBytes());
            out.write("3. List all current recipes\n".getBytes());
            out.write("Please enter a number or \"exit\": ".getBytes());
            out.flush();
            input = scan.next();
            switch(input) {
                case "1":
                    Handlers.addRecipe(database, out, scan);
                    recipes = database.getAllRecipes();
                    break;
                case "2":
                    scan.nextLine();
                	String searchKey = scan.nextLine();
                    Handlers.searchAllRecipes(database, searchKey, out);
                	Handlers.exploreRecipes(database, scan);
                    break;
                case "3":
                    Handlers.displayAllRecipes(database, recipes, out, scan);
                    Handlers.exploreRecipes(database, scan);
                    break;
                case "exit":
                    break;
                default:
                    out.write("Unrecognized command\n".getBytes());
            }

        }
        scan.close();
        out.close();
    }
}
