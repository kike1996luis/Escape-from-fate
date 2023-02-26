package com.me.EFF;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONDataRW {

	private static CodeGame codegame;
	private static GameClass parent;

	public JSONDataRW(CodeGame codegame, GameClass parent) {
		JSONDataRW.codegame = codegame;
		JSONDataRW.parent = parent;
	}

	@SuppressWarnings("unchecked")
	public void readJSON(String filepath) {

		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader("bin/" + filepath)) {

			Object obj = jsonParser.parse(reader);
			JSONArray mapProperties = (JSONArray) obj;
			mapProperties.forEach(tmp -> parseMapObject((JSONObject) tmp));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private static void parseMapObject(JSONObject mapProp) {

		Number number;
		JSONObject mapPropertiesObject = (JSONObject) mapProp.get("mapProperties");
		JSONObject limitObject = (JSONObject) mapProp.get("limit");
		JSONObject materialObject = (JSONObject) mapProp.get("material");
		JSONObject enemyObject = (JSONObject) mapProp.get("enemy");
		JSONObject gamedataObject = (JSONObject) mapProp.get("gamedata");
		JSONObject weaponObject = (JSONObject) mapProp.get("weapon");
		JSONObject itemObject = (JSONObject) mapProp.get("item");

		if (mapPropertiesObject != null) {
			String name = (String) mapPropertiesObject.get("mapname");
			number = (Number) mapPropertiesObject.get("width");
			int width = number.intValue();
			number = (Number) mapPropertiesObject.get("height");
			int height = number.intValue();
			String backgroundMusic = (String) mapPropertiesObject.get("backgroundMusic");
			String path = (String) mapPropertiesObject.get("path");
			String pngpath = (String) mapPropertiesObject.get("pngPath");
			String medium = "null";
			if (mapPropertiesObject.get("mediumPngPath") != null) {
				medium = (String) mapPropertiesObject.get("mediumPngPath");
			}
			number = (Number) mapPropertiesObject.get("initialX");
			float initialX = number.floatValue();
			number = (Number) mapPropertiesObject.get("initialY");
			float initialY = number.floatValue();
			number = (Number) mapPropertiesObject.get("initialAngle");
			float initialAngle = number.floatValue();
			int nextMap = 0;
			if (mapPropertiesObject.get("nextMap") != null) {
				number = (Number) mapPropertiesObject.get("nextMap");
				nextMap = number.intValue();
			}
			codegame.buildMap(name, width, height, path, pngpath, medium, initialX, initialY, initialAngle, nextMap,
					backgroundMusic);

		}
		if (limitObject != null) {

			String figure = (String) limitObject.get("figure");
			if (figure.equals("line")) {

				number = (Number) limitObject.get("x1");
				float x1 = number.floatValue();
				number = (Number) limitObject.get("x2");
				float x2 = number.floatValue();
				number = (Number) limitObject.get("y1");
				float y1 = number.floatValue();
				number = (Number) limitObject.get("y2");
				float y2 = number.floatValue();
				number = (Number) limitObject.get("goalID");
				int goalID = number.intValue();
				if(limitObject.get("isTraspassable") != null) {
					boolean isTraspassable = (boolean) limitObject.get("isTraspassable");
					codegame.buildLimit(figure, x1, y1, x2, y2, 0, 0, 0, 0, goalID, isTraspassable);
				}else {
					codegame.buildLimit(figure, x1, y1, x2, y2, 0, 0, 0, 0, goalID, false);
				}

			} else if (figure.equals("circle")) {

				number = (Number) limitObject.get("x");
				float x = number.floatValue();
				number = (Number) limitObject.get("y");
				float y = number.floatValue();
				number = (Number) limitObject.get("radius");
				float radius = number.floatValue();
				number = (Number) limitObject.get("goalID");
				int goalID = number.intValue();
				if(limitObject.get("isTraspassable") != null) {
					boolean isTraspassable = (boolean) limitObject.get("isTraspassable");
					codegame.buildLimit(figure, x, y, 0, 0, radius, 0, 0, 0, goalID, isTraspassable);
				}else {
					codegame.buildLimit(figure, x, y, 0, 0, radius, 0, 0, 0, goalID, false);
				}

			} else if (figure.equals("box")) {

				number = (Number) limitObject.get("x");
				float x = number.floatValue();
				number = (Number) limitObject.get("y");
				float y = number.floatValue();
				number = (Number) limitObject.get("sizeX");
				float sizeX = number.floatValue();
				number = (Number) limitObject.get("sizeY");
				float sizeY = number.floatValue();
				number = (Number) limitObject.get("angle");
				float angle = number.floatValue();
				number = (Number) limitObject.get("goalID");
				int goalID = number.intValue();
				if(limitObject.get("isTraspassable") != null) {
					boolean isTraspassable = (boolean) limitObject.get("isTraspassable");
					codegame.buildLimit(figure, x, y, 0, 0, 0, angle, sizeX, sizeY, goalID, isTraspassable);
				}else {
					codegame.buildLimit(figure, x, y, 0, 0, 0, angle, sizeX, sizeY, goalID, false);
				}
			}
		}
		if (materialObject != null) {

			boolean back = false;
			String color = "null";
			String name = (String) materialObject.get("name");
			number = (Number) materialObject.get("x");
			float x = number.floatValue();
			number = (Number) materialObject.get("y");
			float y = number.floatValue();
			number = (Number) materialObject.get("angle");
			float angle = number.floatValue();
			if (materialObject.get("back") != null) {
				back = (boolean) materialObject.get("back");
			}
			if (materialObject.get("color") != null) {
				color = (String) materialObject.get("color");
			}
			codegame.buildMaterial(name, x, y, angle, back, color);
		}
		if (weaponObject != null) {

			String name = (String) weaponObject.get("name");
			number = (Number) weaponObject.get("x");
			float x = number.floatValue();
			number = (Number) weaponObject.get("y");
			float y = number.floatValue();
			number = (Number) weaponObject.get("angle");
			float angle = number.floatValue();
			codegame.buildWeapon(name, x, y, angle);
		}
		if (enemyObject != null) {

			String name = (String) enemyObject.get("name");
			String weapon = (String) enemyObject.get("weapon");
			number = (Number) enemyObject.get("x");
			float x = number.floatValue();
			number = (Number) enemyObject.get("y");
			float y = number.floatValue();
			String angle = (String) enemyObject.get("angle");
			String behavior = (String) enemyObject.get("behavior"); // vertical, horizontal, quiet, box1, box2,
																	// circular1, circular2
			number = (Number) enemyObject.get("origen");
			int origen = number.intValue();
			number = (Number) enemyObject.get("destino");
			int destino = number.intValue();
			number = (Number) enemyObject.get("pasos");
			int pasos = number.intValue();
			codegame.buildEnemy(name, weapon, x, y, angle, behavior, origen, destino, pasos);
		}
		if (itemObject != null) {

			String type = (String) itemObject.get("type");
			number = (Number) itemObject.get("x");
			float x = number.floatValue();
			number = (Number) itemObject.get("y");
			float y = number.floatValue();
			number = (Number) itemObject.get("angle");
			float angle = number.floatValue();
			number = (Number) itemObject.get("goalID");
			int goalID = number.intValue();
			String text = "null";
			if (itemObject.get("text") != null) {
				text = (String) itemObject.get("text");
			}
			codegame.buildItem(type, x, y, angle, goalID, text);
		}
		if (gamedataObject != null) {

			number = (Number) gamedataObject.get("lastLevel");
			parent.readData(number.intValue());
		}
	}
}
