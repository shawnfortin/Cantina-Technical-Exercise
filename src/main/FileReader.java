package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FileReader {
	private File file;
	private Scanner fileIn;

	public FileReader(String filePath) {
		this.file = new File(filePath);
	}

	private JSONObject fileToJson() {
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
		JSONObject obj = fileToJson();
		if (obj != null) {
			return find(obj, selector);
		}
		return -1;
	}
	
	private int find(JSONObject obj, String selector) {
		int count = 0;
		if (obj.containsKey("contentView")) {
			count += find((JSONObject) obj.get("contentView"), selector);
		}
		
		if (obj.containsKey("control")) {
			count += find((JSONObject) obj.get("control"), selector);
		}
		
		if (obj.containsKey("subviews")) {
			for (int i = 0; i < ((JSONArray) obj.get("subviews")).size(); i++) {
				count += find((JSONObject)((JSONArray) obj.get("subviews")).get(i), selector);
			}
		}
		
		if (obj.containsKey("class")) {
			if (obj.get("class").equals(selector)) {
				System.out.println(obj);
				count += 1;
			}
		}
		
		if (obj.containsKey("identifier")) {
			if (obj.get("identifier").equals(selector)) {
				System.out.println(obj);
				count += 1;
			}
		}
		
		if (obj.containsKey("classNames")) {
			JSONArray classnames = (JSONArray) obj.get("classNames");
			for (int i = 0; i < classnames.size(); i++) {
				if (classnames.get(i).equals(selector)) {
					System.out.println(obj);
					count += 1;
				}
			}
		}
		return count;
	}
	
}
