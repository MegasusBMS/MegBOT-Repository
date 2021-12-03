package MegBOT.Commands;

import MegBOT.Main;
import MegBOT.CommandControlCenter.CommandContext;
import MegBOT.CommandControlCenter.ICommand;
import MegBOT.Utils.EmbedTemplate;
import MegBOT.Utils.HelpNews;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

public class HelpCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		EmbedBuilder eb = EmbedBuilder();
		eb.setTitle(":page_with_curl: **Commands:**");
		eb.appendDescription(
						":musical_note: **Music:**"+"\n" +
						":pushpin: play \t**->** Play music" +"\n" +
						":pushpin: autoplay \t**->** Play a similar mix" +"\n" +
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
						"<:rust:825669303739088917> **Rust:**\n"+
						":pushpin: rustserver [short by:(player,online)] (name)\t**->Search Rust Servers\n"+
						"\n"+
						"<:CHALANGER:796371531148034058> **League of Legends:**" +"\n" +
						":pushpin: lol profile \t**->** Show lol profile"+"\n" +
						":pushpin: lol mastery \t**->** Show lol masteries" +"\n" +
						":pushpin: lol register \t**->** Link your lol account to your Discord ID"+"\n" +
						":pushpin: lol unregister \t**->** Unlink your lol account**" + "\n" +"\n" +
						(ctx.getEvent().getMember().hasPermission(Permission.MANAGE_CHANNEL) ? ":gear: **Settings:**" + "\n"
								+ "**:pushpin: setdjroles \t**->** Set roles for DjMode" + "\n"
								+ ":pushpin: setprefix \t**->** Set prefix for this Guild**" +"\n"
								:""));
		
		ctx.getEvent().getChannel().sendMessage(eb.build()).queue(msg -> {
			msg.addReaction("📰").queue();
			if(Main.news.containsKey(ctx.getGuild().getIdLong()))
				Main.news.remove(ctx.getGuild().getIdLong());
			Main.news.put(ctx.getGuild().getIdLong(),new HelpNews(msg));
		});
		
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
