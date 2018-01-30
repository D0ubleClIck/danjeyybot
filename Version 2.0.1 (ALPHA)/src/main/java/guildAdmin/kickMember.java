package guildAdmin;

import cmd.Command;
import core.Permission;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;

import static core.Permission.isAllowed;

public class kickMember implements Command {


    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {
        //wenn die Rolle botOwner nicht gefunden wird kann der command nicht ausgeführt werden
        if(!isAllowed(event)){
            Permission.showErrorMSG(event);
            return;
        }

        String reason = "none";
        if (args.length > 1) {
            reason = args[1];
        }else{
            event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.MAGENTA)
            .setDescription("**USAGE OF KICK**\n`-kick @user <reason>`").build()).queue();
            return;
        }
        event.getTextChannel().sendMessage(
                ":triangular_flag_on_post:   " +
                        event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0)).getAsMention()
                        + " got **kicked** by " + event.getAuthor().getAsMention() + " (" + event.getMember().getRoles().get(0).getName() + ").\n\n" +
                        "       Reason: " + reason
        ).queue();


        try {
            PrivateChannel pc = event.getMessage().getMentionedUsers().get(0).openPrivateChannel().complete();
            pc.sendMessage(
                    "Sorry, you got kicked from Server " +
                            event.getGuild().getName() + " by " + event.getAuthor().getAsMention()
                            + " (" + event.getMember().getRoles().get(0).getName() + ").\n\n" +
                            "Reason: " + reason

            ).queue();
        }catch (Exception exc){
            exc.printStackTrace();
        }


        event.getGuild().getController().kick(
                event.getGuild().getMember(
                        event.getMessage().getMentionedUsers().get(0))).queue();


    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}

