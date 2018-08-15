package de.letsplaybar.discordbot.command.commands;

import de.letsplaybar.discordbot.command.command.Command;
import de.letsplaybar.discordbot.command.utils.Tenor;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Letsplaybar
 *         Created on 21.08.2017.
 */
public class TENOR implements Command {
    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) throws ParseException, IOException {
        String name = Arrays.stream(args).map(s-> s+"%20").collect(Collectors.joining());
        name = name.substring(0,name.length()-3);
        Tenor tenor = new Tenor(name,50);
        try {
            tenor.load();
        }catch (Exception ex){
            event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.red).setDescription("Leider Konnten keine Gifs gefunden werden").build()).queue();
            return;
        }
        String g = tenor.getRndGif(0);
        if(g != null) {
            event.getChannel().sendMessage(
                    new EmbedBuilder().setImage(g).setColor(Color.cyan).build()).queue();
        }else{
            event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.red).setDescription("Leider Konnten keine Gifs gefunden werden").build()).queue();
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
        return "cmd.tenor";
    }
}
