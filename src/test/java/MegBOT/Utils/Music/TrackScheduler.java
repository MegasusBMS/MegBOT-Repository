package MegBOT.Utils.Music;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import MegBOT.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.managers.AudioManager;

/**
 * This class schedules tracks for the audio player. It contains the queue of tracks.
 */
public class TrackScheduler extends AudioEventAdapter {
	private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;
    private final Guild g;
    public boolean repeating = false;
    public boolean repeatone = false;
    public boolean repeatqueue = false;
    public boolean djmode = false;
    
    /**
     * @param player The audio player this scheduler uses
     */
    public TrackScheduler(AudioPlayer player,Guild g) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
        this.g = g;
    }

    /**
     * Add the next track to queue or play right away if nothing is in the queue.
     *
     * @param track The track to play or add to queue.
     */
    public void queue(AudioTrack track) {
        // Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
        // something is playing, it returns false and does nothing. In that case the player was already playing so this
        // track goes to the queue instead.
        if (!player.startTrack(track, true)) {
            queue.offer(track);
        }
    }
    
	public void queueclear() {
		queue.clear();
	}
    
	public BlockingQueue<AudioTrack> getQueue() {
		return queue;
	}

    /**
     * Start the next track, stopping the current one if it is playing.
     */
    public void nextTrack() {
        // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
        // giving null to startTrack, which is a valid argument and will simply stop the player.
    	
        player.startTrack(queue.poll(), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
        if (endReason.mayStartNext) 
        	
        	if(this.djmode) {
        		AudioManager am = this.g.getAudioManager();
				PlayerManager playerManager = PlayerManager.getInstance();
				GuildMusicManager musicManager = playerManager.getGuildMusicManager(this.g);
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
        	
        	if(this.repeating||this.repeatone) {
        		this.player.startTrack(track.makeClone(), false);
        		this.repeatone = false;
        		return;
        	}
        	if(this.repeatqueue) {
        		queue.add(track);
        	}
            nextTrack();
    }
}