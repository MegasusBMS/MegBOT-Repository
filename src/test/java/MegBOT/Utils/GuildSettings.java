package MegBOT.Utils;

import java.util.List;

import net.dv8tion.jda.api.entities.Role;

public class GuildSettings {
	List<Role> DjRoles;
	long GuildID;
	
	public GuildSettings(List<Role> DjRoles,long GuildID) {
		this.DjRoles = DjRoles;
		this.GuildID = GuildID;
	}

	public List<Role> getDjRoles() {
		return DjRoles;
	}

	public void setDjRoles(List<Role> djRoles) {
		DjRoles = djRoles;
	}
	public long getGuildID() {
		return GuildID;
	}

	public void setGuildID(long guildID) {
		GuildID = guildID;
	}

	public void addDjRole(Role r) {
		DjRoles.add(r);
		
	}
}
