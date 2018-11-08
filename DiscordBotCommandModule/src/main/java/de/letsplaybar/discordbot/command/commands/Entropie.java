package de.letsplaybar.discordbot.command.commands;

import de.letsplaybar.discordbot.command.CommandModule;
import de.letsplaybar.discordbot.command.command.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Entropie implements Command {
    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) {
        return false;
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) throws ParseException, IOException {
        if(args.length > 1){

            HashMap<Integer,Integer> values = new HashMap();
            Arrays.asList(args).stream().forEach(set -> {
                try {
                    int i = Integer.parseInt(set);
                    if(!values.containsKey(i)){
                        values.put(i,0);
                    }
                    values.put(i,values.get(i)+1);
                }catch (NumberFormatException ex){
                    if(eventGuild != null)
                        eventGuild.getChannel().sendMessage(new EmbedBuilder().setColor(Color.red).setTitle("Help")
                            .setDescription(help()).build()).queue();
                    if(eventPrivat != null)
                        eventPrivat.getChannel().sendMessage(new EmbedBuilder().setColor(Color.red).setTitle("Help")
                            .setDescription(help()).build()).queue();
                }
            });
            List<Double> probs = new ArrayList<>();
            values.values().stream().forEach(amount -> {
                probs.add((double)amount/(double)args.length);
            });
            final double[] size = {0};
            probs.stream().forEach(p ->{
                size[0] = size[0] + -p * (Math.log(p)/Math.log(2));
            } );
            if(eventGuild != null)
                eventGuild.getChannel().sendMessage(new EmbedBuilder().setColor(Color.YELLOW).setTitle("Entropie")
                    .setDescription("The Entropie from ["+ Arrays.asList(args).stream().map(s -> s+ ", ")
                            .collect(Collectors.joining())+ "] is "+ size[0]+".").build()).queue();
            if(eventPrivat != null)
                eventPrivat.getChannel().sendMessage(new EmbedBuilder().setColor(Color.YELLOW).setTitle("Entropie")
                    .setDescription("The Entropie from ["+ Arrays.asList(args).stream().map(s -> s+ ", ")
                            .collect(Collectors.joining())+ "] is "+ size[0]+".").build()).queue();
        }
    }

    @Override
    public void executed(boolean success, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) {

    }

    @Override
    public String help() {
        return "make "+ CommandModule.getInstance().getCommand() +"help for help";
    }

    @Override
    public String getPerm() {
        return "cmd.entropie";
    }
}
