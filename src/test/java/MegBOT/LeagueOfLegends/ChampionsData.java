package MegBOT.LeagueOfLegends;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import MegBOT.Main;


public class ChampionsData {
	
	ChampionData cd;
	
	public ChampionData getCd() {
		return cd;
	}

	public ChampionsData() {};
	
	public ChampionsData(String name) {
		cd = Main.ChampionsData.get(name);
	}
	
	public ChampionsData(int id) {
		cd = Main.ChampionsData.get(id+"");
	}
	
	
	public HashMap<String,ChampionData> load() throws MalformedURLException, ParseException {
		HashMap<String,ChampionData> cds= new HashMap<String, ChampionData>();
		URL url = new URL("http://ddragon.leagueoflegends.com/cdn/11.5.1/data/en_US/champion.json");
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput( true );
			conn.setInstanceFollowRedirects( false );
			conn.setRequestMethod( "GET" );
			conn.connect();
			
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) 
			{
				response.append(inputLine+"\n");
			}
			in.close();
			
			JSONParser jp = new JSONParser();
			Object o =jp.parse(response.toString());
			JSONObject jo = (JSONObject) o;
			
			jo=(JSONObject) jo.get("data");
			String[] names = (jo.keySet()+"").replaceAll(",","").replace("[","").replace("]", "").split(" ");
			for(String n : names) {
				JSONObject jo2;
				jo2=(JSONObject) jo.get(n);
				String version;
				String id;
				String key;
				String name;
				String title;
				String[] tags;
				String partype;
				version = (String) jo2.get("version");
				id=(String) jo2.get("id");
				key=(String) jo2.get("key");
				name=(String) jo2.get("name");
				title=(String) jo2.get("title");
				ChampionInfo info=new ChampionInfo(Integer.parseInt(""+((JSONObject)jo2.get("info")).get("attack")),
									  			   Integer.parseInt(""+((JSONObject)jo2.get("info")).get("defense")),
									  			   Integer.parseInt(""+((JSONObject)jo2.get("info")).get("magic")),
									  			   Integer.parseInt(""+((JSONObject)jo2.get("info")).get("difficulty"))
									  			   );
				tags=jo2.get("tags").toString().replaceAll("\"", "").replace("[", "").replace("]", "").split(",");
				partype=(String) jo.get("partype");
				ChampionData cd = new ChampionData(version, id, key, name, title, info, tags, partype);
				cds.put(n.toLowerCase(), cd);
				cds.put(key,cd);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cds;
	}
}
