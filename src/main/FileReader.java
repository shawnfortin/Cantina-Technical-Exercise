package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FileReader {
	private File file;
	private Scanner fileIn;

	public FileReader(String filePath) {
		this.file = new File(filePath);
	}

	private JSONObject fileToJson() {
		// Read the file and create a JSON object from its contents
		try {
			fileIn = new Scanner(file);
			JSONParser parser = new JSONParser();
			String fileText = fileIn.useDelimiter("\\A").next();
			JSONObject obj = (JSONObject) parser.parse(fileText);
			return obj;
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			return null;
		} catch (ParseException e) {
			System.out.println("Could not parse file");
			return null;
		}
	}
	
	public int find(String selector) {
		// Find all instances of a given selector
		JSONObject obj = fileToJson();
		if (obj != null) {
			return find(obj, selector);
		}
		return -1;
	}
	
	private int find(JSONObject obj, String selector) {
		// Recursively search the given JSONObject for a given selector and print all found instances
		int count = 0;
		if (obj.containsKey("contentView")) {
			// find selector within the contentView
			count += find((JSONObject) obj.get("contentView"), selector);
		}
		
		if (obj.containsKey("control")) {
			// find selector within the control view
			count += find((JSONObject) obj.get("control"), selector);
		}
		
		if (obj.containsKey("subviews")) {
			for (int i = 0; i < ((JSONArray) obj.get("subviews")).size(); i++) {
				// find the selector in any subviews
				count += find((JSONObject)((JSONArray) obj.get("subviews")).get(i), selector);
			}
		}
		
		// print all found instances of the selector
		
		if (obj.containsKey("class")) {
			if (obj.get("class").equals(selector)) {
				Gson g = new GsonBuilder().setPrettyPrinting().create();
				System.out.println(g.toJson(obj));
				count += 1;
			}
		}
		
		if (obj.containsKey("identifier")) {
			if (obj.get("identifier").equals(selector)) {
				Gson g = new GsonBuilder().setPrettyPrinting().create();
				System.out.println(g.toJson(obj));
				count += 1;
			}
		}
		
		if (obj.containsKey("classNames")) {
			JSONArray classnames = (JSONArray) obj.get("classNames");
			for (int i = 0; i < classnames.size(); i++) {
				if (classnames.get(i).equals(selector)) {
					Gson g = new GsonBuilder().setPrettyPrinting().create();
					System.out.println(g.toJson(obj));
					count += 1;
				}
			}
		}
		return count;
	}
	
}
