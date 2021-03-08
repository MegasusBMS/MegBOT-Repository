package MegBOT.Commands.Music;

import MegBOT.Main;
import MegBOT.CommandControlCenter.CommandContext;
import MegBOT.CommandControlCenter.ICommand;
import MegBOT.Utils.DjMode;
import MegBOT.Utils.EmbedTemplate;
import MegBOT.Utils.Music.GuildMusicManager;
import MegBOT.Utils.Music.PlayerManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.annotation.Nullable;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand implements ICommand {

	private YouTube YouTube = null;
	public static String input;
	public static boolean b;
	public boolean randomlist = false;
	public boolean playlist = false;

	private void PlayCommand(String[] args, GuildMessageReceivedEvent event, boolean bo) {
		TextChannel channel = event.getChannel();
		AudioManager am = event.getGuild().getAudioManager();
		TextChannel tc = event.getChannel();
		PlayerManager mm = PlayerManager.getInstance();
		GuildVoiceState vs = event.getMember().getVoiceState();
		VoiceChannel vc = vs.getChannel();
		b = bo;
		if (b) {
			if (args.length < 2) {
				EmbedBuilder play = EmbedBuilder();
				play.setTitle(":upside_down: Please provide some arguments");
				play.setDescription(getName() + " [url/yt_id/title/thumbnails]");
				channel.sendMessage(play.build()).queue();

				return;
			}

			if (!vs.inVoiceChannel()) {
				EmbedBuilder j = EmbedBuilder();
				j.setTitle(":sweat_smile: You are not connected to a voice channel");
				j.setDescription("First join to a voice channel");
				tc.sendMessage(j.build()).queue();
				return;
			}

			Member sm = event.getGuild().getSelfMember();
			if (!sm.hasPermission(vc, Permission.VOICE_CONNECT)) {
				EmbedBuilder j = EmbedBuilder();
				j.setTitle(":cry: I dont have permision to connect to a voice channel");
				j.setDescription("say to owner or an administrator to give me this permision");
				tc.sendMessage(j.build()).queue();
				return;
			}
			
			final GuildMusicManager musicManager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild());
			
			DjMode dm = new DjMode();
			
			if(musicManager.scheduler.djmode&&!dm.hasPermision(event.getMember(), event.getGuild().getIdLong())) {
				EmbedBuilder j = EmbedBuilder();
				event.getChannel().sendMessage(dm.noPermisionMessage()).queue();
				return;
			}
			if (!am.isConnected()) {
				Main.vc.add(event.getGuild());
				mm.getGuildMusicManager(event.getGuild()).player.setVolume(50);
				am.openAudioConnection(vc);
			}
		}
		YouTube temp = null;
		try {
			temp = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(),
					JacksonFactory.getDefaultInstance(), null).setApplicationName("MegBOT").build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		PlayerManager manager = PlayerManager.getInstance();
		YouTube = temp;
		if (args[1].startsWith("https://www.youtube.com/watch?v=")) {
			manager.loadAndPlay(event.getChannel(), args[1], b);
			return;
		}
		// if(args[1].startsWith("https://open.spotify.com/track/05l63xRmIhBCYmGSPFOhyE?si=")){
		// args=Spotify.Name(args[1].substring("https://open.spotify.com/track/05l63xRmIhBCYmGSPFOhyE?si=".length())).split("
		// ");
		// }

		String imput = "";
		for (int i = 1; i < args.length; i++) {
			/*
			 * if (args[i].equals("-l")) { if (!randomlist) playlist = true; } else { if
			 * (!args[i].equals("-r")) { if (i == args.length - 1) { imput = imput +
			 * args[i]; } else imput = imput + args[i] + " "; } else { if (!playlist)
			 * randomlist = true; } }
			 */
			if (i == args.length - 1) {
				imput = imput + args[i];
			} else
				imput = imput + args[i] + " ";
		}
		input = imput;

		if (!isUrl(input)) {
			String ytSearched = searchYoutube(input);
			System.out.print("Server: " + event.getGuild().getName() + " Catator: " + event.getAuthor().getName()
					+ " Cautare: " + imput + " Rezultat: " + ytSearched + "\n");

			if (ytSearched == null) {
				EmbedBuilder play = EmbedBuilder();
				play.setTitle(":thinking: I don't find this input");
				play.setDescription("Are you sure , this is what you want to play?");
				channel.sendMessage(play.build()).queue();

				return;
			}

			input = ytSearched;
		}
		manager.loadAndPlay(event.getChannel(), input, b);
	}

	private boolean isUrl(String input) {
		try {
			new URL(input);

			return true;
		} catch (MalformedURLException ignored) {
			return false;
		}
	}
	
	private EmbedBuilder EmbedBuilder() {
		EmbedTemplate et = new EmbedTemplate();
		et.Footer().RC();
		return et.getEmbedBuilder();
	}

	@Nullable
	private String searchYoutube(String input) {
		try {
			List<SearchResult> results;
			if (playlist) {
				try {
					results = YouTube.search().list("id,snippet").setQ(input).setMaxResults(1L).setType("playlist")
							.setFields("items(id/kind,id/PlayListId,snippet/title,snippet/thumbnails/default/url)")
							.setKey("AIzaSyBQOsZVBB4AFnJbvi8jCjOJqDt82e-qPOA").execute().getItems();
				} catch (GoogleJsonResponseException e) {
					try {
						results = YouTube.search().list("id,snippet").setQ(input).setMaxResults(25L).setType("playlist")
								.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
								.setKey("AIzaSyDWqLqNE3mYJnrx7S_QUrVPB1rgL0MrN6c").execute().getItems();
					} catch (GoogleJsonResponseException q) {
						try {
							results = YouTube.search().list("id,snippet").setQ(input).setMaxResults(25L)
									.setType("playlist")
									.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
									.setKey("AIzaSyBacRrWKlXMo0KO6oCrR8u2Pyq74q22YIU").execute().getItems();
						} catch (GoogleJsonResponseException w) {
							try {
								results = YouTube.search().list("id,snippet").setQ(input).setMaxResults(25L)
										.setType("playlist")
										.setFields(
												"items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
										.setKey("AIzaSyBFJwcEl-ui3HN6QCDZ3k4SIDWLeLtb6dA").execute().getItems();
							} catch (GoogleJsonResponseException a) {
								results = YouTube.search().list("id,snippet").setQ(input).setMaxResults(25L)
										.setType("playlist")
										.setFields(
												"items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
										.setKey("AIzaSyCPpG9guLtxDGBkIYfdN2zJ1vq4C_vU6Io").execute().getItems();

							}
						}
					}
				}
			} else {
				try {
					results = YouTube.search().list("id,snippet").setQ(input).setMaxResults(1L).setType("video")
							.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
							.setKey("AIzaSyBQOsZVBB4AFnJbvi8jCjOJqDt82e-qPOA").execute().getItems();
				} catch (GoogleJsonResponseException e) {
					try {
						results = YouTube.search().list("id,snippet").setQ(input).setMaxResults(1L).setType("video")
								.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
								.setKey("AIzaSyDWqLqNE3mYJnrx7S_QUrVPB1rgL0MrN6c").execute().getItems();
					} catch (GoogleJsonResponseException q) {
						try {
							results = YouTube.search().list("id,snippet").setQ(input).setMaxResults(1L).setType("video")
									.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
									.setKey("AIzaSyBacRrWKlXMo0KO6oCrR8u2Pyq74q22YIU").execute().getItems();
						} catch (GoogleJsonResponseException w) {
							try {
								results = YouTube.search().list("id,snippet").setQ(input).setMaxResults(1L)
										.setType("video")
										.setFields(
												"items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
										.setKey("AIzaSyBFJwcEl-ui3HN6QCDZ3k4SIDWLeLtb6dA").execute().getItems();
							} catch (GoogleJsonResponseException a) {
								results = YouTube.search().list("id,snippet").setQ(input).setMaxResults(1L)
										.setType("video")
										.setFields(
												"items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
										.setKey("AIzaSyCPpG9guLtxDGBkIYfdN2zJ1vq4C_vU6Io").execute().getItems();

							}
						}
					}
				}
			}
			if (!results.isEmpty()) {
				String videoId = results.get(0).getId().getVideoId();
				if (randomlist) {
					randomlist = false;
					videoId = videoId + "&list=RDWL1hlzLsUaU&start_radio=1&t=1";
				}
				return "https://www.youtube.com/watch?v=" + videoId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return input;
	}

	@Override
	public void handle(CommandContext ctx) {
		PlayCommand(ctx.getEvent().getMessage().getContentRaw().split(" "), ctx.getEvent(), true);
	}

	@Override
	public String getName() {
		return "play";
	}

	@Override
	public String getHelp() {
		return getName() + " (song/link)";
	}

	@Override
	public String getAliase() {
		return "p";
	}

}
