package MegBOT.LeagueOfLegends;

import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;

public class RiotConnection {
	
	RiotApi riot;
	
	public RiotConnection() {
		ApiConfig ac = new ApiConfig().setKey("RGAPI-73267e51-a098-422a-b497-72937e448650");
		riot = new RiotApi(ac);
	}
	
	public RiotApi openConnection() {
		return riot;
	}
}
