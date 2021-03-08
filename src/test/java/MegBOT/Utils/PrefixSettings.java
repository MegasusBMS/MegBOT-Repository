package MegBOT.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrefixSettings {
	String Prefix = "$";
	long GuildID;
	List<String> ValidPrefix = new ArrayList<>(Arrays.asList("! $ % & * + = ~ > < . ^".split(" ")));
	
	public PrefixSettings(Long GuildID,String Prefix) {
		if(!ValidPrefix.contains(Prefix))
			return;
		this.GuildID = GuildID;
		this.Prefix = Prefix;
	}

	public PrefixSettings() {
		// TODO Auto-generated constructor stub
	}

	public String getPrefix() {
		return Prefix;
	}

	public void setPrefix(String prefix) {
		Prefix = prefix;
	}

	public long getGuildID() {
		return GuildID;
	}

	public void setGuildID(long guildID) {
		GuildID = guildID;
	}
	
	public boolean isValid(String prefix) {
		return ValidPrefix.contains(prefix);
	}

	public List<String> getValidPrefix() {
		return ValidPrefix;
	}

}
