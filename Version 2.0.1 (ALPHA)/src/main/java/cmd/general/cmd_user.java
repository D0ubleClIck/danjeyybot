package cmd.general;

import cmd.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;

public class cmd_user implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {

        Member member;

        if (args.length > 0) {
            member = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
        } else {
            member = event.getMember();
        }

        String NAME = member.getEffectiveName();
        String TAG = member.getUser().getName() + "#" + member.getUser().getDiscriminator();
        String GUILD_JOIN_DATE = member.getJoinDate().format(DateTimeFormatter.RFC_1123_DATE_TIME);
        String DISCORD_JOINED_DATE = member.getUser().getCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME);
        String ID = member.getUser().getId();
        String STATUS = member.getOnlineStatus().getKey();
        String ROLES = "";
        String GAME;
        String AVATAR = member.getUser().getAvatarUrl();

        try {
            GAME = member.getGame().getName();
            //LEVEL = member.getGuild().getExplicitContentLevel().toString();
        } catch (Exception e) {
            GAME = "-/-";
        }

        for ( Role r : member.getRoles() ) {
            ROLES += r.getName() + ", ";
        }
        if (ROLES.length() > 0)
            ROLES = ROLES.substring(0, ROLES.length()-2);
        else
            ROLES = "No roles on this server.";

        if (AVATAR == null) {
            AVATAR = "No Avatar";
        }

        EmbedBuilder em = new EmbedBuilder().setColor(Color.MAGENTA);
        em.setDescription(":mag_right: User-Informations about **" + member.getUser().getName() +"**")
                .addField("Name/Nickname:",NAME, false)
                .addField("User Tag:", TAG, false)
                .addField("User ID:", ID, false)
                .addField("Current Online Status", STATUS, false)
                .addField("User is currently playing:", GAME, false)
                .addField("Roles on this Server:", ROLES, false)
                .addField("Guild Joined this Server:", GUILD_JOIN_DATE, false)
                .addField("User joined Discord:", DISCORD_JOINED_DATE, false)
                .addField("Avatar-URL", AVATAR, false);
        //.addField("Level", LEVEL, true);

        if (AVATAR != "No Avatar") {
            em.setThumbnail(AVATAR);
        }

        event.getTextChannel().sendMessage(
                em.build()
        ).queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}
