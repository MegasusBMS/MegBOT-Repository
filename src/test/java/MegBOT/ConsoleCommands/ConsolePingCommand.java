package MegBOT.ConsoleCommands;

import MegBOT.Main;
import MegBOT.Utils.GuildSettingsLoader;

public class ConsolePingCommand {
	public ConsolePingCommand() {
		Main.jda.getRestPing().queue(

				(ping) -> System.out.println("Ping: " + ping+"\n"));
		
	}
}
