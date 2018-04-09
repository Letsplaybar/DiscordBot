package de.letsplaybar.discordbot.command.command;

import de.letsplaybar.discordbot.sql.SQLModule;

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

    public static void handleCommand(CommandParser.CommandContainer cmd) throws ParseException {
        try {
            if(SQLModule.getInstance().getSql().isInDatabase(cmd.event.getMember().getUser().getId(),"Permissions","Userid")|| SQLModule.getInstance().getSql().isInDatabase("*","Permissions","Userid"))
            if(commands.containsKey(cmd.invoke)){
                boolean safe = commands.get(cmd.invoke).called(cmd.args,cmd.event);
                if(!safe){
                    try {
                        cmd.event.getChannel().sendTyping().queue();
                        commands.get(cmd.invoke).action(cmd.args,cmd.event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cmd.event.getChannel().sendTyping().complete();
                    commands.get(cmd.invoke).executed(safe,cmd.event);
                }else{
                    commands.get(cmd.invoke).executed(safe,cmd.event);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}