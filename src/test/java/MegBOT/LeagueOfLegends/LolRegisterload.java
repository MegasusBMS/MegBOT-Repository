package MegBOT.LeagueOfLegends;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LolRegisterload {

	public LolRegisterload() {
	}

	public HashMap<Long, LolRegister> load() {
		HashMap<Long, LolRegister> hm = new HashMap<Long, LolRegister>();
		JSONParser p = new JSONParser();
		Object o;
		try {
			o = p.parse(new FileReader("Accounts.json"));
			JSONArray a = (JSONArray) o;
			for (int i = 0; i < a.size(); i++) {
				JSONObject obj = (JSONObject) a.get(i);
				long key = (long) obj.get("id");
				String name = (String) obj.get("name");
				String platform = (String) obj.get("platform");
				LolRegister lr = new LolRegister(name, platform);
				hm.put(key, lr);
			}
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hm;
	}

	public JSONArray getAcconts() {
		JSONParser p = new JSONParser();
		Object o;
		try {
			o = p.parse(new FileReader("Accounts.json"));
			JSONArray a = (JSONArray) o;
			return a;
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
