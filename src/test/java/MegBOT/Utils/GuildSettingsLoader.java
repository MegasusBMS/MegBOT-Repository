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

public class GuildSettingsLoader {

	public void save(GuildSettings gs) {
		JSONObject list = new JSONObject();
		JSONArray roles = new JSONArray();
		for (Role r : gs.DjRoles)
			roles.add(r.getId());
		list.put("GuildID", gs.GuildID);
		list.put("DjRoles", roles);
		JSONArray e = read();
		if (e.size() > 0) {
			for (int i = 0; i < e.size(); i++) {
				JSONObject obj = (JSONObject) e.get(i);
				Long id = gs.GuildID;
				if (((Long) obj.get("GuildID")).equals(id)) {
					e.remove(i);
					break;
				}
			}
		}
		e.add(list);
		try (FileWriter f = new FileWriter("GuildsSettings.json")) {

			f.write(e.toString());
			f.flush();
			f.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public boolean verifyGuildIn(Long id) {
		return Main.jda.getGuilds().contains(Main.jda.getGuildById(id));
	}

	public HashMap<Long, GuildSettings> load() {
		JSONArray a = read();
		HashMap<Long, GuildSettings> gs = new HashMap<Long, GuildSettings>();
		boolean ok = true;

		for (int i = 0; i < a.size(); i++) {
			JSONObject obj = (JSONObject) a.get(i);
			long GuildID = (long) obj.get("GuildID");
			JSONArray Roles = (JSONArray) obj.get("DjRoles");
			if (verifyGuildIn(GuildID)) {
				List<Role> DjRoles = new ArrayList<>();
				for (int j = 0; j < Roles.size(); j++) {
					Role Role = Main.jda.getRoleById((String) Roles.get(j));
					DjRoles.add(Role);
				}
				GuildSettings guilds = new GuildSettings(DjRoles, GuildID);
				gs.put(GuildID, guilds);
			} else {
				a.remove(i);
				ok = false;
			}
		}
		if (!ok)
			try (FileWriter f = new FileWriter("GuildsSettings.json")) {

				f.write(a.toString());
				f.flush();
				f.close();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		return gs;
	}

	private JSONArray read() {
		JSONParser p = new JSONParser();
		Object o;
		try {
			o = p.parse(new FileReader("GuildsSettings.json"));
			JSONArray a = (JSONArray) o;
			return a;
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JSONArray();
	}
}
