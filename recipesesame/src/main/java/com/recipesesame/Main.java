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
        // System.out.println(System.getProperty("user.dir"));
        Scanner scan = new Scanner(System.in);
        BufferedOutputStream out = new BufferedOutputStream(System.out);

        System.out.print("Your recipe folder path: ");
        String filePath = scan.nextLine();
        Database database = new FileSystemDatabase(filePath);

        ArrayList<Recipe> recipes = database.getAllRecipes();

        String input = "";
        while(input.equalsIgnoreCase("exit") != true) {
            out.write("\nWelcome to Recipe Sesame!\n".getBytes());
            out.write("1. Write a recipe              2. Search for a recipe\n".getBytes());
            out.write("3. List all current recipes    4. Get random recipe\n".getBytes());
            out.write("Please enter a number or \"exit\": ".getBytes());
            out.flush();
            input = scan.next();
            switch(input) {
                case "1":
                    Handlers.addRecipe(database, out, scan);
                    recipes = database.getAllRecipes();
                    break;
                case "2":
                    out.write("\nSearch for: ".getBytes());
                    out.flush();
                    scan.nextLine();
                	String searchKey = scan.nextLine();
                    Handlers.searchAllRecipes(database, searchKey, out);
                	Handlers.exploreRecipes(database, scan);
                    break;
                case "3":
                    Handlers.displayAllRecipes(database, recipes, out, scan);
                    Handlers.exploreRecipes(database, scan);
                    break;
                case "4":
                    Handlers.getRandomRecipe(database, recipes);
                    Handlers.exploreRecipes(database, scan);
                    break;
                case "exit":
                    out.write("\nGoodbye for now!\n\n".getBytes());
                    out.flush();
                    break;
                default:
                    out.write("Unrecognized command\n".getBytes());
            }

        }
        scan.close();
        out.close();
    }
}
