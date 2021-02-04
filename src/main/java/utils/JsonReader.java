package utils;

import java.io.BufferedReader;
import java.io.FileReader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * <copyright file="JsonReader.java" company="Modi's Consulting Group"> Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic,
 * mechanical or otherwise, is prohibited without the prior written permission
 * of the copyright owner. </copyright>
 * 
 * @author 
 */

public class JsonReader {
	private JsonObject jObj = null;

	public JsonReader(String path) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(path));
		JsonParser parser = new JsonParser();
		jObj = parser.parse(br).getAsJsonObject();
	}

	public JsonObject getRoot() {
		return jObj;
	}
}
