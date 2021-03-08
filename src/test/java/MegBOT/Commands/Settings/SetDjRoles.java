package MegBOT.Commands.Settings;

import java.util.ArrayList;
import java.util.List;

import MegBOT.Main;
import MegBOT.CommandControlCenter.CommandContext;
import MegBOT.CommandControlCenter.ICommand;
import MegBOT.Utils.EmbedTemplate;
import MegBOT.Utils.GuildSettings;
import MegBOT.Utils.GuildSettingsLoader;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;

public class SetDjRoles implements ICommand {

	@Override
	public void handle(CommandContext ctx) {

		if (!(ctx.getEvent().getMember().getPermissions().contains(Permission.MANAGE_CHANNEL))) {
			return;
		}

		String msg = ctx.getEvent().getMessage().getContentRaw();
		EmbedBuilder eb = EmbedBuilder();
		
		
		if (ctx.getEvent().getMessage().getMentionedRoles().size()<1) {
			eb.setTitle(":anger: Provide Roles");
			eb.setDescription(getHelp());
			ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
			return;
		}

		msg.replace(getName(), "");
		List<Role> lr = ctx.getEvent().getMessage().getMentionedRoles();
		if (lr != null)
			if (lr.size() > 0)
				lr = ctx.getEvent().getMessage().getMentionedRoles();
			else {
				eb.setTitle(":anger: Pls Mention the role/roles for DJMode");
				ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
			}
		else {
			eb.setTitle(":anger: Pls Mention the role/roles for DJMode");
			ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
		}
		List<Role> roles = new ArrayList<>();

		boolean ok=false;
		
		if (Main.gs.containsKey(ctx.getGuild().getIdLong())) {
			if (Main.gs.get(ctx.getGuild().getIdLong()).getDjRoles()!=null) {
				roles = Main.gs.get(ctx.getGuild().getIdLong()).getDjRoles();
				ok = true;
			}
		}
		
		for (Role r : lr) {
			if (!ctx.getGuild().getRoles().contains(r)) {
				eb.setTitle(":anger: Pls Mention the role/roles for DJMode");
				ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
				return;
			}
			if(!roles.contains(r)){
				roles.add(r);
				if(ok)
				Main.gs.get(ctx.getGuild().getIdLong()).addDjRole(r);
			}
		}
		if(!ok) {
			Main.gs.put(ctx.getGuild().getIdLong(), new GuildSettings(roles,ctx.getGuild().getIdLong()));
		}
		GuildSettings gs = new GuildSettings(lr, ctx.getGuild().getIdLong());
		GuildSettingsLoader gsl = new GuildSettingsLoader();
		gsl.save(gs);

		eb.setTitle(":white_check_mark:  Saved");
		ctx.getEvent().getChannel().sendMessage(eb.build()).queue();
		return;

	}

	private EmbedBuilder EmbedBuilder() {
		EmbedTemplate et = new EmbedTemplate();
		et.Footer().RC();
		return et.getEmbedBuilder();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "setdjroles";
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return getName() + " (role1) (role2) ...";
	}

	@Override
	public String getAliase() {
		return getName();
	}

}
