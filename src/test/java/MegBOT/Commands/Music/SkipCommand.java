package MegBOT.Commands.Music;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import MegBOT.Main;
import MegBOT.CommandControlCenter.CommandContext;
import MegBOT.CommandControlCenter.ICommand;
import MegBOT.Utils.DjMode;
import MegBOT.Utils.EmbedTemplate;
import MegBOT.Utils.Music.GuildMusicManager;
import MegBOT.Utils.Music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class SkipCommand implements ICommand {

	private void SkipCommand(List<String> msg, GuildMessageReceivedEvent event) {
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		TextChannel channel = event.getChannel();
		AudioPlayer player = musicManager.player;
		
		DjMode dm = new DjMode();
		
		if(musicManager.scheduler.djmode&&!dm.hasPermision(event.getMember(), event.getGuild().getIdLong())) {
			EmbedBuilder j = EmbedBuilder();
			event.getChannel().sendMessage(dm.noPermisionMessage()).queue();
			return;
		}
		
		if (player.getPlayingTrack() == null) {
			channel.sendMessage("Nothing to skip").queue();
			return;
		}
		BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();
		if (queue.size() == 0) {
			channel.sendMessage("Nothing to skip").queue();
			return;
		}
		musicManager.scheduler.nextTrack();
		channel.sendMessage("Skipping...").queue();
	}
	
	private EmbedBuilder EmbedBuilder() {
		EmbedTemplate et = new EmbedTemplate();
		et.Footer().RC();
		return et.getEmbedBuilder();
	}

	@Override
	public void handle(CommandContext ctx) {
		SkipCommand(ctx.getArgs(), ctx.getEvent());

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "skip";
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAliase() {
		// TODO Auto-generated method stub
		return "next";
	}

}
