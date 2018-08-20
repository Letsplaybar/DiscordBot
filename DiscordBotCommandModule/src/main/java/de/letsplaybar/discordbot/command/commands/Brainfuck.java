package de.letsplaybar.discordbot.command.commands;

import de.letsplaybar.discordbot.command.CommandModule;
import de.letsplaybar.discordbot.command.command.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Brainfuck implements Command {
    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent event) {
        if(args.length < 2){
            return true;
        }

        return false;
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) throws ParseException, IOException {
        if(args[0].equalsIgnoreCase("interpret"))
            event.getChannel().sendMessage(new EmbedBuilder().setTitle("Brainfuck Interpretiert")
                    .setDescription(CommandModule.getInstance().getBrainFuck().convert(
                            Arrays.asList(args).stream().skip(1).collect(Collectors.joining())
                    )).setColor(Color.GREEN).build()).queue();
        else if(args[0].equalsIgnoreCase("tobrainfuck")){
            event.getChannel().sendMessage(new EmbedBuilder().setTitle("Brainfuck")
                    .setDescription(CommandModule.getInstance().getBrainFuck().convertBack(
                            Arrays.asList(args).stream().skip(1).map(s -> s +" ").collect(Collectors.joining())
                    )).setColor(Color.GREEN).build()).queue();
        }else
            event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.red).setDescription("make "+
                    CommandModule.getInstance().getCommand()+"help for help").build()).queue();
    }

    @Override
    public void executed(boolean success, GuildMessageReceivedEvent event) {
        if(success){
            event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.red).setDescription("make "+
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
