package MegBOT.LeagueOfLegends;

import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;

public class RiotConnection {
	
	RiotApi riot;
	
	public RiotConnection() {
		ApiConfig ac = new ApiConfig().setKey("RGAPI-654032f8-371e-488a-b6fe-5573084db9f0");
		riot = new RiotApi(ac);
	}
	
	public RiotApi openConnection() {
		return riot;
	}
}
