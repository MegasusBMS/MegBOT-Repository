package MegBOT.Utils;

import MegBOT.Main;
import MegBOT.Utils.Music.GuildMusicManager;
import MegBOT.Utils.Music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class DjMode {
	
	public boolean hasPermision(Member m,long GuildID) {
		if(Main.gs.size()==0) {
			return m.getPermissions().contains(Permission.MANAGE_CHANNEL);
		}
		return m.getPermissions().contains(Permission.MANAGE_CHANNEL)||m.getRoles().stream().anyMatch(element -> Main.gs.get((Long)GuildID).getDjRoles().contains(element));
	}

	public void DjMod(Guild g) {
		final GuildMusicManager musicManager = PlayerManager.getInstance().getGuildMusicManager(g);
		final boolean newDjMode = !musicManager.scheduler.djmode;
		musicManager.scheduler.djmode = newDjMode;
	}
	
	public MessageEmbed noPermisionMessage() {
		EmbedTemplate et = new EmbedTemplate();
		et.RC().Footer();
		EmbedBuilder eb = et.getEmbedBuilder();
		eb.setTitle(":headphones: Dj Mod **ON**");
		eb.setDescription("**You need to have role with DJ MOD Allowed or Manage_Channel Permission!!!**\n:bangbang: **BETA** :bangbang: ");
		return eb.build();
	}
}