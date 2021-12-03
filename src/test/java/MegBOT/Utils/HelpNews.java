package MegBOT.Utils;

import net.dv8tion.jda.api.entities.Message;

public class HelpNews {
	public Message msg;
	public int time;
	
	public HelpNews(Message msg) {
		this.msg=msg;
		time = 0;
	}
}
