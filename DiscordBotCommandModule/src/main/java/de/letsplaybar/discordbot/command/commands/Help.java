package de.letsplaybar.discordbot.command.commands;

import de.letsplaybar.discordbot.command.CommandModule;
import de.letsplaybar.discordbot.command.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author Letsplaybar
 *         Created on 20.08.2017.
 */
public class Help implements Command {
    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) {
        return false;
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) throws ParseException, IOException {
        String zeichen = CommandModule.getInstance().getCommand();
        MessageEmbed embed = new EmbedBuilder().setColor(Color.green).setDescription(
                "Commands:\n"+
                        zeichen+"music help\n"+
                        zeichen+"weather <city>\n"+
                        zeichen+"umfrage start frage | emote ; Auswahlmöglichkeit1 | emote ; Auswahlmöglichkeit2 | ...\n"+
                        zeichen+"gifg <name> - sucht Gifs auf Giphy\n"+
                        zeichen+"gift <name> - sucht Gifs auf Tenor\n"+
                        zeichen+"image <name>\n"+
                        zeichen+"pun\n"+
                        zeichen+"brainfuck interpret <brainfuck> - BrainFuck to Text\n"+
                        zeichen+"brainfuck tobrainfuck <txt> - Text to BrainFuck\n"+
                        zeichen+"answer <Ja Nein Frage>\n"+
                        zeichen+"entropie <List of Integer>\n"+
                        zeichen+"user - become infos for you\n"+
                        zeichen+"user <user> - become info from user"
        ).build();
        if(eventGuild != null)
            eventGuild.getChannel().sendMessage(embed).queue();
        if(eventPrivat != null)
            eventPrivat.getChannel().sendMessage(embed).queue();

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
        return "cmd.help";
    }
}
