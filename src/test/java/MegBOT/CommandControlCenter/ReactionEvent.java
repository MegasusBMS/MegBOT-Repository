package MegBOT.CommandControlCenter;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import MegBOT.Main;
import MegBOT.Rust.RustNavigatorReactElement;
import MegBOT.Rust.RustServerStats;
import MegBOT.Utils.EmbedTemplate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReactionEvent extends ListenerAdapter {
	@Override
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
		if(Main.news.containsKey(e.getGuild().getIdLong()))
			if(e.getMessageIdLong()==Main.news.get(e.getGuild().getIdLong()).msg.getIdLong()
					&& e.getMember().getUser().getIdLong()!=Main.jda.getSelfUser().getIdLong()) {
				news(e.getGuild().getIdLong());
			}
		if (!Main.Nav.containsKey(e.getGuild().getIdLong()))
			return;
		if (e.getMember().getUser().getIdLong() == Main.jda.getSelfUser().getIdLong())
			return;
		Message msg = Main.Nav.get(e.getGuild().getIdLong()).msg;
		List<RustServerStats> rs = Main.Nav.get(e.getGuild().getIdLong()).rustservers;
		if (e.getMessageIdLong() == msg.getIdLong()) {
			EmbedBuilder eb = new EmbedTemplate().RC().Footer().getEmbedBuilder();
			int pag = Integer.parseInt(msg.getEmbeds().get(0).getDescription()
					.split("\n")[msg.getEmbeds().get(0).getDescription().split("\n").length - 1].split("/")[0]);
			int maxpag = Integer.parseInt(msg.getEmbeds().get(0).getDescription()
					.split("\n")[msg.getEmbeds().get(0).getDescription().split("\n").length - 1].split("/")[1]);
			int idex;
			RustServerStats rss = new RustServerStats();
			switch (e.getReactionEmote().getName()) {
			case "1️⃣":
				idex = 5 * (pag - 1);
				rss = rs.get(idex);
				break;
			case "2️⃣":
				idex =1+ 5 * (pag - 1);
				rss = rs.get(idex);
				break;
			case "3️⃣":
				idex = 2+ 5 * (pag - 1);
				rss = rs.get(idex);
				break;
			case "4️⃣":
				idex = 3+ 5 * (pag - 1);
				rss = rs.get(idex);
				break;
			case "5️⃣":
				idex = 4+ 5 * (pag - 1);
				rss = rs.get(idex);
				break;
			case "⏪":
				if (pag > 1) {
					pag--;
					eb.setTitle("<:rust:825669303739088917> **RustSearch:**");
					for (int i = 0; i < 5; i++) {
						eb.appendDescription("**" + (i+1) + ") :flag_"
								+ rs.get(i + 5 * (pag - 1)).country.toLowerCase() + ": "
								+ rs.get(i + 5 * (pag - 1) + 1).name + "**\n");
						eb.appendDescription("**Players:**" + "`" + rs.get(i).players + "/"
								+ rs.get(i + 5 * (pag - 1)).maxPlayers + "`\n**IP:** `" + rs.get(i + 5 * (pag - 1)).ip
								+ ":" + rs.get(i + 5 * (pag - 1)).port + "`\n\n");
					}
					eb.appendDescription(pag + "/" + (rs.size() % 5 == 0 ? rs.size() / 5 : rs.size() / 5 + 1));
					e.retrieveMessage().complete().clearReactions().queue();
					e.getChannel().editMessageById(msg.getIdLong(), eb.build()).queue(message -> {
						message.addReaction("1️⃣").queue();
						message.addReaction("2️⃣").queue();
						message.addReaction("3️⃣").queue();
						message.addReaction("4️⃣").queue();
						message.addReaction("5️⃣").queue();
						message.addReaction("⏪").queue();
						message.addReaction("⏩").queue();
						Main.Nav.remove(e.getGuild().getIdLong());
						Main.Nav.put(e.getGuild().getIdLong(), new RustNavigatorReactElement(rs, message));
					});
				}
				return;
			case "⏩":
				if (pag < maxpag) {
					pag++;
					for (int i = 0; i < Math.min(5, rs.size() - 5 * (pag - 1)); i++) {
						eb.appendDescription("**" + (i+1) + ") :flag_"
								+ rs.get(i + 5 * (pag - 1)).country.toLowerCase() + ": "
								+ rs.get(i + 5 * (pag - 1)).name + "**\n");
						eb.appendDescription("**Players:**" + "`" + rs.get(i + 5 * (pag - 1)).players + "/"
								+ rs.get(i + 5 * (pag - 1)).maxPlayers + "`\n**IP:** `" + rs.get(i + 5 * (pag - 1)).ip
								+ ":" + rs.get(i + 5 * (pag - 1)).port + "`\n\n");
					}
					eb.appendDescription(pag + "/" + (rs.size() % 5 == 0 ? rs.size() / 5 : rs.size() / 5 + 1));
					e.retrieveMessage().complete().clearReactions().queue();
					e.getChannel().editMessageById(msg.getIdLong(), eb.build()).queue(message -> {
						message.addReaction("1️⃣").queue();
						message.addReaction("2️⃣").queue();
						message.addReaction("3️⃣").queue();
						message.addReaction("4️⃣").queue();
						message.addReaction("5️⃣").queue();
						message.addReaction("⏪").queue();
						message.addReaction("⏩").queue();
						Main.Nav.remove(e.getGuild().getIdLong());
						Main.Nav.put(e.getGuild().getIdLong(), new RustNavigatorReactElement(rs, message));
					});
				}
				return;
			default:
				return;
			}
			eb.setTitle((rss.status.equals("online") ? ":green_circle: Online" : ":red_circle: Offline")+(rss.mapImageUri!=null?" _\\_\\_\\_\\_\\_\\_\\_ Click to View Map ----->":""));
			if (rss.serverSite != null)
				eb.appendDescription("**"+String.format("[%s](%s)\n\n", rss.name, rss.serverSite)+"**");
			else {
				eb.appendDescription("**" + rss.name + "**\n\n");
			}

			eb.appendDescription(rss.description + "\n\n");

			eb.appendDescription("**"+"Click to connect: steam://connect/" + rss.ip + ":" + rss.port+"**\n\n");
			eb.appendDescription("**"+String.format("View on [%s](%s)\n", "BattelMetrics\n",
					"https://www.battlemetrics.com/servers/rust/" + rss.id)+"**");
			
			Locale loc = new Locale("",rss.country);
			String country = loc.getDisplayCountry();
			
			eb.addField("**Type**", "```"+((rss.official ? "Official" : (rss.moded ? "Modded" : "Community")) +" "+(rss.pvp?"PVP":"PVE"))+"```", true);
			eb.addField("**Players:**","```"+rss.players+"/"+rss.maxPlayers+"```",true);
			eb.addField("**In Queue:**", "```"+rss.queue+"```", true);
			eb.addField("**Location: **:flag_"+rss.country.toLowerCase()+":", "```" + country + "```", true);
			eb.addField("**FPS (avg. FPS)**", "```"+rss.fps+"("+rss.fpsAvg+")```", true);
			if(rss.mapsize!=null)
			eb.addField("**MapSize**","```"+rss.mapsize+"```",true);
			eb.addField("**IP:**","```"+rss.ip+":"+rss.port+"```",true);
			if(rss.mapImageUri!=null)
			eb.setThumbnail(rss.mapImageUri);
			eb.setImage(rss.serverImageUri);
			
			e.getChannel().sendMessage(eb.build()).queue();
		}
	}

	private String Date(String sdate) {
			
			String[] s = sdate.split("T");
			String[] ss = s[0].split("-");
			
			System.out.println(sdate);
			
			int year=0,month=0,date=0,hrs=0,min=0,sec = 0;
			
			year	=	Integer.parseInt(ss[0]);
			month	=	Integer.parseInt(ss[1]);
			date	=	Integer.parseInt(ss[2]);
			
			ss = s[1].split(":");
			
			hrs 	=  	Integer.parseInt(ss[0]);
			min 	=	Integer.parseInt(ss[1]);
			sec		=	Integer.parseInt(ss[2].substring(0,1));
			System.out.print((year+" "+month+ " "+date+" "+ hrs+" "+ min+ " "+sec));
			@SuppressWarnings("deprecation")
			Date d = new Date(year, month, date, hrs, min, sec);
			
			return d.toString();
			
		}
	
	private String upTime(Long uptime) {
		
		long days = TimeUnit.MILLISECONDS
			    .toDays(uptime);
			uptime -= TimeUnit.DAYS.toMillis(days);

			long hours = TimeUnit.MILLISECONDS
			    .toHours(uptime);
			uptime -= TimeUnit.HOURS.toMillis(hours);

			long minutes = TimeUnit.MILLISECONDS
			    .toMinutes(uptime);
			uptime -= TimeUnit.MINUTES.toMillis(minutes);

			long seconds = TimeUnit.MILLISECONDS
			    .toSeconds(uptime);
		
		return days+" days "+hours+" hours "+minutes+" minutes "+seconds+" seconds  ago";
	}
	private void news(Long id) {
		EmbedBuilder eb = new EmbedTemplate().RC().Footer().getEmbedBuilder();
		eb.setTitle("**Version:"+Main.version+"**");
		eb.appendDescription("📰 **News:**\n"
				+ ":pushpin: <:rust:825669303739088917> Rust Server Search Command\n"
				+ ":pushpin: 📰 News Section\n"
				+ "\n:white_check_mark: **Fixed:**\n"
				+ ":pushpin: Random Command activate similar command\n"
				+ "Ex: player activate play command");
	}
}
