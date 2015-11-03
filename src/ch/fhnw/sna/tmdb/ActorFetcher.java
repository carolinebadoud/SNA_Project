package ch.fhnw.sna.tmdb;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;


public class ActorFetcher {
	
    public static void main(String[] args) throws Exception{

        URL url = new URL("http://api.themoviedb.org/3/person/popular?api_key=5164b6e30308365338b5f33533bd23d8");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        JsonReader reader = new JsonReader(new InputStreamReader((con.getInputStream())));
        List<Actor> actors = new ArrayList<Actor>();
        
        try {          
          reader.beginObject();
          
          while (reader.hasNext()) {
            String object = reader.nextName();
            if (object.equals("results")) {
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonArray jArray = parser.parse(reader).getAsJsonArray();
                for(JsonElement obj : jArray ){
                    Actor act = gson.fromJson(obj , Actor.class);
                    actors.add(act);
                }               
            } else {
                reader.skipValue();
            }
            
            for(Actor act : actors )
            {
                System.out.println(act.name);
            }
          }
        } finally {
          reader.close();
        }      
      }
}
