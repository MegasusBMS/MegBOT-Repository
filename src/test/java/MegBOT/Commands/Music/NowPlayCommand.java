package MegBOT.Commands.Music;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import MegBOT.Main;
import MegBOT.CommandControlCenter.CommandContext;
import MegBOT.CommandControlCenter.ICommand;
import MegBOT.Utils.EmbedTemplate;
import MegBOT.Utils.Music.GuildMusicManager;
import MegBOT.Utils.Music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class NowPlayCommand implements ICommand {

	private void NowPlayCommand(List<String> args, GuildMessageReceivedEvent event) {
		TextChannel channel = event.getChannel();
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		AudioPlayer player = musicManager.player;

		if (player.getPlayingTrack() == null) {
			channel.sendMessage("The player is not playing any song.").queue();

			return;
		}

		AudioTrackInfo info = player.getPlayingTrack().getInfo();
		EmbedBuilder n = EmbedBuilder();
		n.setTitle(":thinking: Now play :");
		n.setDescription(String.format("**Playing** [%s](%s)\n%s %s - %s", info.title, info.uri,
				player.isPaused() ? "\u23F8" : "▶", formatTime(player.getPlayingTrack().getPosition()),
				formatTime(player.getPlayingTrack().getDuration())));
		channel.sendMessage(n.build()).queue();
	}

	private String formatTime(long timeInMillis) {
		final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
		final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
		final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}
	
	private EmbedBuilder EmbedBuilder() {
		EmbedTemplate et = new EmbedTemplate();
		et.Footer().RC();
		return et.getEmbedBuilder();
	}

	@Override
	public void handle(CommandContext ctx) {
		NowPlayCommand(ctx.getArgs(), ctx.getEvent());

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "nowplay";
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "Show the curent track";
	}

	@Override
	public String getAliase() {
		// TODO Auto-generated method stub
		return "np";
	}

}
