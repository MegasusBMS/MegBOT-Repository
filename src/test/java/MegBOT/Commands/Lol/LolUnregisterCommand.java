package MegBOT.Commands.Lol;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import MegBOT.Main;
import MegBOT.CommandControlCenter.CommandContext;
import MegBOT.CommandControlCenter.ICommand;
import MegBOT.LeagueOfLegends.LolRegisterload;
import MegBOT.Utils.EmbedTemplate;
import net.dv8tion.jda.api.EmbedBuilder;

public class LolUnregisterCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		EmbedTemplate et = new EmbedTemplate();
		et.RC().Footer();
		EmbedBuilder eb = et.getEmbedBuilder();
		if(!Main.accounts.containsKey(ctx.getEvent().getMember().getIdLong())) {
			eb.setTitle(":anger: You are not register on this DiscordID");
			eb.setDescription("if u want to register:\n"+new LolRegisterCommand().getHelp());
			ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
			return;
		}
		LolRegisterload lrl= new LolRegisterload();
		JSONArray a = lrl.getAcconts();
		for(int i = 0 ; i<a.size() ; i++) {
			JSONObject o = (JSONObject) a.get(i);
			if(o.get("id").equals(ctx.getEvent().getMember().getIdLong())){
				a.remove(i);
				break;
			}
		}
		try(FileWriter f = new FileWriter("Accounts.json")){
			
			f.write(a.toString());
			f.flush();
			f.close();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Main.accounts.remove(ctx.getEvent().getMember().getIdLong());
		eb.setTitle(":white_check_mark: You are no longer registered");
		ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
	}

	@Override
	public String getName() {
		return "lol unregister";
	}

	@Override
	public String getHelp() {
		return getName();
	}

	@Override
	public String getAliase() {
		return "lol unlink";
	}
	
}
