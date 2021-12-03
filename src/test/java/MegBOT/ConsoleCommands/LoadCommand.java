package MegBOT.ConsoleCommands;

import MegBOT.Main;
import MegBOT.LeagueOfLegends.LolRegisterload;
import MegBOT.Utils.GuildSettingsLoader;
import MegBOT.Utils.PrefixSettingsLoader;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LoadCommand extends ListenerAdapter{
	@Override
	public void onReady(ReadyEvent e){
		LoadCommand();
	}
/*	@Override
	public void onGuildLeave(GuildLeaveEvent e) {
		LoadCommand();
	}*/
	
	public LoadCommand(){
	}
	public LoadCommand(int i) {
		LoadCommand();
	}
	
	public void LoadCommand() {
		
		GuildSettingsLoader gsl = new GuildSettingsLoader();
		Main.gs = gsl.load();
		System.out.println("Load GS: "+Main.gs.size());
		
		LolRegisterload lrd = new LolRegisterload();
		Main.accounts = lrd.load();
		System.out.println("Load LOL Accounts: "+Main.accounts.size());
		
		PrefixSettingsLoader psl = new PrefixSettingsLoader();
		Main.prefix = psl.load();
		System.out.println("Load PS: "+Main.prefix.size());
		
		Main.ready=true;
		
		System.out.println("Ready to go!");
	}
}
