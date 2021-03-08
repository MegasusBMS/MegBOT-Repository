package MegBOT.Commands.Lol;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import MegBOT.Main;
import MegBOT.CommandControlCenter.CommandContext;
import MegBOT.CommandControlCenter.ICommand;
import MegBOT.LeagueOfLegends.LolPlayer;
import MegBOT.LeagueOfLegends.LolRegister;
import MegBOT.LeagueOfLegends.LolRegisterload;
import MegBOT.LeagueOfLegends.RiotConnection;
import MegBOT.Utils.EmbedTemplate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.rithms.riot.api.RiotApi;

public class LolRegisterCommand implements ICommand{

	@Override
	public void handle(CommandContext ctx) {
		String arg = ctx.getEvent().getMessage().getContentRaw();
		if(arg.equalsIgnoreCase(ctx.getPrefix()+getName())||arg.equalsIgnoreCase(ctx.getPrefix()+getAliase()))
			arg = arg.replace(ctx.getPrefix()+getName()+" ","");
		else
			arg = arg.replace(ctx.getPrefix()+getAliase()+" ","");
		
		if (arg.startsWith(ctx.getPrefix()+getName()))
			arg = arg.replace(ctx.getPrefix()+getName() + " ", "");
		else
			arg = arg.replace(ctx.getPrefix()+getAliase() + " ", "");
		
		EmbedTemplate et = new EmbedTemplate();
		et.RC().Footer();
		EmbedBuilder eb = et.getEmbedBuilder();
		RiotConnection rc = new RiotConnection();
		RiotApi ra = rc.openConnection();
			LolPlayer lolp=new LolPlayer(arg,this,ra);
			if(lolp.isError()) {
				ctx.getEvent().getChannel().sendMessage(lolp.getErrorEmbed().build()).queue();
				return;
			}
			List<Long> key = new ArrayList<Long>(Main.accounts.keySet());
			if(key.contains(ctx.getEvent().getMember().getIdLong())) {
				eb.setTitle(":anger: You are already registered");
				ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
				return;
			}
			
			JSONObject list = new JSONObject();
			list.put("id",ctx.getEvent().getMember().getIdLong());
			list.put("platform", arg.split(" ")[0]);
			list.put("name", arg.replace(arg.split(" ")[0]+" ",""));
			LolRegisterload lrl = new LolRegisterload();
			JSONArray e = lrl.getAcconts();
			e.add(list);
			try(FileWriter f = new FileWriter("Accounts.json")){
				
				f.write(e.toString());
				f.flush();
				f.close();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			LolRegister lolr = new LolRegister(arg.replace(arg.split(" ")[0]+" ", ""), arg.split(" ")[0]);
			Main.accounts.put(ctx.getEvent().getMember().getIdLong(), lolr);
			eb.setTitle(":white_check_mark: Your lol account was registred on your DiscordID");
			ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
	}

	@Override
	public String getName() {
		return "lol register";
	}

	@Override
	public String getHelp() {
		return getName()+" (region) (name)";
	}

	@Override
	public String getAliase() {
		return "lol r";
	}
	
}
