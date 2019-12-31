package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
	public static String fileToString(String path) {
		StringBuilder builder = new StringBuilder();
		BufferedReader reader;
		String line;
		try {
			reader = new BufferedReader(new FileReader(path));
			do {
				line = reader.readLine();
				builder.append(line + "\n");
			}while(line != null);
			reader.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return builder.toString();
	}
}
