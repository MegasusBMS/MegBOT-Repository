package MegBOT.Commands.Music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

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

public class PauseCommand implements ICommand{

	@Override
	public void handle(CommandContext ctx) {
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(ctx.getGuild());
		AudioPlayer p = musicManager.player;
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
		
		if(ctx.getEvent().getMessage().getContentRaw().startsWith(getAliase())&&!p.isPaused()) {
			eb.setTitle(":anger: Player isn't paused!");
			channel.sendMessage(eb.build()).queue();
			return;
		}
		
		if(ctx.getEvent().getMessage().getContentRaw().startsWith(getName())&&p.isPaused()) {
			eb.setTitle(":anger: Player is allready paused!");
			channel.sendMessage(eb.build()).queue();
			return;
		}
		
		DjMode dm = new DjMode();
		
		if(musicManager.scheduler.djmode&&!dm.hasPermision(ctx.getEvent().getMember(), ctx.getGuild().getIdLong())) {
			ctx.getEvent().getChannel().sendMessage(dm.noPermisionMessage()).queue();
			return;
		}
		
		p.setPaused(!p.isPaused());
		
		eb.setTitle(String.format("**%s**", p.isPaused() ? ":pause_button: Pasused" : ":arrow_forward: Resumed"));
		ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
	}
	
	private EmbedBuilder EmbedBuilder() {
		EmbedTemplate et = new EmbedTemplate();
		et.Footer().RC();
		return et.getEmbedBuilder();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "pause";
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "Pause the track";
	}

	@Override
	public String getAliase() {
		// TODO Auto-generated method stub
		return "resume";
	}
	
}
