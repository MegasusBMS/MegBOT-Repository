package MegBOT.Commands.Other;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import MegBOT.CommandControlCenter.CommandContext;
import MegBOT.CommandControlCenter.ICommand;
import MegBOT.Utils.EmbedTemplate;
import net.dv8tion.jda.api.EmbedBuilder;

public class MemeCommand implements ICommand{
		
	@Override
	public void handle(CommandContext ctx) {
		JSONParser parser = new JSONParser();
		String postLink = "";
		String title = "";
		String url = "";
		try {
			URL memeURL = new URL("https://meme-api.herokuapp.com/gimme");
			BufferedReader br = new BufferedReader(new InputStreamReader(memeURL.openConnection().getInputStream()));
			
			String lines;
			while ((lines = br.readLine())!=null) {
				JSONArray a = new JSONArray();
				a.add(parser.parse(lines));
				
				for(Object o : a) {
					JSONObject jo =  (JSONObject) o;
					postLink = (String) jo.get("postLink");
					title = (String) jo.get("title");
					url = (String) jo.get("url");
				}
			}
			br.close();
			EmbedTemplate et = new EmbedTemplate();
			et.RC().Footer();
			EmbedBuilder b = et.getEmbedBuilder().setTitle(title,postLink).setImage(url);
			ctx.getEvent().getChannel().sendMessage(b.build()).queue();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return "meme";
	}

	@Override
	public String getHelp() {
		return "A meme command";
	}

	@Override
	public String getAliase() {
		return getName();
	}
}
