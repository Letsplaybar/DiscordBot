package de.letsplaybar.discordbot.commandline.command;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;

/**
 * @author Letsplaybar
 *         Created on 09.03.2017.
 */
public interface Command {

    boolean called(String[] args);
    void action(String[] args ) throws ParseException, IOException;
    void executed(boolean success);
    String help();
}
