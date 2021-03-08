package MegBOT.LeagueOfLegends;

import java.util.ArrayList;
import java.util.List;

import MegBOT.Main;

public class LolRegister {
	String name;
	String platform;
	
	public LolRegister(String name, String platform) {
		this.name=name;
		this.platform=platform;
	}
	
	public LolRegister(Long id) {
		List<Long> keys = new ArrayList<Long>(Main.accounts.keySet());
		if(keys.contains(id)) {
		name = Main.accounts.get(id).getName();
		platform = Main.accounts.get(id).getPlatform();
		}
		else {
			this.name=null;
			this.platform=null;
		}
	}
	
	public String getName() {
		return name;
	}
	public String getPlatform() {
		return platform;
	}
}
