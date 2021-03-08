package MegBOT.Commands.Music;

import MegBOT.Main;
import MegBOT.CommandControlCenter.CommandContext;
import MegBOT.CommandControlCenter.ICommand;
import MegBOT.Utils.DjMode;
import MegBOT.Utils.EmbedTemplate;
import MegBOT.Utils.Music.GuildMusicManager;
import MegBOT.Utils.Music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class LeaveCommand implements ICommand {

	private void StopCommand(GuildMessageReceivedEvent event, boolean b) {
		AudioManager am = event.getGuild().getAudioManager();
		TextChannel tc = event.getChannel();

		if (!am.isConnected()) {
			if (b) {
				EmbedBuilder j = EmbedBuilder();
				j.setTitle(":smiley: I'm not connected to a channel");
				j.setDescription("First i need to join one");
				tc.sendMessage(j.build()).queue();
			}
			return;
		}

		VoiceChannel vc1 = am.getConnectedChannel();

		if (!vc1.getMembers().contains(event.getMember())) {
			if (b) {
				EmbedBuilder j = EmbedBuilder();
				j.setTitle(":sweat_smile: You have to be in the same voicechannel as me to use this");
				j.setDescription("Come to me if you can! :smiling_imp: ");
				tc.sendMessage(j.build()).queue();
			}
			return;
		}
		
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		
		DjMode dm = new DjMode();
		
		if(musicManager.scheduler.djmode&&!dm.hasPermision(event.getMember(), event.getGuild().getIdLong())) {
			EmbedBuilder j = EmbedBuilder();
			event.getChannel().sendMessage(dm.noPermisionMessage()).queue();
			return;
		}
		
		musicManager.player.stopTrack();
		musicManager.scheduler.queueclear();
		am.closeAudioConnection();
		Main.vc.remove(event.getGuild());
		if (b) {
			EmbedBuilder j = EmbedBuilder();
			j.setTitle(":wave: Disconnected from your channel");
			j.setDescription("Bye !!!");
			tc.sendMessage(j.build()).queue();
		}
	}
	
	private EmbedBuilder EmbedBuilder() {
		EmbedTemplate et = new EmbedTemplate();
		et.Footer().RC();
		return et.getEmbedBuilder();
	}

	@Override
	public void handle(CommandContext ctx) {
		StopCommand(ctx.getEvent(), true);

	}

	@Override
	public String getName() {
		return "leave";
	}

	@Override
	public String getHelp() {
		return "Stop the music!";
	}

	@Override
	public String getAliase() {
		return getName();
	}
}
