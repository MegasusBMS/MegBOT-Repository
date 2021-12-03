package MegBOT.Commands.Lol;

import java.util.ArrayList;
import java.util.List;

import MegBOT.CommandControlCenter.CommandContext;
import MegBOT.CommandControlCenter.ICommand;
import MegBOT.LeagueOfLegends.LolPlayer;
import MegBOT.LeagueOfLegends.LolRegister;
import MegBOT.LeagueOfLegends.RiotConnection;
import MegBOT.Utils.EmbedTemplate;
import MegBOT.Utils.Emotes;
import net.dv8tion.jda.api.EmbedBuilder;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.champion_mastery.dto.ChampionMastery;
import net.rithms.riot.api.endpoints.spectator.dto.BannedChampion;
import net.rithms.riot.api.endpoints.spectator.dto.CurrentGameInfo;
import net.rithms.riot.api.endpoints.spectator.dto.CurrentGameParticipant;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

public class lolLiveCommand implements ICommand{

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
		
		Emotes emotes = new Emotes();
		RiotConnection rc = new RiotConnection();
		RiotApi ra = rc.openConnection();
			LolPlayer lolp=new LolPlayer(arg,this,ra);
			if(lolp.isError()) {
				ctx.getEvent().getChannel().sendMessage(lolp.getErrorEmbed().build()).queue();
				return;
			}
			EmbedTemplate et = new EmbedTemplate();
			et.RC().Footer();
			EmbedBuilder eb = et.getEmbedBuilder();
			Platform platform = Platform.getPlatformByName(arg.split(" ")[0]);
			String name = arg.split(" ")[1];
			Summoner s = lolp.getSummoner();
			CurrentGameInfo game = null;
			
			try {
				game = rc.openConnection().getActiveGameBySummoner(platform, s.getId());
			} catch (RiotApiException e) {
				eb.setTitle("**:loudspeaker: This summoner isn't playing right now**");
				ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
				return;
			}
			
			eb.setTitle("**:video_game: Let's see the game: **");
			
			List<BannedChampion> bc = game.getBannedChampions();
			List<CurrentGameParticipant> p = game.getParticipants();
			String type = game.getGameType();
			eb.appendDescription("BannedChampions:\n");
			for(int i = 0 ;i< bc.size(); i++) {
				eb.appendDescription(emotes.getEmote(bc.get(i).getChampionId())+" ");
				if(i== 4) 
					eb.appendDescription(" :black_medium_small_square: :black_medium_small_square: ");
			}
			
			
			
			List<String>participants = new ArrayList<String>();
			int c = 0;
			
			for(CurrentGameParticipant part : p) {
				ChampionMastery cm=null;
				try {
					cm = rc.openConnection().getChampionMasteriesBySummonerByChampion(platform, part.getSummonerId(), part.getChampionId());
				} catch (RiotApiException e) {
					e.printStackTrace();
				}
				participants.add(part.isBot() ? "BOT" : emotes.getEmote(part.getChampionId()) + (cm != null ? emotes.getEmote("ml" + cm.getChampionLevel()) : emotes.getEmote("ml0")) +" " +part.getSummonerName() + " ");
			}
			
			
			eb.addField("**Blue Team:**", 
					participants.get(0)+ "\n" +
					participants.get(1)+ "\n" +
					participants.get(2)+ "\n" +
					participants.get(3)+ "\n" +
					participants.get(4)+ "\n"
					, true);
			eb.addField("**Red Team:**", 
					participants.get(5)+ "\n" +
					participants.get(6)+ "\n" +
					participants.get(7)+ "\n" +
					participants.get(8)+ "\n" +
					participants.get(9)+ "\n"
					, true);
			
			
			ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
			
			
			
			
			
			
			
			
	}

	@Override
	public String getName() {
		return "lol live";
	}

	@Override
	public String getHelp() {
		return "lol live (region) (name)";
	}

	@Override
	public String getAliase() {
		return getName();
	}
}
