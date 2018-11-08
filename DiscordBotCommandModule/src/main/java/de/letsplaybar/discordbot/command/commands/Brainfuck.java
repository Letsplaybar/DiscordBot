package de.letsplaybar.discordbot.command.commands;

import de.letsplaybar.discordbot.command.CommandModule;
import de.letsplaybar.discordbot.command.command.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Brainfuck implements Command {
    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) {
        if(args.length < 2){
            return true;
        }

        return false;
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) throws ParseException, IOException {
        if(eventGuild != null) {
            if (args[0].equalsIgnoreCase("interpret"))
                eventGuild.getChannel().sendMessage(new EmbedBuilder().setTitle("Brainfuck Interpretiert")
                        .setDescription(CommandModule.getInstance().getBrainFuck().convert(
                                Arrays.asList(args).stream().skip(1).collect(Collectors.joining())
                        )).setColor(Color.GREEN).build()).queue();
            else if (args[0].equalsIgnoreCase("tobrainfuck")) {
                eventGuild.getChannel().sendMessage(new EmbedBuilder().setTitle("Brainfuck")
                        .setDescription(CommandModule.getInstance().getBrainFuck().convertBack(
                                Arrays.asList(args).stream().skip(1).map(s -> s + " ").collect(Collectors.joining())
                        )).setColor(Color.GREEN).build()).queue();
            } else
                eventGuild.getChannel().sendMessage(new EmbedBuilder().setColor(Color.red).setDescription("make " +
                        CommandModule.getInstance().getCommand() + "help for help").build()).queue();
        }
        if(eventPrivat != null){
            if (args[0].equalsIgnoreCase("interpret"))
                eventPrivat.getChannel().sendMessage(new EmbedBuilder().setTitle("Brainfuck Interpretiert")
                        .setDescription(CommandModule.getInstance().getBrainFuck().convert(
                                Arrays.asList(args).stream().skip(1).collect(Collectors.joining())
                        )).setColor(Color.GREEN).build()).queue();
            else if (args[0].equalsIgnoreCase("tobrainfuck")) {
                eventPrivat.getChannel().sendMessage(new EmbedBuilder().setTitle("Brainfuck")
                        .setDescription(CommandModule.getInstance().getBrainFuck().convertBack(
                                Arrays.asList(args).stream().skip(1).map(s -> s + " ").collect(Collectors.joining())
                        )).setColor(Color.GREEN).build()).queue();
            } else
                eventPrivat.getChannel().sendMessage(new EmbedBuilder().setColor(Color.red).setDescription("make " +
                        CommandModule.getInstance().getCommand() + "help for help").build()).queue();
        }
    }

    @Override
    public void executed(boolean success, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) {
        if(success){
            if(eventGuild != null)
                eventGuild.getChannel().sendMessage(new EmbedBuilder().setColor(Color.red).setDescription("make "+
                    CommandModule.getInstance().getCommand()+"help for help").build()).queue();
            if(eventPrivat != null)
                eventPrivat.getChannel().sendMessage(new EmbedBuilder().setColor(Color.red).setDescription("make "+
                        CommandModule.getInstance().getCommand()+"help for help").build()).queue();
        }
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public String getPerm() {
        return "cmd.brainfuck";
    }
}
