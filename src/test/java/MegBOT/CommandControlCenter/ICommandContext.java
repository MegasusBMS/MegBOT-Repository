package MegBOT.CommandControlCenter;

import java.util.List;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface ICommandContext {

    Guild getGuild();

    GuildMessageReceivedEvent getEvent();

    List<String> getArgs();
}
