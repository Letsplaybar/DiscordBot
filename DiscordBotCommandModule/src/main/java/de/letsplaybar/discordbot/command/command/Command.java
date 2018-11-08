package de.letsplaybar.discordbot.command.command;

import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;

/**
 * @author Letsplaybar
 *         Created on 09.03.2017.
 */
public interface Command {

    /**
     * zum checken ob alles Funktioniert false == es kalppt alles
     * @param args Argumente des Commands
     * @param eventGuild Event wo der cmd aufgerufen wurde
     * @return
     */
    boolean called(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat);

    /**
     * hier wird der Command ausgeführt wenn called = false
     * @param args Argumente des Commands
     * @param eventGuild Event wo der cmd aufgerufen wurde
     * @throws ParseException
     * @throws IOException
     */
    void action(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) throws ParseException, IOException;

    /**
     * zum ausgeben von Nachrichten
     * @param success ob alles geklappt hat
     * @param eventGuild Event wo der cmd aufgerufen wurde
     */
    void executed(boolean success, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat);

    /**
     * gibt die hilfe nachricht wieder
     * @return
     */
    String help();

    /**
     * gibt die benötigte perm aus
     * @return
     */
    default String getPerm(){
        return "";
    }
}
