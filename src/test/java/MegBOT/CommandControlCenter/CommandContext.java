package MegBOT.CommandControlCenter;


import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class CommandContext implements ICommandContext {
    private final GuildMessageReceivedEvent event;
    private final List<String> args;
    private final String prefix;

    public CommandContext(GuildMessageReceivedEvent event2, List<String> args,String prefix) {
        this.event = event2;
        this.args = args;
        this.prefix=prefix;
    }

    public Guild getGuild() {
        return this.getEvent().getGuild();
    }

    public GuildMessageReceivedEvent getEvent() {
        return this.event;
    }

    public List<String> getArgs() {
        return this.args;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
}
