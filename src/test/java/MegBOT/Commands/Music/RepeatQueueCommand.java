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

public class RepeatQueueCommand implements ICommand{

	@Override
	public void handle(CommandContext ctx) {
		final TextChannel channel = ctx.getEvent().getChannel();
		final Member self = ctx.getEvent().getGuild().getSelfMember();
		final GuildVoiceState selfVoiceState = self.getVoiceState();

		EmbedBuilder eb = EmbedBuilder();
		
		if (!selfVoiceState.inVoiceChannel()) {
			eb.setTitle(":anger: I need to be in a voice channel for this to work");
			channel.sendMessage(eb.build()).queue();
			return;
		}

		final Member member = ctx.getEvent().getMember();
		final GuildVoiceState memberVoiceState = member.getVoiceState();

		if (!memberVoiceState.inVoiceChannel()) {
			eb.setTitle(":anger: You need to be in a voice channel for this command to work");
			channel.sendMessage(eb.build()).queue();
			return;
		}

		if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			eb.setTitle(":anger: You need to be in the same voice channel as me for this to work");
			channel.sendMessage(eb.build()).queue();
			return;
		}
		
		final GuildMusicManager musicManager = PlayerManager.getInstance().getGuildMusicManager(ctx.getGuild());
		
		DjMode dm = new DjMode();
		
		if(musicManager.scheduler.djmode&&!dm.hasPermision(ctx.getEvent().getMember(), ctx.getGuild().getIdLong())) {
			EmbedBuilder j = EmbedBuilder();
			ctx.getEvent().getChannel().sendMessage(dm.noPermisionMessage()).queue();
			return;
		}
		
		final boolean newRepeatqueue = !musicManager.scheduler.repeatqueue;

		musicManager.scheduler.repeatqueue = newRepeatqueue;
		musicManager.scheduler.repeating = false;
		musicManager.scheduler.repeatone = false;

		eb.setTitle(String.format(":repeat: The player has been set to **%s**", newRepeatqueue ? "repeat queue" : "not repeating"));
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
		return "repeat queue";
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "Repeat the queue";
	}

	@Override
	public String getAliase() {
		// TODO Auto-generated method stub
		return "repeat q";
	}
	
}
