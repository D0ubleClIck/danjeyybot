package cmd.general;

import cmd.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Statics;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;

public class cmd_changePerfix implements Command{
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {
        if(!event.getAuthor().getName().equals(Statics.USER_OWNER_DANIEL)){
            event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.RED)
            .setDescription(":warning: **WARNING**\nOnly my Owner "+
                    Statics.USER_OWNER_DANIEL+" is allowed to change My Keysymbol!").build()).queue();
            return;
        }
        if(args.length < 1){
            event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.MAGENTA)
            .setDescription(help()).build()).queue();
            return;

        }

        event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.MAGENTA)
        .setDescription(":ballot_box_with_check: **Keysmbol changed**\n\n" +
                "**"+Statics.KeySymbol+"** -----> **"+args[0]+"**").build()).queue();

        Statics.KeySymbol = args[0];



    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return ":warning: **USAGE OF KEYWORD**\n`-keyword <new perfix>`";
    }
}
