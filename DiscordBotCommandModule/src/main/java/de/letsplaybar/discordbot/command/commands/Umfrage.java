package de.letsplaybar.discordbot.command.commands;

import com.vdurmont.emoji.EmojiParser;
import de.letsplaybar.discordbot.command.CommandModule;
import de.letsplaybar.discordbot.command.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

import java.awt.*;
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
    public boolean called(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) {
        return false;
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) throws ParseException, IOException {
        if(args.length <= 1){
            if(eventGuild != null)
                eventGuild.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Error").setDescription("make "+ CommandModule.getInstance().getCommand()+"help for help").build()).complete();
            if(eventPrivat != null)
                eventPrivat.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Error").setDescription("make "+ CommandModule.getInstance().getCommand()+"help for help").build()).complete();
            return;
        }
        if(args[0].equalsIgnoreCase("start")) {
            List<String> emoji = new ArrayList<>();

            // Teilt den CMD in Pattern auf 1 Pater Frage weitere Pattern Antworten
            String[] msgarray = Arrays.asList(args).stream().skip(1).map(s -> s+ " ").collect(Collectors.joining()).split(Pattern.quote("|"));
            String ausgabe =  msgarray[0];
            int emote = 0;
            if(msgarray.length == 1){
                if(eventGuild != null)
                    eventGuild.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Error").setDescription("Missing answer parameter\n make "+ CommandModule.getInstance().getCommand()+"help for help").build()).complete();
                if(eventPrivat != null)
                    eventPrivat.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Error").setDescription("Missing answer parameter\n make "+ CommandModule.getInstance().getCommand()+"help for help").build()).complete();
                return;
            }
            for (int i = 1; i < msgarray.length; i++) {
                //Teilt Antwortenpattern in Emotepatter und Auswahlmöglichkeitpatter
                String[] teil = msgarray[i].split(Pattern.quote(";"));

                if(emoji.contains(teil[0])){
                    if(eventGuild != null)
                        eventGuild.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Error").setDescription("You can only use every Emote one Time\n make "+ CommandModule.getInstance().getCommand()+"help for help").build()).complete();
                    if(eventPrivat!= null)
                        eventPrivat.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Error").setDescription("You can only use every Emote one Time\n make "+ CommandModule.getInstance().getCommand()+"help for help").build()).complete();
                    return;
                }
                List<String> em = EmojiParser.extractEmojis(teil[0]);
                em.stream().forEach(e-> System.out.println(e));
                if(em.isEmpty()&& !(teil[0].replace(" ", "").startsWith(":")&& teil[0].replace(" ", "").endsWith(":"))){
                    if(eventGuild != null)
                        eventGuild.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Error").setDescription("You can only use Emote to Indentify the answer\n make "+ CommandModule.getInstance().getCommand()+"help for help").build()).complete();
                    if(eventPrivat != null)
                        eventPrivat.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Error").setDescription("You can only use Emote to Indentify the answer\n make "+ CommandModule.getInstance().getCommand()+"help for help").build()).complete();
                    return;
                }else {
                    if(em.size() != 0)
                        teil[0] = em.get(0);
                }

                // fügt das Emote und die auswahlpatter zur message hinzu
                if(eventGuild != null)
                    ausgabe += "\n"+(i)+". " + (teil[0].contains(":")? eventGuild.getMessage().getEmotes().get(emote++).getAsMention():teil[0].replace(" ", "")) + " : " + teil[1];
                if(eventPrivat != null)
                    ausgabe += "\n"+(i)+". " + (teil[0].contains(":")? eventPrivat.getMessage().getEmotes().get(emote++).getAsMention():teil[0].replace(" ", "")) + " : " + teil[1];
                emoji.add(teil[0]);
            }
            if(eventGuild != null) {
                Message msg =
                        eventGuild.getChannel().sendMessage(ausgabe).complete();
                eventGuild.getMessage().getEmotes().stream().forEach(em -> {
                    msg.addReaction(em).queue();
                });
                emoji.stream().forEach(em -> {
                    msg.addReaction(em.replace(" ", ""));
                });
            }
            if(eventPrivat != null){
                Message msg =
                        eventPrivat.getChannel().sendMessage(ausgabe).complete();
                eventPrivat.getMessage().getEmotes().stream().forEach(em -> {
                    msg.addReaction(em).queue();
                });
                emoji.stream().forEach(em -> {
                    msg.addReaction(em.replace(" ", ""));
                });
            }
        }else{
            if(eventGuild != null)
                eventGuild.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Error").setDescription("make "+ CommandModule.getInstance().getCommand()+"help for help").build()).complete();
            if(eventPrivat != null)
                eventPrivat.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Error").setDescription("make "+ CommandModule.getInstance().getCommand()+"help for help").build()).complete();
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
        return "cmd.umfrage";
    }


}
