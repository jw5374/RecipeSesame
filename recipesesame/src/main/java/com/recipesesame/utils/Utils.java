package com.recipesesame.utils;

import java.nio.charset.Charset;
import java.util.Random;

public class Utils {
	public static String randomID() {
		// max length of 10
		byte[] array = new byte[10];
	    new Random().nextBytes(array);
	    String generatedString = new String(array, Charset.forName("UTF-8"));
	    
	    return generatedString;
	}
}
