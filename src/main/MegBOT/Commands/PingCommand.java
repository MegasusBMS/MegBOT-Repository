package Commands;
import net.dv8tion.jda.api.JDA;

public class PingCommand implements ICommand{

    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getEvent().getJDA();
        jda.getRestPing().queue(

                (ping) -> ctx.getEvent().getChannel().sendMessage("Ping: " + ping).queue()


        );
    }

    @Override
    public String getName() {
        return KiraBOT.prefix+"ping";
    }

    @Override
    public String getHelp() {
        return "Show the ping!";
    }

    @Override
    public String getAliase() {
        return KiraBOT.prefix+"p";
    }

}

