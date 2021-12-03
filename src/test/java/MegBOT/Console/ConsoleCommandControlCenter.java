package MegBOT.Console;

import MegBOT.ConsoleCommands.ConsolChampCommand;
import MegBOT.ConsoleCommands.ConsolePingCommand;
import MegBOT.ConsoleCommands.LoadCommand;

public class ConsoleCommandControlCenter {
	

	public ConsoleCommandControlCenter(String[] args){
		switch(args[0]) {
		case "ping":
			new ConsolePingCommand();
			break;
		case "champ":
			new ConsolChampCommand(args);
			break;
		case "load":
			new LoadCommand(5);
			break;
		}
	}
}