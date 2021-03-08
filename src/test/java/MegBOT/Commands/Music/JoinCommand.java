package MegBOT.Commands.Music;

import MegBOT.Main;
import MegBOT.CommandControlCenter.CommandContext;
import MegBOT.CommandControlCenter.ICommand;
import MegBOT.Utils.EmbedTemplate;
import MegBOT.Utils.Music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand implements ICommand{

	public JoinCommand() {}
	
	private void JoinCommand(GuildMessageReceivedEvent event) {
		AudioManager am = event.getGuild().getAudioManager();
		TextChannel tc = event.getChannel();
        PlayerManager mm = PlayerManager.getInstance();
        mm.getGuildMusicManager(event.getGuild()).player.setVolume(10);
		EmbedBuilder j = EmbedBuilder();
				if(am.isConnected()){
					if(event.getMember().getVoiceState().inVoiceChannel()){

						j.setTitle(":sweat_smile: You are not connected to a voice channel");
						j.setDescription("First join to a voice channel");
						tc.sendMessage(j.build()).queue();
						return;
					}
					if(am.getConnectedChannel().getName().equalsIgnoreCase(event.getMember().getVoiceState().getChannel().getName())){
						j.setTitle(":sweat_smile: I'm allready here");
						j.setDescription("You don't see me ?");
						tc.sendMessage(j.build()).queue();
						return;
					}
					j.setTitle(":cry: I'm connected to other channel");
					j.setDescription("First i need leave from here");
					tc.sendMessage(j.build()).queue();
					return;
				}
				
				GuildVoiceState vs = event.getMember().getVoiceState();
				
				if(!vs.inVoiceChannel()){
					j.setTitle(":sweat_smile: You are not connected to a voice channel");
					j.setDescription("First join to a voice channel");
					tc.sendMessage(j.build()).queue();
					return;
				}
				
				VoiceChannel  vc = vs.getChannel();
				Member sm = event.getGuild().getSelfMember();
				if(!sm.hasPermission(vc,Permission.VOICE_CONNECT)){
					j.setTitle(":cry: I dont have permision to connect to a voice channel");
					j.setDescription("say to owner or an administrator to give me this permision");
					tc.sendMessage(j.build()).queue();
					return;
				}
				am.openAudioConnection(vc);
				j.setTitle(":smile: Joining your voice channel");
				j.setDescription("Let's play some music");
				tc.sendMessage(j.build()).queue();
	}
	
	private EmbedBuilder EmbedBuilder() {
		EmbedTemplate et = new EmbedTemplate();
		et.Footer().RC();
		return et.getEmbedBuilder();
	}
	
	@Override
	public void handle(CommandContext ctx) {
		JoinCommand(ctx.getEvent());
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "join";
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "Join in your voice channel";
	}

	@Override
	public String getAliase() {
		// TODO Auto-generated method stub
		return getName();
	}

}
