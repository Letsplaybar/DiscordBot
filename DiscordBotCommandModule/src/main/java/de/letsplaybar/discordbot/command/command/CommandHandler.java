package de.letsplaybar.discordbot.command.command;

import de.letsplaybar.discordbot.sql.SQLModule;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;

/**
 * @author Letsplaybar
 *         Created on 09.03.2017.
 */
public class CommandHandler {

    public static final CommandParser parse = new CommandParser();
    public static HashMap<String,Command>commands = new HashMap<>();

    /**
     * handelt das aufrufen des Commands
     * @param cmd {@link CommandParser.CommandContainer} enthält alle teile die der command braucht
     * @throws ParseException
     */
    public static void handleCommand(CommandParser.CommandContainer cmd) throws ParseException {
        try {
            GuildMessageReceivedEvent eventGuild = (cmd.event instanceof GuildMessageReceivedEvent)? (GuildMessageReceivedEvent) cmd.event : null ;
            PrivateMessageReceivedEvent eventPrivate = (cmd.event instanceof PrivateMessageReceivedEvent)? (PrivateMessageReceivedEvent) cmd.event : null ;
            if(commands.containsKey(cmd.invoke)&&
                    ( eventGuild != null && SQLModule.getInstance().hasPermission(eventGuild.getAuthor().getId(),commands.get(cmd.invoke).getPerm())||
                            eventPrivate != null && SQLModule.getInstance().hasPermission(eventPrivate.getAuthor().getId(),commands.get(cmd.invoke).getPerm()))){
                boolean safe = commands.get(cmd.invoke).called(cmd.args,eventGuild,eventPrivate);
                if(!safe){
                    try {
                        if(eventGuild != null)
                            eventGuild.getChannel().sendTyping().queue();
                        if(eventPrivate != null)
                            eventPrivate.getChannel().sendTyping().queue();
                        commands.get(cmd.invoke).action(cmd.args,eventGuild,eventPrivate);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    commands.get(cmd.invoke).executed(safe,eventGuild,eventPrivate);
                }else{
                    commands.get(cmd.invoke).executed(safe,eventGuild,eventPrivate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}