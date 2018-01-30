package core;

import cmd.Music;
import cmd.admin.StopBot;
import cmd.admin.restartBot;
import cmd.chat.cmd_clear;
import cmd.chat.cmd_stups;
import cmd.general.*;
import guildAdmin.kickMember;
import listeners.commandListener;
import listeners.readyListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import util.Statics;

import javax.security.auth.login.LoginException;

/**
 *@author Daniel Lambrecht
 *@version 1.0.0*/

public class Main {

    private static JDABuilder dependencies;

    public static void main(String args[]) {
        dependencies = new JDABuilder(AccountType.BOT);
        dependencies.setStatus(OnlineStatus.ONLINE);
        dependencies.setAutoReconnect(true);
        dependencies.setGame(Game.playing(Statics.PlayingGame));
        dependencies.setToken(Statics.TOKEN);

        initalizeCommands();
        initalizeListeners();

        try{
            JDA jda = dependencies.buildBlocking();
    } catch (InterruptedException | LoginException e) {
        e.printStackTrace();
    }
}
    //Jeder command bekommt einen Alibi
    private static void initalizeCommands(){
        //General
        commandHandler.commands.put("help", new cmd_help());
        commandHandler.commands.put("h", new cmd_help());
        commandHandler.commands.put("info", new command_info());
        commandHandler.commands.put("i", new command_info());
        commandHandler.commands.put("clear", new cmd_clear());
        commandHandler.commands.put("c", new cmd_clear());
        commandHandler.commands.put("prefix", new cmd_actualPerfix());
        //music
        commandHandler.commands.put("music", new Music());
        commandHandler.commands.put("m", new Music());
        //Chat
        //commandHandler.commands.put("poll", new cmd_poll());
      //  commandHandler.commands.put("p", new cmd_poll());
        commandHandler.commands.put("userinfo", new cmd_user());
        commandHandler.commands.put("ui", new cmd_user());
        commandHandler.commands.put("ping", new cmd_ping());
        commandHandler.commands.put("p", new cmd_ping());
        commandHandler.commands.put("stups", new cmd_stups());
        commandHandler.commands.put("st", new cmd_stups());

        /*###__________Admin Usages_____________________###*/
        commandHandler.commands.put("kick", new kickMember());
        commandHandler.commands.put("keyword", new cmd_changePerfix());
        //commandHandler.commands.put("restart", new restartBot());
        //commandHandler.commands.put("r", new restartBot());
        commandHandler.commands.put("stop", new StopBot());
        commandHandler.commands.put("s", new StopBot());



    }
    private static void initalizeListeners(){
        dependencies.addEventListener(new commandListener());
        dependencies.addEventListener(new readyListener());

    }
}