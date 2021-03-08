package MegBOT.Utils;

import java.awt.Color;

import MegBOT.Main;
import net.dv8tion.jda.api.EmbedBuilder;

public class EmbedTemplate {
	
	EmbedBuilder eb;
	
	public EmbedTemplate() {
		eb = new EmbedBuilder();
		
	}
	
	public EmbedTemplate RC() {
	eb.setColor(new Color((int)(Math.random() * 0x1000000)));
	return this;
	}
	
	public EmbedTemplate Footer(){
		eb.setFooter("{Megbot "+Main.version+"} | ©Megasus#4837 | "+Main.jda.getGuilds().size()+" Servers");
		return this;
	}
	
	public EmbedBuilder getEmbedBuilder() {
		return eb;
	}
} 
