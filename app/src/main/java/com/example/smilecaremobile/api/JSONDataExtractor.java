package com.example.smilecaremobile.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONDataExtractor {

    public static String Extract(String toExcrat, String JSONString)  {
        try{
            JSONObject jsonObject = new JSONObject(JSONString);
            return jsonObject.getString(toExcrat);
        }
        catch (Exception e) {
            return e.toString();
        }
    }

    public static ArrayList<String> ExtractList(String toExcrat, String JSONString) {
        try{
            ArrayList<String> returnArray = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(JSONString);

            for (int i = 0; i < jsonArray.length(); i++) {
                returnArray.add(Extract(toExcrat, jsonArray.getJSONObject(i).toString()));
            }
            return returnArray;
        }
        catch(Exception e) {
            return null;
        }
    }
}