package MegBOT.LeagueOfLegends;

public class ChampionInfo {
	int attack;
	int defense;
	int magic;
	int difficulty;
	public ChampionInfo(int attack,int defence,int magic,int difficulty) {
		this.attack=attack;
		this.defense=defence;
		this.magic=magic;
		this.difficulty=difficulty;
	}
	public int getAttack() {
		return attack;
	}

	public int getDefense() {
		return defense;
	}

	public int getMagic() {
		return magic;
	}

	public int getDifficulty() {
		return difficulty;
	}

}
