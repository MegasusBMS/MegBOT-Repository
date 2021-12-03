package MegBOT.Rust;

import java.util.List;

import net.dv8tion.jda.api.entities.Message;

public class RustNavigatorReactElement {
	public List<RustServerStats> rustservers;
	public int time;
	public Message msg;
	
	public RustNavigatorReactElement(List<RustServerStats> rs,Message msg) {
		rustservers=rs;
		time = 1;
		this.msg=msg;
	}

	public void setMsg(Message msg) {
		this.msg = msg;
	}
	
}
