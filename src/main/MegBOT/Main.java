import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public static class Main {

    public static JDA jda;
    public static String prefix = "$";

    public static void main(String[] args) throws LoginException {
        Thread console = new MainConsole();
        jda = JDABuilder.createDefault("Njk4NDM2MDAwNzQxMDY0NzE1.XpFzNg._-BNjj96sdUYkFOA8sUqablgA6o").addEventListeners(new Listener()).build();
    }
}