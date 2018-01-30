package cmd.chat;
//import
import cmd.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Daniel Lambrecht
 * @version 1.0.0
 * */

public class cmd_stups implements Command{

    /**
     * called wird aufgerufen wenn der command eingetippt wird
     *
     * @param args
     * @param event*/

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    /**
     * action verarbeitet die argumente args und das Event MessageReceived
     *
     * @param args
     * @param event */

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        String msg = String.join(" ", args).split("@")[0];
        User member = event.getMessage().getMentionedUsers().size() > 0 ?  event.getMessage().getMentionedUsers().get(0) : null;
        TextChannel channel = event.getTextChannel();

        //wenn zu wenig argumente eingegeben wurden
        if (args.length < 2 || member == null) {

            channel.sendMessage(new EmbedBuilder()
                    .setColor(Color.MAGENTA)
                    .setDescription(help()).build()).queue();
            //return sorgt dafür das der programmablauf executed wird
            return;
        }else {//wenn die nötigen argumente vorhanden sind

            //zeigt die Uhrzeit an, an dem man angestupst wurde
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            event.getMessage().delete().queue();

            //privater kanal wird geöffnet und die nachricht gesendet
            member.openPrivateChannel().queue(pc -> pc.sendMessage(
                    new EmbedBuilder()
                            .setColor(Color.MAGENTA)
                            .setTitle(sdf.format(new Date()))
                            .setDescription("Oh! User " +
                                    event.getAuthor().getAsMention() + " Nudged you!\n\n `message`:\n"+ msg.substring(0, msg.length() - 1))
                            .build()
            ).queue());
        }


    }
    /**
     * Executed wird ausgeführt wenn der command ausgeführt wurde
     *
     * @param event
     * @param success*/
    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        System.out.println("command stups executed");

    }
    /**
     * help dient als hilfsnachricht wenn der command falsche argumente enthält
     **/
    @Override
    public String help() {
        return ":warning: **USAGE OF STUPS**:\n `-stups <message> <@user>`";
    }
}
