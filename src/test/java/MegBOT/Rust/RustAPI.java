package MegBOT.Rust;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RustAPI {
	
	public List<RustServerStats> ServerStatus;
	
	public RustAPI(String link) throws IOException {
		
		ServerStatus= new ArrayList<RustServerStats>();
		ServerStatus = Search(link,ServerStatus);
		
	}

	public static List<RustServerStats> Search(String link,List<RustServerStats> rs) throws IOException {
		
		URL url = null;
		try {
			url = new URL(link);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpURLConnection conn;
		conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("GET");
		conn.connect();

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine + "\n");
		}
		in.close();

		JsonParser jp = new JsonParser();
		JsonObject jo = jp.parse(response.toString()).getAsJsonObject();
		JsonObject obj = jo.get("links").getAsJsonObject();
		JsonArray ja = jo.get("data").getAsJsonArray();
		for (int i = ja.size() - 1; i >= 0; i--) {
			jo = ja.get(i).getAsJsonObject();
			obj = jo.get("attributes").getAsJsonObject();
			String status = obj.get("status").getAsString();
			if (!(status.equals("dead") || status.equals("removed") || status.equals("invalid"))) {
				RustServerStats rss = new RustServerStats();
				
				rss.setId(jo.get("id").getAsLong());				//id
				
				rss.setName(obj.get("name").getAsString());				//name
				rss.setIp(obj.get("ip").getAsString());					//ip
				rss.setPort(obj.get("port").getAsInt());				//port
				rss.setPlayers(obj.get("players").getAsInt());			//players
				rss.setMaxPlayers(obj.get("maxPlayers").getAsInt());	//maxPlayers
				rss.setStatus(obj.get("status").getAsString());			//status
				rss.setCountry(obj.get("country").getAsString());		//country
				
				obj=obj.get("details").getAsJsonObject();				
				
				rss.setUptime(obj.get("rust_uptime").getAsLong());					//uptime
				rss.setLastWipe(obj.get("rust_last_wipe").getAsString());			//lastWipe
				rss.setModed(obj.get("rust_modded").getAsBoolean());				//moded
				rss.setDescription(obj.get("rust_description").getAsString());		//description
				rss.setOfficial(obj.get("official").getAsBoolean());				//official
				rss.setPvp(!obj.get("pve").getAsBoolean());							//pvp
				rss.setServerImageUri(obj.get("rust_headerimage").getAsString());	//serverImage
				rss.setQueue(obj.get("rust_queued_players").getAsInt());			//queue
				rss.setServerSite(obj.get("rust_url").getAsString());				//serverSite
				rss.setFps(obj.get("rust_fps").getAsFloat());						//fps
				rss.setFpsAvg(obj.get("rust_fps_avg").getAsFloat());				//fpsAvg
				
				if(obj.has("rust_maps")) {
				obj=obj.get("rust_maps").getAsJsonObject();
				//>>ERROR<<
				if(obj.has("thumbnailUrl"))
				rss.setMapImageUri(obj.get("thumbnailUrl").getAsString());	//mapImage
				rss.setMapsize(obj.get("size").getAsLong());					//mapSize
				}else {
					rss.setMapImageUri("");
					rss.setMapsize(0L);
				}
				
				rs.add(rss);
			}
		}
		return rs;
	}
}
