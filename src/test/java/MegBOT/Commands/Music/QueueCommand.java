package MegBOT.Commands.Music;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
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

public class QueueCommand implements ICommand {

	private void QueueCommand(String[] args, GuildMessageReceivedEvent event) {
		TextChannel channel = event.getChannel();
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();

		if (args.length == 2 && args[1].equalsIgnoreCase("clear")) {
			musicManager.scheduler.queueclear();
			channel.sendMessage("Clear queue ...").queue();
		}

		if (queue.isEmpty()) {
			channel.sendMessage("The queue is empty").queue();

			return;
		}
		int trackCount;
		List<AudioTrack> list;
		list = new ArrayList<AudioTrack>(queue);
		trackCount = Math.min(queue.size(), 10);
		EmbedBuilder builder = EmbedBuilder();
		builder.setTitle("Current Queue (Total: " + list.size() + ")");

		for (int i = 0; i < trackCount; i++) {
			AudioTrack track = list.get(i);
			AudioTrackInfo info = track.getInfo();

			builder.appendDescription(String.format("**" + (i + 1) + ")** [%s](%s)\n%s %s\n", info.title, info.uri,
					"Duration: ", formatTime(track.getDuration())));
		}
		long duration = 0;
		for (int i = 0; i < list.size(); i++) {
			AudioTrack track = list.get(i);
			duration += track.getDuration();
		}
		builder.appendDescription(String.format("\n --------------------------------\n**Duration of the queue:** [%s] ",
				formatTime(duration)));

		channel.sendMessage(builder.build()).queue();
	}

	private String formatTime(long timeInMillis) {
		final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
		final long minutes = timeInMillis % TimeUnit.HOURS.toMillis(1) / TimeUnit.MINUTES.toMillis(1);
		final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

		return String.format("%02dh %02dm %02ds", hours, minutes, seconds);
	}
	
	private EmbedBuilder EmbedBuilder() {
		EmbedTemplate et = new EmbedTemplate();
		et.Footer().RC();
		return et.getEmbedBuilder();
	}

	@Override
	public void handle(CommandContext ctx) {
		QueueCommand(ctx.getEvent().getMessage().getContentRaw().split(" "), ctx.getEvent());

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "queue";
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "Show music queue";
	}

	@Override
	public String getAliase() {
		// TODO Auto-generated method stub
		return "list";
	}

}
