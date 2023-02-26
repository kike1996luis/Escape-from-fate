package com.me.aTester;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONDataReader {
	
	private static Code code;
	
	public JSONDataReader (Code code) {
		JSONDataReader.code = code;
	}
	
	@SuppressWarnings("unchecked")
	public void readJSON(String filepath) {

		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader("bin/com/" + filepath)) {

			Object obj = jsonParser.parse(reader);
			JSONArray mapProperties = (JSONArray) obj;
			mapProperties.forEach(tmp -> parseDataObject((JSONObject) tmp));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private static void parseDataObject(JSONObject dataObject) {
		
		Number number;
		JSONObject layerObject = (JSONObject) dataObject.get("layer");
		if(layerObject != null) {
			
			String path = (String) layerObject.get("path");
			
			number = (Number) layerObject.get("x");
			float x = number.floatValue();
			
			number = (Number) layerObject.get("y");
			float y = number.floatValue();
			
			number = (Number) layerObject.get("angle");
			float angle = number.floatValue();
			
			number = (Number) layerObject.get("height");
			float height = number.floatValue();
			
			number = (Number) layerObject.get("width");
			float width = number.floatValue();
			
			number = (Number) layerObject.get("split");
			int split = number.intValue();
			
			number = (Number) layerObject.get("speed");
			float speed = number.floatValue();
			
			code.addLayer(path, x, y, angle, height, width, split, speed);
		}
	}
}
