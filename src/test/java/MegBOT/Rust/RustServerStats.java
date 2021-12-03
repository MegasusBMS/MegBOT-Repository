package MegBOT.Rust;

public class RustServerStats {

	public String name;
	public String ip;
	public int port;
	public int players;
	public int maxPlayers;
	public String status;
	public long uptime;
	public String lastWipe;
	public boolean moded;
	public boolean official;
	public String description;
	public boolean pvp;
	public String mapImageUri;
	public Long mapsize;
	public int queue;
	public String serverImageUri;
	public String serverSite;
	public String country;
	public Long id;
	public float fps;
	public float fpsAvg;
	
	public RustServerStats() {
		}

	public void setName(String name) {
		this.name = name;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setPlayers(int players) {
		this.players = players;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUptime(long uptime) {
		this.uptime = uptime;
	}

	public void setLastWipe(String lastWipe) {
		this.lastWipe = lastWipe;
	}

	public void setModed(boolean moded) {
		this.moded = moded;
	}

	public void setOfficial(boolean official) {
		this.official = official;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPvp(boolean pvp) {
		this.pvp = pvp;
	}

	public void setMapImageUri(String mapImageUri) {
		mapImageUri=mapImageUri.replace("Thumbnail","FullLabeledMap");
		this.mapImageUri = mapImageUri;
	}

	public void setMapsize(Long mapsize) {
		this.mapsize = mapsize;
	}

	public void setQueue(int queue) {
		this.queue = queue;
	}

	public void setServerImageUri(String serverImageUri) {
		this.serverImageUri = serverImageUri;
	}

	public void setServerSite(String serverSite) {
		this.serverSite = serverSite;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setFps(float fps) {
		this.fps = fps;
	}
	
	public void setFpsAvg(float fpsAvg) {
		this.fpsAvg=fpsAvg;
	}
}
