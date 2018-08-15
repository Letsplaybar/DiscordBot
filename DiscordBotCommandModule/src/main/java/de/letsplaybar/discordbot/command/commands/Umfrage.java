package de.letsplaybar.discordbot.command.commands;

import de.letsplaybar.discordbot.command.command.Command;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Letsplaybar
 *         Created on 26.07.2017.
 */
public class Umfrage implements Command {


    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) throws ParseException, IOException {
        if(args[0].equalsIgnoreCase("start")) {

            String[] msgarray = Arrays.asList(args).stream().skip(1).map(s -> s+ " ").collect(Collectors.joining()).split(Pattern.quote("|"));
            String ausgabe =  msgarray[0];
            for (int i = 1; i < msgarray.length; i++) {
                String[] teil = msgarray[i].split(Pattern.quote(";"));
                ausgabe += "\n"+(i-1)+". " + teil[0] + ":" + teil[1];
            }
            event.getChannel().sendMessage(ausgabe).queue();
        }
    }

    @Override
    public void executed(boolean success, GuildMessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public String getPerm() {
        return "cmd.umfrage";
    }


}