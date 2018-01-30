package listeners;

import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.guild.GuildBanEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Random;

public class Member extends ListenerAdapter{
    int randomMSG;
    int bound = 4;
    String[] hello = {
            "Welcome to my house", //0
            "Oh look! a new Member",//1
            "Stage free for",//2
            "A new Member is here",//3
            "New Member! All eyes on"//4
            };
    /**
     * @param event*/
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {

        if(event.getMember().getUser().isBot()) {
            event.getGuild().getTextChannels().get(0).sendMessage("Oh look! A new bot called"
                    +event.getMember().getAsMention()+"! But... I'm better! :hugging:").queue();
            return;
        }
        randomMSG = new Random().nextInt(bound);
        switch (randomMSG){
            case 0:
                event.getGuild().getTextChannels().get(0)
                        .sendMessage(hello[0]).queue();
                break;
            case 1:
                event.getGuild().getTextChannels().get(0)
                        .sendMessage(hello[1]).queue();
                break;
            case 2:
                event.getGuild().getTextChannels().get(0)
                        .sendMessage(hello[2]).queue();
                break;
            case 3:
                event.getGuild().getTextChannels().get(0)
                        .sendMessage(hello[3]).queue();
                break;
            case 4:
                event.getGuild().getTextChannels().get(0)
                        .sendMessage(hello[4]).queue();
                break;

        }
        PrivateChannel pc = event.getMember().getUser().openPrivateChannel().complete();
        pc.sendMessage("Hey "+event.getMember().getAsMention()
                +"! Welcome on the Discord Server **"+ event.getJDA().getGuilds().get(0)+"**\n" +
                ". Ask the admin or a supporter for the rules! Have fun\n" +
                ":wave: :v: ").queue();


    }
    /**
     * @param event*/
    public void onGuildBan(GuildBanEvent event) {
        PrivateChannel privateChannel = event.getUser().openPrivateChannel().complete();
        privateChannel.sendMessage("Oh! You got banned from this Server...**"+event.getJDA().getGuilds().get(0)+"** !\n" +
                "possible reasons:\n" +
                "--> racism\n" +
                "--> threats\n" +
                "--> disregarded rules").queue();

    }
}

