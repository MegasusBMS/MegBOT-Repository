package MegBOT.Console;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleListener extends Thread implements Runnable {
	
	
	public ConsoleListener() {}
	
	@Override
	public void run() 
	{
		while (true)
		{
			try
			{
				BufferedReader reader =  
		                   new BufferedReader(new InputStreamReader(System.in)); 
		        String text="";
		        text = reader.readLine(); 
		        new ConsoleCommandControlCenter(text.split(" "));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}