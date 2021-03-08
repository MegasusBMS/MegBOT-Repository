package MegBOT.Commands;

import MegBOT.CommandControlCenter.CommandContext;
import MegBOT.CommandControlCenter.ICommand;
import MegBOT.Utils.EmbedTemplate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

public class HelpCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		EmbedBuilder eb = EmbedBuilder();
		eb.setTitle(":page_with_curl: **Commands:**");
		eb.appendDescription(
						":musical_note: **Music:**"+"\n" +
						":pushpin: play **->** Play music" +"\n" +
						":pushpin: join \t**->** Join in your VoiceChannel" +"\n" +
						":pushpin: leave \t**->** Leave from your VoiceChannel" +"\n" +
						":pushpin: skip \t**->** Play next track" +"\n" +
						":pushpin: pause \t**->** Pause music" +"\n" +
						":pushpin: resume \t**->** Resume music"+"\n" +
						":pushpin: queue \t**->** Show queue"+"\n" +
						":pushpin: nowplay \t**->** Show playing track info" +"\n" +
						":pushpin: repeat \t**->** Repeat curent track" +"\n" +
						":pushpin: again \t**->** Repeat curent track one time"+"\n" +
						":pushpin: repeat queue \t**->** Repeat queue"+"\n" +
						":pushpin: djmode \t**->** Allow just members with DJRole to use music commands"+"\n"+
						":bangbang: This command is in **BETA** pls report all problems :bangbang:"+"\n"+
						"\n" +
						":rofl: **Fun:**" +"\n" +
						":pushpin: meme \t**->** A meme command :)" +"\n" +
						"\n" +
						"<:CHALANGER:796371531148034058> **League of Legends:**" +"\n" +
						":pushpin: lol profile \t**->** Show lol profile"+"\n" +
						":pushpin: lol mastery \t**->** Show lol masteries" +"\n" +
						":pushpin: lol register \t**->** Link your lol account to your Discord ID"+"\n" +
						":pushpin: lol unregister \t**->** Unlink your lol account" + "\n" +"\n" +
						(ctx.getEvent().getMember().hasPermission(Permission.MANAGE_CHANNEL) ? ":gear: **Settings:**" + "\n"
								+ ":pushpin: setdjroles \t**->** Set roles for DjMode" + "\n"
								+ ":pushpin: setprefix \t**->** Set prefix for this Guild" +"\n"
								:""));
		
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
		return "help";
	}

	@Override
	public String getHelp() {
		return "Help!!!";
	}

	@Override
	public String getAliase() {
		return getName();
	}

}
