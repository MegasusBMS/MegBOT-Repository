package MegBOT.Commands.Lol;

import java.util.List;

import MegBOT.Main;
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
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

public class lolMasteryCommand implements ICommand{
	@Override
	public void handle(CommandContext ctx) {
		String arg = ctx.getEvent().getMessage().getContentRaw().toLowerCase().toLowerCase();
		if(arg.equalsIgnoreCase(ctx.getPrefix()+getName())||arg.equalsIgnoreCase(ctx.getPrefix()+getAliase())) {
			arg="";
			LolRegister lr = new LolRegister(ctx.getEvent().getMember().getIdLong());
			if(lr.getPlatform()!=null)
			arg+=lr.getPlatform()+" "+lr.getName();
		}
		if(arg.startsWith(ctx.getPrefix()+getName()))
			arg = arg.replace(ctx.getPrefix()+getName()+" ","");
		else
			arg = arg.replace(ctx.getPrefix()+getAliase()+" ","");
		Summoner s;
		Emotes emotes = new Emotes();
		RiotConnection rc = new RiotConnection();
		RiotApi ra = rc.openConnection();
			LolPlayer lolp=new LolPlayer(arg,this,ra);
			if(lolp.isError()) {
				ctx.getEvent().getChannel().sendMessage(lolp.getErrorEmbed().build()).queue();
				return;
			}
			s=lolp.getSummoner();
			EmbedTemplate et = new EmbedTemplate();
			et.RC().Footer();
			EmbedBuilder eb = et.getEmbedBuilder();
			eb.setTitle(s.getName());
			try {
			List<ChampionMastery> cm = ra.getChampionMasteriesBySummoner(Platform.getPlatformByName(arg.split(" ")[0]), s.getId());
			LolUtils lu = new LolUtils();
			ChampionData cd;
			String c="";
			String b="";
			for(int i=0;i<Math.min(cm.size(), 20);i++) {
				cd=new ChampionData(cm.get(i).getChampionId());
				if(i<10)
					c+="**"+(i+1)+")**"+emotes.getEmote(cm.get(i).getChampionId())+" "+emotes.getEmote("ml"+cm.get(i).getChampionLevel())+" "+ lu.masteryPointrsConvertor(cm.get(i).getChampionPoints())+"\n";
				else
					b+="**"+(i+1)+")**"+emotes.getEmote(cm.get(i).getChampionId()) +" "+emotes.getEmote("ml"+cm.get(i).getChampionLevel())+" "+ lu.masteryPointrsConvertor(cm.get(i).getChampionPoints())+"\n";
			}
			eb.addField("Mastery", c , true);
			eb.addField("", b , true);
			eb.setThumbnail("https://ddragon.leagueoflegends.com/cdn/11.5.1/img/profileicon/"+s.getProfileIconId()+".png");
			ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
			
			
		} catch (RiotApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public String getName() {
		return "lol mastery";
	}

	@Override
	public String getHelp() {
		return "lol mastery (region) (name)";
	}

	@Override
	public String getAliase() {
		return "lol m";
	}
}
