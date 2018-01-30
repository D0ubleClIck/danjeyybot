package cmd.general;

import cmd.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Statics;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;

public class cmd_actualPerfix implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {
        event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.MAGENTA)
        .setDescription("**CURRENT Keysymbol**\n\n" +
                "`"+ Statics.KeySymbol+"`").build()).queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}
