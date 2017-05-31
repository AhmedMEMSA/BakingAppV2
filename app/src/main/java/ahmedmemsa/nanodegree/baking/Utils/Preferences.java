package ahmedmemsa.nanodegree.baking.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ahmedmemsa.nanodegree.baking.Pojo.Recipe;

/**
 * Created by Ahmed Magdy on 4/29/2017.
 */

public class Preferences {

    private static final String DATA = "data";


    private SharedPreferences sharedPreferences;

    public Preferences(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

    }

    public void setData(String data) {
        sharedPreferences.edit().putString(DATA, data).apply();
    }

    public String getData() {
        return sharedPreferences.getString(DATA, "");
    }

    public ArrayList<Recipe> getRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(sharedPreferences.getString(DATA, ""));
            if (array.length() < 0) {

            } else {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    Gson gson = new GsonBuilder().create();
                    Recipe recipe = gson.fromJson(jsonObject.toString(), Recipe.class);
                    recipes.add(recipe);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }
}
