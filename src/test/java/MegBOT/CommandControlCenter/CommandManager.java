package MegBOT.CommandControlCenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import MegBOT.Main;
import MegBOT.Commands.HelpCommand;
import MegBOT.Commands.Lol.LolRegisterCommand;
import MegBOT.Commands.Lol.LolUnregisterCommand;
import MegBOT.Commands.Lol.lolLiveCommand;
import MegBOT.Commands.Lol.lolMasteryCommand;
import MegBOT.Commands.Lol.lolProfileCommand;
import MegBOT.Commands.Music.BissCommand;
import MegBOT.Commands.Music.DjModeCommand;
import MegBOT.Commands.Music.JoinCommand;
import MegBOT.Commands.Music.NowPlayCommand;
import MegBOT.Commands.Music.PauseCommand;
import MegBOT.Commands.Music.PlayCommand;
import MegBOT.Commands.Music.QueueCommand;
import MegBOT.Commands.Music.RepeatCommand;
import MegBOT.Commands.Music.RepeatQueueCommand;
import MegBOT.Commands.Music.SkipCommand;
import MegBOT.Commands.Music.LeaveCommand;
import MegBOT.Commands.Other.MemeCommand;
import MegBOT.Commands.Other.PingCommand;
import MegBOT.Commands.Settings.SetDjRoles;
import MegBOT.Commands.Settings.SetPrefix;
import MegBOT.Utils.Music.GuildMusicManager;
import MegBOT.Utils.Music.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();
    
    public CommandManager() {
    	addCommand(new lolProfileCommand());
       // addCommand(new PingCommand());
        addCommand(new MemeCommand());
        addCommand(new lolMasteryCommand());
        addCommand(new LolRegisterCommand());
        addCommand(new LolUnregisterCommand());
        addCommand(new lolLiveCommand());
        addCommand(new SkipCommand());
        addCommand(new LeaveCommand());
        addCommand(new QueueCommand());
        addCommand(new NowPlayCommand());
        addCommand(new RepeatQueueCommand());
        addCommand(new BissCommand());
        addCommand(new RepeatCommand());
        addCommand(new PauseCommand());
        addCommand(new PlayCommand());
        addCommand(new JoinCommand());
        addCommand(new SetDjRoles());
        addCommand(new DjModeCommand());
        addCommand(new SetPrefix());
        addCommand(new HelpCommand());
    }

    private void addCommand(ICommand cmd) {
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound) {
            throw new IllegalArgumentException("A command with this name is already present");
        }

        commands.add(cmd);
    }

    public List<ICommand> getCommands() {
        return commands;
    }

    @Nullable
    public ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();
        for (ICommand cmd : this.commands) {
            if (searchLower.startsWith(cmd.getName()) || searchLower.startsWith(cmd.getAliase())) {
                return cmd;
            }
        }

        return null;
    }

    void handle(GuildMessageReceivedEvent event,String prefix) {
        String invoke = event.getMessage().getContentRaw();
        String[] split = invoke.split(" ");
        
        if(invoke.startsWith(prefix+"stop")) {
        	invoke.replace(prefix+"stop", Main.prefix+"pause");
        }
        
        ICommand cmd = this.getCommand(invoke.substring(1));

        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args,prefix);

            cmd.handle(ctx);
        }
    }
}

