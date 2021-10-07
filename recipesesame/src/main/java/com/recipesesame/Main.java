package com.recipesesame;

import java.util.Scanner;
import java.io.BufferedOutputStream;
import java.io.IOException;

import com.recipesesame.database.*;
import com.recipesesame.functions.*;

public class Main {
    public static void main( String[] args ) throws IOException, ClassNotFoundException {
        System.out.println(System.getProperty("user.dir"));
        Database database = new FileSystemDatabase("RecipeSesame/recipesesame/src/main/java/com/recipesesame/recipes");
    	
        Scanner scan = new Scanner(System.in);
        BufferedOutputStream out = new BufferedOutputStream(System.out);
        String input = "";
        while(input.equalsIgnoreCase("exit") != true) {
            out.write("Welcome to Recipe Sesame!\n".getBytes());
            out.write("1. Write a recipe           2. Search for a recipe\n".getBytes());
            out.write("3. List all current recipes\n".getBytes());
            out.write("Please enter a number or \"exit\":".getBytes());
            out.flush();
            input = scan.next();
            switch(input) {
                case "1":
                    Handlers.addRecipe(database, out, scan);
                    break;
                case "2":
                    out.write("This is 2 ".getBytes());
                    break;
                case "3":
                    Handlers.displayAllRecipes(database, out);
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
