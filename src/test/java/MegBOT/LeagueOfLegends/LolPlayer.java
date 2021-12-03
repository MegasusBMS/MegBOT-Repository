package MegBOT.LeagueOfLegends;

import java.util.ArrayList;
import java.util.List;

import MegBOT.CommandControlCenter.ICommand;
import MegBOT.Utils.EmbedTemplate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

public class LolPlayer {
	
	Summoner summoner ;
	Platform platform;
	boolean error;
	EmbedBuilder errorEmbed;
	String[] platforms = "EUNE EUW JP BR KR LAN LAS NA OCE RU TR".split(" ");
	
	public LolPlayer(String arg,ICommand cmd, RiotApi ra) {
		error = false;
		String[] args = arg.split(" ");
		
		//System.out.println(arg);
		
		if(args.length<2) {
			embedEror("Provide more arguments", cmd.getHelp());
			return;
		}
		boolean ok=false;
		
		for(int i=0;i<platforms.length;i++) {
			if(platforms[i].equalsIgnoreCase(args[0])) {
				ok=true;
				break;
			}
		}
		if(!ok) {
			error = true;
			embedEror("Unknow region", "**Valid Regions:**: EUNE, EUW, JP, BR, KR, LAN, LAS, NA, OCE, RU, TR");
			return;
		}
		String name = arg.replace(args[0]+" ", "");
		this.platform=Platform.getPlatformByName(args[0]);
		try {
			this.summoner = ra.getSummonerByName(this.platform, name);
		} catch (RiotApiException e) {
			if(e.getErrorCode()==404) {
				embedEror("This Summoner doesn't exist", "Provide **Valid Region** and **Valid Summoner**\n**Valid Regions:**: EUNE, EUW, JP, BR, KR, LAN, LAS, NA, OCE, RU, TR\n**Valid Summoner:** the name of an existing summoner");
			}
			return;
		}
		
	}
	
	public Summoner getSummoner() {
		return summoner;
	}


	public Platform getPlatform() {
		return platform;
	}


	public boolean isError() {
		return error;
	}


	public EmbedBuilder getErrorEmbed() {
		return errorEmbed;
	}


	public String[] getPlatforms() {
		return platforms;
	}

	public void embedEror(String error,String cmdHelp) {
		
		EmbedTemplate et = new EmbedTemplate();
		et.RC().Footer();
		this.error = true;
		EmbedBuilder eb = et.getEmbedBuilder();
		eb.setTitle(":anger: "+error);
		eb.setDescription(cmdHelp);
		errorEmbed=eb;
	}
	
}
