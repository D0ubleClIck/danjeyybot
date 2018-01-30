package cmd.admin;

import cmd.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;

public class restartBot implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {
        if(!core.Permission.isAllowed(event)) {
            core.Permission.showErrorMSG(event);
            return;
        }
        event.getTextChannel().sendMessage(":warning: Bot will restart now...").queue();

        if (System.getProperty("os.name").toLowerCase().contains("linux")) //wenn es sich um ein linux system handelt
            Runtime.getRuntime().exec("screen sudo python restart.py");
        else
            Runtime.getRuntime().exec("cmd.exe -restart"); //wenn nicht

        System.exit(0);



    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}
