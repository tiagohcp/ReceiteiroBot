import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;


public class Connection {
	
	public static List<Recipe> getDataRecipe(String ingrediente) throws JSONException {

        final StringBuilder result = new StringBuilder();

        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL("http://food2fork.com/api/search?key=4fe184badd0c478ebe05f532ba06fb8b&q=" + ingrediente);

            urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            int data = isw.read();

            int x = 0;
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                if (current == '['){
                	x = 1;
                }
                if (x != 0){ 
                	result.append(current);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        List<Recipe> finalResult = generateJSONRecipe(new JSONArray(result.toString()));

        return finalResult;
    }

    
    public static List<Recipe> generateJSONRecipe(JSONArray json){

        List<Recipe> found = new LinkedList<Recipe>();
        try {

            for (int i = 0; i < json.length(); i++) {
                JSONObject obj = json.getJSONObject(i);
                Dados dados =  new Dados(obj.getString("publisher"),obj.getString("title"),obj.getDouble("social_rank"),obj.getString("recipe_id"),obj.getString("image_url"));
            	Recipe recipe = new Recipe(dados);
                found.add(recipe);

            }

        } catch (JSONException e) {
            // handle exception
        }
        
        return found;

    }
	
	public static List<Ingredient> getDataIngredient(String id) throws JSONException {

        final StringBuilder result = new StringBuilder();

        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL("http://food2fork.com/api/get?key=4fe184badd0c478ebe05f532ba06fb8b&rId=" + id);

            urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            int data = isw.read();

            int x = 0;
            int y = 0;
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                if (current == '{'){
                	y++;
                	if(y == 2){
                    	result.append('[');
                    	x = 1;
                    }
                } 
                if (current == '}'){
                	if(x == 1){
                		result.append('}');
	                	result.append(']');
	                	x = 0;
	                	continue;
                	}
                	else{
                		continue;
                	}
                }
                if (x != 0){ 
                	result.append(current);
                }                
            }
            if (result.length()==0){
            	result.append("[]");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<Ingredient> finalResult = generateJSONIngredient(new JSONArray(result.toString()));

        return finalResult;
    }

    
    public static List<Ingredient> generateJSONIngredient(JSONArray json){
    	List<Ingredient> found = new LinkedList<Ingredient>();

        try {
        	
        	JSONObject obj = json.getJSONObject(0);
        	Dados dados =  new Dados(obj.getString("publisher"),obj.getString("title"),obj.getDouble("social_rank"),obj.getString("recipe_id"),obj.getString("image_url"));
        	Ingredient ingredient = new Ingredient(dados,obj.getJSONArray("ingredients").toString(),obj.getString("source_url"));
        	found.add(ingredient);
        	
        } catch (JSONException e) {
            // handle exception

        }
        return found;

    }
    
}
