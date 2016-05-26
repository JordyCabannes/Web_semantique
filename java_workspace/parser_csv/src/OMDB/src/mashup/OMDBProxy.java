package OMDB.src.mashup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;



import org.json.JSONObject;

public class OMDBProxy {

	private String baseUrl = "http://www.omdbapi.com/?type=movie&y=&plot=short&r=json&t=";
	public OMDBProxy()
	{
		
	}
	
	
	public HashMap<String, String> getMovieInfos(String movieTitle)
	{ //permet pour un titre de film de récupérer un hachage contenant les couples (propriété du film / valeur) retournés par OMDB
		HashMap<String, String> ret = new HashMap<>();
		
		 URL url;
	      HttpURLConnection conn;
	      BufferedReader rd;
	      String line;
	      String result = "";
	      try {
	         url = new URL(this.baseUrl+URLEncoder.encode(movieTitle, "UTF-8"));
	         conn = (HttpURLConnection) url.openConnection();
	         conn.setRequestMethod("GET");
	         rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	         
	        
	         
	         while ((line = rd.readLine()) != null) {
	            result += line;
	         }
	         rd.close();
	     System.out.println(url);
         JSONObject obj = new JSONObject(result);
         Iterator<String> iterator = obj.keys();
         while(iterator.hasNext())
         {
        	 String key = iterator.next();
        	 String val = obj.getString(key);
        	 ret.put(key, val);
         }
	         
	         
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		
		return ret;
	}
	
}
