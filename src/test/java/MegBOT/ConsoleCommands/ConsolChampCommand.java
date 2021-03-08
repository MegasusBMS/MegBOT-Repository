package MegBOT.ConsoleCommands;

import MegBOT.LeagueOfLegends.ChampionData;

public class ConsolChampCommand {
	public ConsolChampCommand(String[] args) {
		if(args.length>1) {
		ChampionData cd = new ChampionData(args[1]);
		System.out.print(cd.getTitle());
		}else {
			System.out.print("chmap (name)"+"\n");
		}
	}
}
