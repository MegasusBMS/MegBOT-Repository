package MegBOT.LeagueOfLegends;

public class ChampionData {
	String version;
	String id;
	String key;
	String name;
	String title;
	ChampionInfo info;
	String[] tags;
	String partype;
	
	public ChampionData(String version, String id , String key, String name, String title, ChampionInfo info, String[] tags, String partype) {
		this.version = version;
		this.id=id;
		this.key=key;
		this.name=name;
		this.title=title;
		this.info=info;
		this.tags=tags;
		this.partype=partype;
	}
	
	public ChampionData (String name) {
		ChampionsData cds=new ChampionsData(name);
		this.version = cds.getCd().version;
		this.id = cds.getCd().getId();
		this.key = cds.getCd().getKey();
		this.name = cds.getCd().getName();
		this.title = cds.getCd().getTitle();
		this.info = cds.getCd().getInfo();
		this.tags = cds.getCd().getTags();
		this.partype=cds.getCd().getPartype();
	}
	
	public ChampionData (int id) {
		ChampionsData cds=new ChampionsData(id);
		this.version = cds.getCd().version;
		this.id = cds.getCd().getId();
		this.key = cds.getCd().getKey();
		this.name = cds.getCd().getName();
		this.title = cds.getCd().getTitle();
		this.info = cds.getCd().getInfo();
		this.tags = cds.getCd().getTags();
		this.partype=cds.getCd().getPartype();
	}
	
	
	public String getVersion() {
		return version;
	}
	public String getId() {
		return id;
	}
	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public ChampionInfo getInfo() {
		return info;
	}

	public String[] getTags() {
		return tags;
	}

	public String getPartype() {
		return partype;
	}

}
