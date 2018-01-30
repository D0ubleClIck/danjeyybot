package core;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Statics;

import java.awt.*;

/**
 * @author Daniel Lambrecht
 * @version 1.0.0*/

public class Permission {

    /**
     * überprüft ob der user allowed ist um den command zu starten
     * @param event */
    public static boolean isAllowed(MessageReceivedEvent event) {
        String watchForRole = event.getMessage().getMember().getRoles().toString();
        String getName = event.getMessage().getAuthor().getName();
        if (watchForRole.contains("BotOwner") || getName.equals(Statics.USER_OWNER_DANIEL))
            return true;
        else
            return false;
    }
    /**
     * zeigt die message auf bei zu unausreichenden permissions
     * @param event */
    public static void showErrorMSG(MessageReceivedEvent event){
        event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.MAGENTA)
                .setTitle(":warning: **Permission failed!**\n")
                .setDescription("Sorry "+event.getAuthor()
                        .getAsMention()+", but you are not allowed to use this command!\n").build()).queue();
    }
}
