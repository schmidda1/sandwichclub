package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        String mainName;
        ArrayList<String> alsoKnownAs = new ArrayList<String>();
        String placeOfOrigin;
        String description;
        String image;
        ArrayList<String> ingredients = new ArrayList<String>();
        JSONObject sandwichJson = new JSONObject(json);
        JSONObject nameJson = sandwichJson.getJSONObject("name");
        mainName = nameJson.getString("mainName");
        JSONArray alsoKnownAsJson = nameJson.getJSONArray("alsoKnownAs");
        for (int i = 0; i< alsoKnownAsJson.length(); ++i){
            String alsoKnownAsItem = alsoKnownAsJson.getString(i);
            alsoKnownAs.add(alsoKnownAsItem);
        }
        placeOfOrigin = sandwichJson.getString("placeOfOrigin");
        description = sandwichJson.getString("description");
        image = sandwichJson.getString("image");
        JSONArray ingredientsJson = sandwichJson.getJSONArray("ingredients");

        for (int i = 0; i< ingredientsJson.length(); ++i){
            String ingridItem = ingredientsJson.getString(i);
            ingredients.add(ingridItem);
        }
        Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        return sandwich;
    }
}
