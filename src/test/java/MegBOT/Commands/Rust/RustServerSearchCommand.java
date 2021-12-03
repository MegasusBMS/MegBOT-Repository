package MegBOT.Commands.Rust;

import java.io.IOException;
import java.util.List;

import MegBOT.Main;
import MegBOT.CommandControlCenter.CommandContext;
import MegBOT.CommandControlCenter.ICommand;
import MegBOT.Rust.RustAPI;
import MegBOT.Rust.RustNavigatorReactElement;
import MegBOT.Rust.RustServerStats;
import MegBOT.Utils.EmbedTemplate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;

public class RustServerSearchCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		String[] args = ctx.getEvent().getMessage().getContentRaw().toLowerCase().split(" ");
		RustAPI rust = null;
		
		EmbedBuilder eb = new EmbedTemplate().Footer().RC().getEmbedBuilder();
		
		if(args.length<=1) {
			eb.setTitle(":anger: Provide arguments!");
			eb.setDescription(getHelp());
			ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
			return;
		}
		
		switch(args[1]) {
		case "ison":
		case "isonline":
		case "online":
		case "o":
		case "on":
			try {
				if(args.length<=2) {
					eb.setTitle(":anger: Provide arguments!");
					eb.setDescription(getHelp());
					ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
					return;
				}
				String arg=args[2];
				for(int i = 3; i<args.length;i++) {
					arg += "_" + args[i];
				}
				rust = new RustAPI("https://api.battlemetrics.com/servers?page[size]=100&sort=rank&filter[game]=rust&filter[status]=online&filter[search]="+arg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "players":
		case "player":
		case "byplayers":
		case "p":
			try {
				if(args.length<=2) {
					eb.setTitle(":anger: Provide arguments!");
					eb.setDescription(getHelp());
					ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
					return;
				}
				String arg=args[2];
				for(int i = 3; i<args.length;i++) {
					arg += "_" + args[i];
				}
				
				rust = new RustAPI("https://api.battlemetrics.com/servers?page[size]=100&filter[game]=rust&sort=players&filter[status]=online&filter[search]="+arg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			try {
				String arg=args[1];
				for(int i = 2; i<args.length;i++) {
					arg += "_" + args[i];
				}
				
				rust = new RustAPI("https://api.battlemetrics.com/servers?page[size]=100&sort=rank&filter[game]=rust&filter[search]="+arg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		List<RustServerStats> rs = rust.ServerStatus;
		if(rs.size()==0) {
			eb.setTitle(":cry: **No Server Found**");
			ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
			return;
		}
		eb.setTitle("<:rust:825669303739088917> **RustServers:**");
		for(int i = 1; i<=Math.min(5,rs.size());i++) {
			eb.appendDescription("**"+(i)+") :flag_"+rs.get(i-1).country.toLowerCase()+": "+rs.get(i-1).name+"**\n");
			eb.appendDescription("**Players:**"+"`"+rs.get(i-1).players+"/"+rs.get(i-1).maxPlayers+"`\n**IP:** `"+rs.get(i-1).ip+":"+rs.get(i-1).port+"`\n\n");
		}
		eb.appendDescription("1/"+ (rs.size()%5==0 ? rs.size()/5 : rs.size()/5+1));
		
		ctx.getEvent().getChannel().sendMessage(eb.build()).queue((message) -> {
			
			message.addReaction("1️⃣").queue();
			message.addReaction("2️⃣").queue();
			message.addReaction("3️⃣").queue();
			message.addReaction("4️⃣").queue();
			message.addReaction("5️⃣").queue();
			message.addReaction("⏪").queue();
			message.addReaction("⏩").queue();
			if(Main.Nav.containsKey(ctx.getGuild().getIdLong()))
			Main.Nav.remove(ctx.getGuild().getIdLong());
		    Main.Nav.put(ctx.getGuild().getIdLong(), new RustNavigatorReactElement(rs,message));
		    });
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "rustserver";
	}

	@Override
	public String getHelp() {
		return "rustserver [sort by:(isonline/byplayers)] (name)\nExample: rs players FacePunch \n isonline = online/on/ison/o\n byplayers = players/player/p";
	}

	@Override
	public String getAliase() {
		// TODO Auto-generated method stub
		return "rs";
	}

}
