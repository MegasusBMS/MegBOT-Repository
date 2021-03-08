package MegBOT.Commands.Other;

import MegBOT.CommandControlCenter.CommandContext;
import MegBOT.CommandControlCenter.ICommand;
import net.dv8tion.jda.api.JDA;

public class PingCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		JDA jda = ctx.getEvent().getJDA();
		jda.getRestPing().queue(

				(ping) -> ctx.getEvent().getChannel().sendMessage("Ping: " + ping).queue());
		
	}


    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getHelp() {
        return "Show the ping!";
    }

    @Override
    public String getAliase() {
    	return getName();
    }


}
