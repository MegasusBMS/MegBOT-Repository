package MegBOT.Utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import MegBOT.Main;
import net.dv8tion.jda.api.entities.Role;

public class PrefixSettingsLoader {

	public void save(PrefixSettings ps) {
		JSONObject list = new JSONObject();
		JSONArray roles = new JSONArray();
		list.put("GuildID", ps.GuildID);
		list.put("Prefix", ps.Prefix);
		JSONArray e = read();
		if (e.size() > 0) {
			for (int i = 0; i < e.size(); i++) {
				JSONObject obj = (JSONObject) e.get(i);
				Long id = ps.GuildID;
				if (((Long) obj.get("GuildID")).equals(id)) {
				 	e.remove(i);
					break;
				}
			}
		}
		e.add(list);
		try (FileWriter f = new FileWriter("GuildsPrefix.json")) {

			f.write(e.toString());
			f.flush();
			f.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public HashMap<Long, PrefixSettings> load() {
		JSONArray a = read();
		HashMap<Long, PrefixSettings> ps = new HashMap<Long, PrefixSettings>();
		
		for (int i = 0; i < a.size(); i++) {
			JSONObject obj = (JSONObject) a.get(i);
			long GuildID = (long) obj.get("GuildID");
			String Prefix = (String) obj.get("Prefix");
			PrefixSettings guilds = new PrefixSettings(GuildID,Prefix);
			ps.put(GuildID, guilds);
		}
		return ps;
	}

	private JSONArray read() {
		JSONParser p = new JSONParser();
		Object o;
		try {
			o = p.parse(new FileReader("GuildsPrefix.json"));
			JSONArray a = (JSONArray) o;
			return a;
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JSONArray();
	}
	
}
