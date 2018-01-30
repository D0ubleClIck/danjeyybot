package cmd.general;

import cmd.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;

public class cmd_help implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {
        PrivateChannel pc = event.getAuthor().openPrivateChannel().complete();
        pc.sendMessage("Hey "+event.getAuthor().getName()+"! You need help? Here is a list of all commands:").queue();

        pc.sendMessage(new EmbedBuilder().setColor(Color.MAGENTA)
        .setDescription("Commands:\n\n" +
                "Follow the link --> https://github.com/D0ubleClIck/danjeyybot/blob/master/README.md").build()).queue();



    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}
