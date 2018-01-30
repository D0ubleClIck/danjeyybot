package cmd.general;

import cmd.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * @author Daniel Lambrecht
 * @version 1.0.0
 * */

public class cmd_ping implements Command {

    private static long inputTime;
    public static void setInputTime(long inputTimeLong) {
        inputTime = inputTimeLong;
    }

    private Color getColorByPing(long ping) {
        if (ping < 100)
            return Color.cyan;
        if (ping < 400)
            return Color.green;
        if (ping < 700)
            return Color.yellow;
        if (ping < 1000)
            return Color.orange;
        return Color.red;
    }
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

        long processing = new Date().getTime() - inputTime;
        long ping = event.getJDA().getPing();

        //ping wird ausgegeben
        event.getTextChannel().sendMessage(new EmbedBuilder().setColor(getColorByPing(ping))
                .setDescription("**:signal_strength: Ping Informations:**\n\n Your ping: "+ping).build()).queue();
    }

    /**
     * Executed wird ausgeführt wenn der command ausgeführt wurde
     *
     * @param event
     * @param success*/

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        System.out.println("command ping executed");

    }
    /**
     * help dient als hilfsnachricht wenn der command falsche argumente enthält
     * Da hier keine args benötigt werden ist der rückgabewert null(methode wird nicht benutzt)
     **/
    @Override
    public String help() {
        return null;
    }
}
