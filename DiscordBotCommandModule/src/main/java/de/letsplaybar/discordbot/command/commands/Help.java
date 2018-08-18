package de.letsplaybar.discordbot.command.commands;

import de.letsplaybar.discordbot.command.CommandModule;
import de.letsplaybar.discordbot.command.command.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author Letsplaybar
 *         Created on 20.08.2017.
 */
public class Help implements Command {
    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) throws ParseException, IOException {
        String zeichen = CommandModule.getInstance().getCommand();
        event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.green).setDescription(
                "Commands:\n"+
                zeichen+"music help\n"+
                zeichen+"weather <city>\n"+
                zeichen+"umfrage start frage|emote;Auswahlmöglichkeit1|emote;Auswahlmöglichkeit2|...\n"+
                zeichen+"gifg <name> - sucht Gifs auf Giphy\n"+
                zeichen+"gift <name> - sucht Gifs auf Tenor\n"+
                zeichen+"image <name>\n"
        ).build()).queue();

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
        return "cmd.help";
    }
}
