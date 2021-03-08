package MegBOT.Utils;

public class LolUtils {

	public LolUtils() {
	}

	public String masteryPointrsConvertor(long points) {
		String pts = "";
		while (points != 0) {
			if (pts.length() == 3 || pts.length() == 7 || pts.length() == 11) {
				pts = "," + pts;
			}
			pts = points % 10 + pts;
			points = points / 10;
		}
		return pts+" pts";
	}
}
