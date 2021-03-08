package MegBOT.Utils;

import java.util.ArrayList;
import java.util.List;

import MegBOT.Main;
import MegBOT.ConsoleCommands.LoadCommand;
import MegBOT.Utils.Music.GuildMusicManager;
import MegBOT.Utils.Music.PlayerManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.managers.AudioManager;

public class LeaveChannel extends Thread implements Runnable {

	List<Guild> runtime;
	int x = 0;
	@Override
	public void run() {
		while (true) {
			if(x==144) {
				new LoadCommand();
				x=0;
			}else
				x++;
			runtime = new ArrayList<>();
			for (Guild g : Main.vc) {
				AudioManager am = g.getAudioManager();
				PlayerManager playerManager = PlayerManager.getInstance();
				GuildMusicManager musicManager = playerManager.getGuildMusicManager(g);
				if (am.getConnectedChannel().getMembers().size() <= 1) {
					musicManager.player.stopTrack();
					musicManager.scheduler.queueclear();
					musicManager.scheduler.djmode=false;
					am.closeAudioConnection();
					runtime.add(g);
				}
				if (musicManager.player.getPlayingTrack()==null) {
					musicManager.player.stopTrack();
					musicManager.scheduler.queueclear();
					musicManager.scheduler.djmode=false;
					am.closeAudioConnection();
					runtime.add(g);
				}
				if(musicManager.scheduler.djmode) {
					boolean x=false;
					for(Member m : am.getConnectedChannel().getMembers()) {
						if(m.getId()!="603476324195237908" &&( m.getRoles().stream().anyMatch(element -> Main.gs.get(g.getIdLong()).getDjRoles().contains(element))||m.getPermissions().contains(Permission.MANAGE_CHANNEL)	) ) {
							x=true;
							break;
						}
					}
					if(!x) {
						musicManager.scheduler.djmode=false;
					}
				}
			}
			for (Guild g : runtime) {
				Main.vc.remove(g);
			}
			
			try {
				sleep(100 * 60 * 10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
