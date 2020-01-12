/*
 * Name: Sujoy Deb Nath
 * Date Last Editied: January 13, 2020
 * Description: This class is used for utilities such as turning a text file into a string.
 */
package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {

	/*
	 * pre: none
	 * post: returns a text file as a string
	 * Description: This method reads a text file and returns all the contents as a string object. This class is used to load the world onto the screen
	 */
	public static String fileToString(String path) {		//take in a string path
		StringBuilder builder = new StringBuilder();		//create a new string builder
		BufferedReader reader;			//make a buffered reader
		String line;			//make a string
		try {
			reader = new BufferedReader(new FileReader(path));		//try to read each line and add it to the builder, ending with a new line statement
			do {
				line = reader.readLine();
				builder.append(line + "\n");
			}while(line != null);			//continue reading until we reach the end of the text file (no line).
			reader.close();		//close the reader
		}
		catch(IOException e){
			e.printStackTrace();		//print the error statement
		}
		return builder.toString();			//return the builder object as a string
	}
}
