package cmd.general;

import cmd.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Statics;

import java.awt.*;

public class command_info implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage(
                new EmbedBuilder()
                        .setColor(Color.MAGENTA)
                        .setThumbnail("https://raw.githubusercontent.com/D0ubleClIck/danjeyybot/master/img/danjeyybot_logo.png")
                        .addField("General:", "Current version:\n"+Statics.version+"\n\n" +
                                "Available commands:\n(@everyone) "+Statics.commands_insgesamt+"\n"+
                                "Language support: :flag_gb: English\n\n" , true)
                        .addField("Copyright:", "coded by Danjeyy\n©Daniel Lambrecht 2018\n**Apache License-2.0**", true)
                        .addField("Github: ", "view code: https://github.com/D0ubleClIck/danjeyybot\n"
                                ,true).build()).queue();

    }
    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        System.out.println("command action executed");


    }

    @Override
    public String help() {
        return null;
    }
}
