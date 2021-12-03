package MegBOT.Commands.Settings;

import MegBOT.Main;
import MegBOT.CommandControlCenter.CommandContext;
import MegBOT.CommandControlCenter.ICommand;
import MegBOT.Utils.EmbedTemplate;
import MegBOT.Utils.GuildSettingsLoader;
import MegBOT.Utils.PrefixSettings;
import MegBOT.Utils.PrefixSettingsLoader;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

public class SetPrefix implements ICommand{

	@Override
	public void handle(CommandContext ctx) {
		
		if (!(ctx.getEvent().getMember().getPermissions().contains(Permission.MANAGE_CHANNEL))) {
			return;
		}
		
		String[] arg = ctx.getEvent().getMessage().getContentRaw().toLowerCase().split(" ");
		EmbedBuilder eb = EmbedBuilder();
		
		if(arg.length>2) {
			eb.setTitle(":anger: Provide just one argument");
			ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
			return;
		}
		
		PrefixSettings ps = new PrefixSettings();
		
		if(!ps.isValid(arg[1])) {
			eb.setTitle(":anger: Invalid Prefix");
			eb.setDescription("**Vladi Prefix:** ! $ % & * + = ~ > < . ^");
			ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
			return;
		}
		
		ps = new PrefixSettings(ctx.getGuild().getIdLong(), arg[1]);
		
		if(Main.prefix.containsKey(ctx.getGuild().getIdLong())) {
			Main.prefix.get(ctx.getGuild().getIdLong()).setPrefix(arg[1]);
		}else {
			Main.prefix.put(ctx.getGuild().getIdLong(), ps);
		}
		PrefixSettingsLoader psl = new PrefixSettingsLoader();
		
		eb.setTitle(":white_check_mark:  Prefix set : " + arg[1]);
		ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
		
		psl.save(ps);
	}

	private EmbedBuilder EmbedBuilder() {
		EmbedTemplate et = new EmbedTemplate();
		et.Footer().RC();
		return et.getEmbedBuilder();
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "setprefix";
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "Set Prefix";
	}

	@Override
	public String getAliase() {
		return getName();
	}

}
