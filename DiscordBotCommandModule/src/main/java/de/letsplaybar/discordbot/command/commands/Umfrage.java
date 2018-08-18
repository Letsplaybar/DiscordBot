package de.letsplaybar.discordbot.command.commands;

import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import de.letsplaybar.discordbot.command.CommandModule;
import de.letsplaybar.discordbot.command.command.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
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
        if(args.length <= 1){

            event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Error").setDescription("make "+ CommandModule.getInstance().getCommand()+"help for help").build()).complete();
            return;
        }
        if(args[0].equalsIgnoreCase("start")) {
            List<String> emoji = new ArrayList<>();

            String[] msgarray = Arrays.asList(args).stream().skip(1).map(s -> s+ " ").collect(Collectors.joining()).split(Pattern.quote("|"));
            String ausgabe =  msgarray[0];
            int emote = 0;
            if(msgarray.length == 1){
                event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Error").setDescription("Missing answer parameter\n make "+ CommandModule.getInstance().getCommand()+"help for help").build()).complete();
                return;
            }
            for (int i = 1; i < msgarray.length; i++) {
                String[] teil = msgarray[i].split(Pattern.quote(";"));

                if(emoji.contains(teil[0])){
                    event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Error").setDescription("You can only use every Emote one Time\n make "+ CommandModule.getInstance().getCommand()+"help for help").build()).complete();
                    return;
                }
                List<String> em = EmojiParser.extractEmojis(teil[0]);
                em.stream().forEach(e-> System.out.println(e));
                if(em.isEmpty()&& !(teil[0].replace(" ", "").startsWith(":")&& teil[0].replace(" ", "").endsWith(":"))){
                    event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Error").setDescription("You can only use Emote to Indentify the answer\n make "+ CommandModule.getInstance().getCommand()+"help for help").build()).complete();
                    return;
                }else {
                    teil[0] = em.get(0);
                }


                ausgabe += "\n"+(i)+". " + (teil[0].contains(":")? event.getMessage().getEmotes().get(emote++).getAsMention():teil[0].replace(" ", "")) + " : " + teil[1];
                emoji.add(teil[0]);
            }
            Message msg =
                    event.getChannel().sendMessage(ausgabe).complete();
            event.getMessage().getEmotes().stream().forEach(em ->{
                msg.addReaction(em).queue();
            });
            emoji.stream().forEach(em -> {
                    msg.addReaction(em.replace(" ", ""));
            });

        }else{
            event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Error").setDescription("make "+ CommandModule.getInstance().getCommand()+"help for help").build()).complete();
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
