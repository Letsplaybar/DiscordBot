package de.letsplaybar.discordbot.commandline.command;

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
        if(!commands.containsKey(cmd.invoke))
            return;
        boolean safe = commands.get(cmd.invoke).called(cmd.args);
        if(!safe){
            try {
                commands.get(cmd.invoke).action(cmd.args);
            } catch (IOException e) {
                e.printStackTrace();
            }
            commands.get(cmd.invoke).executed(safe);
        }else{
            commands.get(cmd.invoke).executed(safe);
        }

    }

}