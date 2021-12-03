package MegBOT.Commands.Lol;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import MegBOT.CommandControlCenter.CommandContext;
import MegBOT.CommandControlCenter.ICommand;
import MegBOT.LeagueOfLegends.ChampionData;
import MegBOT.LeagueOfLegends.LolPlayer;
import MegBOT.LeagueOfLegends.LolRegister;
import MegBOT.LeagueOfLegends.RiotConnection;
import MegBOT.Utils.EmbedTemplate;
import MegBOT.Utils.Emotes;
import MegBOT.Utils.LolUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.champion_mastery.dto.ChampionMastery;
import net.rithms.riot.api.endpoints.league.dto.LeagueEntry;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

public class lolProfileCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		String arg = ctx.getEvent().getMessage().getContentRaw().toLowerCase().toLowerCase();
		if (arg.equalsIgnoreCase(ctx.getPrefix()+getName())||arg.equalsIgnoreCase(ctx.getPrefix()+getAliase())) {
			arg="";
			LolRegister lr = new LolRegister(ctx.getEvent().getMember().getIdLong());
			if (lr.getPlatform() != null)
				arg += lr.getPlatform() + " " + lr.getName();
		}
		if (arg.startsWith(ctx.getPrefix()+getName()))
			arg = arg.replace(ctx.getPrefix()+getName() + " ", "");
		else
			arg = arg.replace(ctx.getPrefix()+getAliase() + " ", "");
		Summoner s;
		Emotes emotes = new Emotes();
		RiotConnection rc = new RiotConnection();
		RiotApi ra = rc.openConnection();
		EmbedTemplate et = new EmbedTemplate();
		et.RC().Footer();
		EmbedBuilder eb = et.getEmbedBuilder();
		LolPlayer lolp = new LolPlayer(arg, this, ra);
		if (lolp.isError()) {
			ctx.getEvent().getChannel().sendMessage(lolp.getErrorEmbed().build()).queue();
			return;
		}
		s = lolp.getSummoner();
		eb.setTitle(":star2: "+s.getName());
		try {
			Set<LeagueEntry> leag = ra.getLeagueEntriesBySummonerId(Platform.getPlatformByName(arg.split(" ")[0]),
					s.getId());
			List<LeagueEntry> league = new ArrayList<LeagueEntry>(leag);
			boolean rank=false;
			LeagueEntry solo = null;
			LeagueEntry flex = null;
			boolean sd = false;
			boolean f = false;
			switch (league.size()) {
			case 1:
				if (league.get(0).getQueueType().contains("Solo")) {
					sd = true;
					solo = league.get(0);
				} else {
					f = true;
					flex = league.get(0);
				}
				break;
			case 2:
				if (league.get(0).toString().contains("FLEX")) {
					league.add(league.get(0));
					league.remove(0);
				}
				solo=league.get(0);
				flex=league.get(1);
				rank = true;
				break;
			}
			List<ChampionMastery> cm = ra.getChampionMasteriesBySummoner(Platform.getPlatformByName(arg.split(" ")[0]),
					s.getId());
			LolUtils lu = new LolUtils();

			eb.appendDescription("**Level: **" + s.getSummonerLevel());
			String c = "";
			eb.setThumbnail("https://ddragon.leagueoflegends.com/cdn/10.25.1/img/profileicon/" + s.getProfileIconId() + ".png");
			if(cm.size()<=0) {
				eb.appendDescription("\n **No Status**");
				ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
				return;
				}
			ChampionData cd = new ChampionData(cm.get(0).getChampionId());
			for (int i = 0; i < Math.min(3, cm.size()); i++) {
				cd = new ChampionData(cm.get(i).getChampionId());
				c += emotes.getEmote(cm.get(i).getChampionId()) + cd.getName() + " "
						+ emotes.getEmote("ml" + cm.get(i).getChampionLevel()) + " "
						+ lu.masteryPointrsConvertor(cm.get(i).getChampionPoints()) + "\n";
			}
			eb.addField("**Mains:**", c, true);
			Emotes emote = new Emotes();
			
			String eu = emote.getEmote("UNRANK");
			try {
			String es = emote.getEmote(solo.getTier()) + solo.getRank() + " " + solo.getLeaguePoints() + "LP";
			String ef = emote.getEmote(flex.getTier()) + flex.getRank() + " " + flex.getLeaguePoints() + "LP";

			eb.addField("Ranks:",
					"**Solo/Duo:** "
							+ (rank ? es : (sd ? es : eu))
							+ "\n" + "**FLEX:** "
							+ (rank ? ef : (f ? es : eu)),
					true);
			}catch(NullPointerException e) {
				eb.addField("Ranks:",
						"**Solo/Duo:** "
								+ (eu)
								+ "\n" + "**FLEX:** "
								+ (eu),
						true);
			}
		ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
		} catch (RiotApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public String getName() {
		return "lol profile";
	}

	@Override
	public String getHelp() {
		return getName() + " (region) (name)";
	}

	@Override
	public String getAliase() {
		return "lol p";
	}
}
