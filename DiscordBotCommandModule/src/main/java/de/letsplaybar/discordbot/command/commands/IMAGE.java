package de.letsplaybar.discordbot.command.commands;

import de.letsplaybar.discordbot.command.command.Command;
import de.letsplaybar.discordbot.command.utils.Image;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Letsplaybar on 21.08.17.
 */
public class IMAGE implements Command {
    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) {
        return false;
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) throws ParseException, IOException {
        String name = Arrays.stream(args).map(s-> s+"+").collect(Collectors.joining());
        name = name.substring(0,name.length()-1);
        Image img = new Image(name,50);
        try{
            img.load();
        }catch (Exception e){
            if(eventGuild != null)
                eventGuild.getChannel().sendMessage(new EmbedBuilder().setColor(Color.red).setDescription("Leider Konnten keine IMAGE gefunden werden").build()).queue();
            if(eventPrivat != null)
                eventPrivat.getChannel().sendMessage(new EmbedBuilder().setColor(Color.red).setDescription("Leider Konnten keine IMAGE gefunden werden").build()).queue();
            e.printStackTrace();
            return;
        }
        String s = img.getRndImage();
        if(eventGuild != null) {
            if (s != null) {
                eventGuild.getChannel().sendMessage(
                        new EmbedBuilder().setImage(s).setColor(Color.cyan).build()).queue();
            } else {
                eventGuild.getChannel().sendMessage(new EmbedBuilder().setColor(Color.red).setDescription("Leider konnte keine IMAGE gefunden werden").build()).queue();
            }
        }
        if(eventPrivat != null) {
            if (s != null) {
                eventPrivat.getChannel().sendMessage(
                        new EmbedBuilder().setImage(s).setColor(Color.cyan).build()).queue();
            } else {
                eventPrivat.getChannel().sendMessage(new EmbedBuilder().setColor(Color.red).setDescription("Leider konnte keine IMAGE gefunden werden").build()).queue();
            }
        }
    }

    @Override
    public void executed(boolean success, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) {

    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public String getPerm() {
        return "cmd.image";
    }
}
