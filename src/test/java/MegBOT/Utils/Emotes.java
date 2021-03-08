package MegBOT.Utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.JsonParser;

import MegBOT.Main;

public class Emotes {
	
	HashMap<String, String> emotes;
	
	public Emotes() {
		this.emotes = Main.emotes;
	}
	
	public Emotes(int x) {
		this.emotes = new HashMap<String, String>();
	}

	public HashMap<String, String> load() throws FileNotFoundException, IOException, ParseException {
		JSONParser p = new JSONParser();
		Object o = p.parse(new FileReader("Emotes.json"));
		JSONArray a = (JSONArray) o;
		for(int i=0;i<a.size();i++) {
			JSONObject obj = (JSONObject) a.get(i);
			String key = (String) obj.get("key");
			String emote = (String) obj.get("emote");
			emotes.put(key, emote);
		}
		return emotes;
	}

	public String getEmote(String name) {
		if(emotes.get(name)== null)
			return "<:a:"+emotes.get("NULL")+">";
		return "<:a:"+emotes.get(name)+">";
	}
	public String getEmote(int i) {
		if(emotes.get(i+"")== null)
			return "<:a:"+emotes.get("NULL")+">";
		return "<:a:"+emotes.get(i+"")+">";
	}
}
