package de.letsplaybar.discordbot.command.commands;

import de.letsplaybar.discordbot.command.command.Command;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
                ausgabe += "\n"+(i)+". " + event.getMessage().getEmotes().get(i-1).getAsMention() + " : " + teil[1];
            }
            Message msg =
                    event.getChannel().sendMessage(ausgabe).complete();
            event.getMessage().getEmotes().stream().forEach(em ->{
                msg.addReaction(em).queue();
            });
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
