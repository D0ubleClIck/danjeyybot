package cmd.chat;

import cmd.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Statics;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class cmd_poll implements Command{
    //definitionen
    private static TextChannel channel;
    private static HashMap<Guild, Poll> polls = new HashMap<>();
    public static final String[] EMOTIE = {":one:",":two:",":three:",":four:",":five:",
            ":six:",":seven:",":eight:",":nine:",":keycap_ten:",}; //emotes 1 - 10

    private class Poll implements Serializable {
        private String creator;
        private String heading; //thema /überschrift
        private List<String> answers; //antworten .zB ja, nein, vielleicht
        private HashMap<String, Integer> votes;
        private Poll(Member creator, String heading, List<String>answers){
            this.creator = creator.getUser().getId();
            this.heading = heading;
            this.answers = answers;
            this.votes = new HashMap<>();

        }
        private Member getCreator(Guild g){
            return g.getMember(g.getJDA().getUserById(creator));
        }

    }

    private static void message (String msg){
        EmbedBuilder emb = new EmbedBuilder().setDescription(msg);
        channel.sendMessage(emb.build()).queue();
    }
    private static void message (String msg, Color color){
        EmbedBuilder emb = new EmbedBuilder().setDescription(msg).setColor(color);
        channel.sendMessage(emb.build()).queue();
    }
    private EmbedBuilder getParsedPoll(Poll poll, Guild guild){
        StringBuilder ansSTR = new StringBuilder();
        final AtomicInteger count = new AtomicInteger();

        poll.answers.forEach(s -> {
            int voteacc = poll.votes.values().stream().filter(integer1 -> integer1 - 1 == count.get()).findFirst().orElse(0);
            ansSTR.append(EMOTIE[count.get()] + " - "+s+" - votes: `"+voteacc + "` \n");
            count.addAndGet(1);

        });

        return new EmbedBuilder().setAuthor(
                poll.getCreator(guild).getEffectiveName() + "'s Poll", null, poll.getCreator(guild).getUser().getAvatarUrl())
                .setDescription(":bar_chart: "+poll.heading +"\n\n"+ansSTR.toString())
                .setFooter("Type in `"+ Statics.KeySymbol + "poll for <number>` to vote!", null)
                .setColor(Color.BLUE);
    }

    private void createPoll(String[] args, MessageReceivedEvent event){
        if(polls.containsKey(event.getGuild())){
            message("Sorry, there is currently a poll running!", Color.RED);
            return;
        }
        String argsSTR = String.join(" ", new ArrayList<>(Arrays.asList(args).subList(1, args.length)));
        List<String> content = Arrays.asList(argsSTR.split("\\|"));

        String heading = content.get(0);
        List<String> answers = new ArrayList<>(content.subList(1, content.size()));
        Poll poll = new Poll(event.getMember(), heading, answers);
        polls.put(event.getGuild(), poll);
        channel.sendMessage(getParsedPoll(poll , event.getGuild()).build()).queue();

    }
    private void votePoll(String[] args, MessageReceivedEvent event){
        if(!polls.containsKey(event.getGuild())){
            message(":warning: There is currently no poll!", Color.RED);
            return;
        }
        Poll poll = polls.get(event.getGuild());
        int vote;
        try{
            vote = Integer.parseInt(args[1]);
            if(vote > poll.answers.size())
                throw new NullPointerException();
        }catch (Exception e){
            message("`"+event.getAuthor().getName()+"Please type in a valid number", Color.RED);
            return;

        }
        if(polls.containsKey(event.getAuthor().getId())){
            message(":warning: You can only vote for once!", Color.RED);
            return;
        }else {
            poll.votes.put(event.getAuthor().getId(), vote);
            polls.replace(event.getGuild(), poll);
            //event.getMessage().delete().queue();
        }

    }

    private void VoteStats(MessageReceivedEvent event){
        Guild g = event.getGuild();
        Poll poll = polls.get(g);
        if(!polls.containsKey(event.getGuild())){
            message(":warning: There is currently no poll!", Color.RED);
            return;
        }
        if(!poll.getCreator(g).equals(event.getMember())){
            message(":warning: Only the creator of the Poll can show the poll stats -->> `["+poll.getCreator(g).getAsMention()+"]`");
            return;
        }
        channel.sendMessage(getParsedPoll(polls.get(event.getGuild()), event.getGuild()).build()).queue();

    }
    private void closeVote(MessageReceivedEvent event){
        if(!polls.containsKey(event.getGuild())){
            return;
        }
        Guild g = event.getGuild();

        Poll poll = polls.get(g);
        if(!poll.getCreator(g).equals(event.getMember())){
            message(":warning: Only the Poll creator can close the Poll! -->> `["+poll.getCreator(g).getAsMention()+"]`");

        }
        polls.remove(g);
        channel.sendMessage(getParsedPoll(poll, g).build()).queue();
        message("`Poll finished`", Color.GREEN);

    }





    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) throws ParseException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        channel = event.getTextChannel();
        if(args.length < 1){
            message(help(),Color.RED);
            return;
        }
        switch (args[0]){
            case "create":
            case "c":
                createPoll(args, event);
                event.getMessage().delete().queue();
                break;
            case "for":
            case "f":
                votePoll(args, event);
                break;
            case "stats":
            case "s":
                VoteStats(event);
                break;
            case "close":
            case "cl":
                closeVote(event);
                break;
            default: message(":warning: `Der befehl wurde nicht erkannt`", Color.RED);
        }


    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}
