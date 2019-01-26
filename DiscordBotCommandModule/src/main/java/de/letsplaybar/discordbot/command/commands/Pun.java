package de.letsplaybar.discordbot.command.commands;

import de.letsplaybar.discordbot.command.CommandModule;
import de.letsplaybar.discordbot.command.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;

public class Pun implements Command {
    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) {
        return false;
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) throws ParseException, IOException {
        if(eventGuild != null)
            eventGuild.getChannel().sendMessage(new EmbedBuilder().setDescription(CommandModule.getInstance().getGenerator().generate()).setColor(Color.CYAN.brighter()).build()).queue();
        if(eventPrivat != null)
            eventPrivat.getChannel().sendMessage(new EmbedBuilder().setDescription(CommandModule.getInstance().getGenerator().generate()).setColor(Color.CYAN.brighter()).build()).queue();
    }

    @Override
    public void executed(boolean success, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) {}

    @Override
    public String help() {
        return null;
    }

    @Override
    public String getPerm() {
        return "cmd.pun";
    }
}
