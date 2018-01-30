
package cmd.chat;
import cmd.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Statics;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Daniel Lambrecht 2018
 * @version 1.0.0
 * */

public class cmd_clear implements Command {

    /**
     * @param string
     * Method convertiert die anzahl (args 1) zu einem Zahlenwert
     * */
    private int convertToInteger(String string){
        try{
            return Integer.parseInt(string);
        }catch (Exception exc){
            System.out.println("missing argument");
            return 0;
        }

    }
    /**
     *@param args
     *@param event
     * Called method
     * */
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;

    }
    /**
     * function behandelt den geamten ablauf des löschens
     *
     * @param args
     * @param event
     * @throws ParseException
     * @throws IOException
     * */
    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {
        if (Statics.ignore  == true) {
            return;
        } else {
            if (args.length < 1) {
                event.getTextChannel().sendMessage(
                        new EmbedBuilder()
                                .setColor(ColorUIResource.RED)
                                .setDescription(help()).build())
                        .queue();
            } else {
                int numb = convertToInteger(args[0]);
                if (numb > 1 && numb <= 100) {
                    try {
                        MessageHistory history = new MessageHistory(event.getTextChannel());
                        List<Message> msg;

                        event.getMessage().delete().queue();
                        msg = history.retrievePast(numb).complete();
                        event.getTextChannel().deleteMessages(msg).queue();

                        Message message = event.getTextChannel().sendMessage(new EmbedBuilder()
                                .setColor(Color.MAGENTA)
                                .setDescription("deleting `"+args[0]+"` messages successful!")
                                .build()).complete();

                        new Timer().schedule(new TimerTask() { //nach 3 sekunden wird der Embed gelöscht
                            @Override
                            public void run() {
                                message.delete().queue();
                            }
                        }, 3000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    //wenn das argument nummer größer als 100 und kleiner als 2 ist
                    event.getTextChannel().sendMessage(new EmbedBuilder()
                            .setColor(Color.RED)
                            .setDescription(":warning: **You can only clear messages between 2 and 100!**").build())
                            .queue();
                }
            }

        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        System.out.println("[INFO] command <del> executed");
    }
    @Override
    public String help() {
        return ":warning: **USAGE OF CLEAR**\n `-clear <number>`";
    }
}