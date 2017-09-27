package com.sonartrading.fxclient.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;
import javax.json.stream.JsonParsingException;

/**
 * The JsonUtil class provides JSON utility operations.
 * 
 */
public class JsonUtil {

	public static final String STATUS_CODE_FAILURE = "200";
	public static final String STATUS_CODE_BUSINESS_VALIDATION_ERROR = "201";
	public static final String STATUS_CODE_EXCEPTION = "202";
	public static final String STATUS_CODE_REQUEST_VALIDATION_ERROR = "203";

	/**
	 * Default private constructor.
	 */
	private JsonUtil() {

	}

	/**
	 * The getFieldValue() method is used to get string value from json object
	 * 
	 * @param json
	 *            Specifies the JsonObject instance
	 * @param fieldName
	 *            Specifies the field name
	 * @return Return field value of given field name
	 */
	public static String getFieldValue(JsonObject json, String fieldName) {
		
		String result = null;
		
		try {
			result = json.getString(fieldName);
			
			if(ApplicationUtil.isNotNullAndEmpty(result)) {
				result = result.trim();
			}
			
		} catch (Exception e) {
			System.out.println("Invalid Field Value for " + fieldName);
		}
		
		return result;
	}

	/**
	 * The getFieldValue() method is used to get integer value from json object
	 * 
	 * @param json
	 *            Specifies the JsonObject instance
	 * @param fieldName
	 *            Specifies the field name
	 * @return Return field value of given field name
	 */
	public static Integer getIntegerFieldValue(JsonObject json, String fieldName) {
		
		Integer result = null;
		
		try {
			result = json.getInt(fieldName);
			
		} catch (Exception e) {
			System.out.println("Invalid Field Value for " + fieldName);
		}
		
		return result;
	}

	/**
	 * The getFieldValue() method is used to get double value from json object
	 * 
	 * @param json
	 *            Specifies the JsonObject instance
	 * @param fieldName
	 *            Specifies the field name
	 * @return Return field value of given field name
	 */
	public static Double getDoubleFieldValue(JsonObject json, String fieldName) {
		
		Double result = null;
		
		try {
			result = json.getJsonNumber(fieldName).doubleValue();
			
		} catch (Exception e) {
			System.out.println("Invalid Field Value for " + fieldName);
		}
		
		return result;
	}
	
	public static Long getLongFieldValue(JsonObject json, String fieldName) {
		
		Long result = null;
		
		try {
			result = json.getJsonNumber(fieldName).longValue();
			
		} catch (Exception e) {
			System.out.println("Invalid Field Value for " + fieldName);
		}
		
		return result;
	}

	/**
	 * The getBooleanFieldValue() method is used to get boolean value from json
	 * object
	 * 
	 * @param json
	 *            Specifies the JsonObject instance
	 * @param fieldName
	 *            Specifies the field name
	 * @return Return field value of given field name
	 */
	public static Boolean getBooleanFieldValue(JsonObject json, String fieldName) {
		
		Boolean result = null;
		
		try {
			result = json.getBoolean(fieldName);
			
		} catch (Exception e) {
			System.out.println("Invalid Field Value for " + fieldName);
			
		}
		return result;
	}


	/**
	 * The getJsonValueFromJsonString() method is used to get json value of
	 * given key from given json detail string
	 * 
	 * @param json
	 *            Specifies the json detail string
	 * @param key
	 *            Specifies the key name of json
	 * @return Return value of given json key
	 */
	public static String getJsonValueFromJsonString(String json, String key) {
		

		JsonObject jsonObject = Json.createReader(new StringReader(json)).readObject();
		String detailString = null;

		if (jsonObject.containsKey(key) && !jsonObject.isNull(key)) {

			JsonObject detail = jsonObject.getJsonObject(key);

			if (detail != null) {
				detailString = detail.toString();
			}
		}

		System.out.println("getJsonValueFromJsonString() method ends");

		return detailString;
	}

	/**
	 * The getJsonArrayFromJsonString() method is used to get json array from
	 * given json detail string
	 * 
	 * @param json
	 *            Specifies the json detail string
	 * @param key
	 *            Specifies the key name of json
	 * @return Return array of given json key
	 */
	public static String[] getJsonArrayFromJsonString(String json, String key) {
		
		JsonObject jsonObject = Json.createReader(new StringReader(json)).readObject();

		String[] jsonStringArray = null;

		if (jsonObject.containsKey(key) && !jsonObject.isNull(key)) {

			JsonArray jsonArray = jsonObject.getJsonArray(key);

			if (jsonArray != null) {
				jsonStringArray = new String[jsonArray.size()];

				for (int i = 0; i < jsonArray.size(); i++) {
					
					jsonStringArray[i] = jsonArray.getJsonObject(i).toString();
				}
			}
		}

		System.out.println("getJsonArrayFromJsonString() method ends");

		return jsonStringArray;
	}

	/**
	 * The getStringFieldValueFromJson() method is used to get string value from
	 * json string
	 * 
	 * @param json
	 *            Specifies the json string
	 * @param fieldName
	 *            Specifies the field name
	 * @return Return field value of given field name
	 */
	public static String getStringFieldValueFromJson(String json, String fieldName) {
		
		JsonObject jsonObject = Json.createReader(new StringReader(json)).readObject();
		String result = getFieldValue(jsonObject, fieldName);

		return result;
	}

	/**
	 * The getBooleanFieldValueFromJson() method is used to get boolean value
	 * from json string
	 * 
	 * @param json
	 *            Specifies the json string
	 * @param fieldName
	 *            Specifies the field name
	 * @return Return boolean field value of given field name
	 */
	public static Boolean getBooleanFieldValueFromJson(String json, String fieldName) {
		
		JsonObject jsonObject = Json.createReader(new StringReader(json)).readObject();
		Boolean result = getBooleanFieldValue(jsonObject, fieldName);

		return result;
	}

	
	/**
	 * The getIntegerFieldValueFromString() method is used to get integer value from json object
	 * 
	 * @param json
	 *            Specifies the JsonObject instance
	 * @param fieldName
	 *            Specifies the field name
	 * @return Return field value of given field name
	 */
	// TODO: We need to merge getIntegerFieldValueFromString and getIntegerFieldValue
	public static Integer getIntegerFieldValueFromString(JsonObject json, String fieldName) {
		
		Integer result = null;
		
		try {
			JsonValue jsonValue = json.get(fieldName);
			if(jsonValue != null) {
			
				ValueType valueType = jsonValue.getValueType();
				
				if(valueType == ValueType.STRING) {
					result = Integer.parseInt(json.getString(fieldName));
				} 
				else if(valueType == ValueType.NUMBER) {
					result = json.getInt(fieldName);
				}
			}
		} catch (Exception e) {
			System.out.println("Invalid Field Value for " + fieldName);
		}
		
		return result;
	}
	
	public static Double getDoubleFieldValueFromString(JsonObject json, String fieldName) {
		
		Double result = null;
		
		try {
			JsonValue jsonValue = json.get(fieldName);
			if(jsonValue != null) {
			
				ValueType valueType = jsonValue.getValueType();
				
				if(valueType == ValueType.STRING) {
					result = Double.parseDouble(json.getString(fieldName));
				} 
				else if(valueType == ValueType.NUMBER) {
					result = json.getJsonNumber(fieldName).doubleValue();
				}
			}
		} catch (Exception e) {
			System.out.println("Invalid Field Value for " + fieldName);
		}
		
		return result;
	}
	
	public static Boolean isJSONValid (String json) {
    	try {
			Json.createReader(new StringReader(json)).readObject();
			return true;
		} catch (JsonParsingException e) {
			return false;
		}
    }

	public static JsonObjectBuilder getJsonObjectBuilderFromJsonObject(JsonObject jsonObject) {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		for (Entry<String, JsonValue> entry : jsonObject.entrySet()) {
			builder.add(entry.getKey(), entry.getValue());
		}
		return builder;
	}

	public static List<String> fromStringArray(String json) {
		
		JsonArray inputArray = Json.createReader(new StringReader(json)).readArray();
		List<String> result = new ArrayList<String>();

		for (int i = 0; i < inputArray.size(); i++) {
			result.add(inputArray.getString(i));
		}
		return result;
	}
	
	/**
	 * The getJsonArrayFromJsonObject() method is used to get json array from
	 * given json object
	 * 
	 * @param json
	 *            Specifies the json object
	 * @param key
	 *            Specifies the key name of json
	 * @return Return array of given json key
	 */
	public static JsonArray getJsonArrayFromJsonObject(JsonObject request, String key) {
		JsonArray jsonArray = null;
		if (request.containsKey(key)) {
			jsonArray = request.getJsonArray(key);
		}

		return jsonArray;
	}
}
