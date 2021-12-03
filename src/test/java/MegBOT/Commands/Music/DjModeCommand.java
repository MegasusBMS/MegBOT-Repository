package MegBOT.Commands.Music;

import MegBOT.Main;
import MegBOT.CommandControlCenter.CommandContext;
import MegBOT.CommandControlCenter.ICommand;
import MegBOT.Utils.DjMode;
import MegBOT.Utils.EmbedTemplate;
import MegBOT.Utils.Music.GuildMusicManager;
import MegBOT.Utils.Music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class DjModeCommand implements ICommand{

	@Override
	public void handle(CommandContext ctx) {
		Member m = ctx.getEvent().getMember();
		TextChannel channel = ctx.getEvent().getChannel();
		EmbedBuilder eb = EmbedBuilder();
		AudioManager am = ctx.getEvent().getGuild().getAudioManager();
		
		GuildVoiceState vs = ctx.getEvent().getMember().getVoiceState();
		
		DjMode dm = new DjMode();
		if(!dm .hasPermision(m, ctx.getGuild().getIdLong())) {
			ctx.getEvent().getChannel().sendMessage(dm.noPermisionMessage()).queue();
			return;
		}
		
		if(!am.isConnected()){
			eb.setTitle(":sweat_smile: I need to join voice channel");
			eb.setDescription("First join to a voice channel");
			ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
			return;
		}
		
		if(!vs.inVoiceChannel()){
			eb.setTitle(":sweat_smile: You are not connected to a voice channel");
			eb.setDescription("First join to a voice channel");
			ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
			return;
		}
			final GuildMusicManager musicManager = PlayerManager.getInstance().getGuildMusicManager(ctx.getGuild());
			final boolean newDjMode = !musicManager.scheduler.djmode;
			musicManager.scheduler.djmode = newDjMode;
			
			eb.setTitle(String.format(":headphones: DJ Mod: **%s**", newDjMode ? "Enabled" : "Disabled"));
			channel.sendMessage(eb.build()).queue();
	}


	private EmbedBuilder EmbedBuilder() {
		EmbedTemplate et = new EmbedTemplate();
		et.Footer().RC();
		return et.getEmbedBuilder();
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "djmode";
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "Just player with dj permision can use music commands";
	}

	@Override
	public String getAliase() {
		// TODO Auto-generated method stub
		return "dj";
	}
	
}
