package MegBOT;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.json.simple.parser.ParseException;

import MegBOT.CommandControlCenter.Listener;
import MegBOT.Console.ConsoleListener;
import MegBOT.LeagueOfLegends.ChampionData;
import MegBOT.LeagueOfLegends.ChampionsData;
import MegBOT.LeagueOfLegends.LolRegister;
import MegBOT.LeagueOfLegends.LolRegisterload;
import MegBOT.Utils.Emotes;
import MegBOT.Utils.GuildSettings;
import MegBOT.Utils.GuildSettingsLoader;
import MegBOT.Utils.LeaveChannel;
import MegBOT.Utils.PrefixSettings;
import MegBOT.Utils.PrefixSettingsLoader;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;

public class Main {

	public static JDA jda;
	public static String dprefix = "~";
	public static ConsoleListener console = new ConsoleListener();
	public static LeaveChannel lc = new LeaveChannel();
	public static String version = "alpha-0.2.1v";
	public static HashMap<String, String> emotes;
	public static HashMap<String, ChampionData> ChampionsData;
	public static HashMap<Long, LolRegister> accounts = new HashMap<Long, LolRegister>();
	public static List<Guild> vc = new ArrayList<Guild>();
	public static HashMap<Long, GuildSettings> gs = new HashMap<Long, GuildSettings>();
	public static boolean ready =false;
	public static HashMap<Long,PrefixSettings> prefix= new HashMap<Long, PrefixSettings>();

	public static void main(String[] args) throws LoginException {

		jda = JDABuilder.createDefault("Njk4NDM2MDAwNzQxMDY0NzE1.XpFzNg._-BNjj96sdUYkFOA8sUqablgA6o")
				.addEventListeners(new Listener()).build();
		Emotes e = new Emotes(1);
		ChampionsData c = new ChampionsData();
		try {
			ChampionsData = c.load();
		} catch (MalformedURLException | ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			emotes = e.load();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		console.start();
		lc.run();
	}
}