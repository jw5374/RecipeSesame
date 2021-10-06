package com.recipesesame;

import java.util.Scanner;
import java.io.BufferedOutputStream;
import java.io.IOException;

import com.recipesesame.database.*;
import com.recipesesame.functions.*;

public class Main {
    public static void main( String[] args ) throws IOException {
        Database database = new FileSystemDatabase("src/main/java/com/recipesesame/recipes");
    	
        Scanner scan = new Scanner(System.in);
        BufferedOutputStream out = new BufferedOutputStream(System.out);
        out.write("Welcome to Recipe Sesame!\n".getBytes());
        out.write("1. Write a recipe           2. Search for a recipe\n".getBytes());
        out.write("3. List all current recipes\n".getBytes());
        out.write("Please enter a number or \"exit\":".getBytes());
        out.flush();
        String input = scan.next();
        while(input.equalsIgnoreCase("exit") != true) {
            switch(input) {
                case "1":
                    out.write("This is 1 ".getBytes());
                    break;
                case "2":
                    out.write("This is 2 ".getBytes());
                    break;
                case "3":
                    out.write(Handlers.displayAllRecipes(database).getBytes());
                    break;
                default:
                    out.write("Unrecognized command".getBytes());
            }
            out.flush();
            input = scan.next();

        }
        scan.close();
        out.close();
    }
}
