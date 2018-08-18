package de.letsplaybar.discordbot.command.utils;

import de.letsplaybar.discordbot.command.CommandModule;
import de.letsplaybar.discordbot.command.command.CommandHandler;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.text.ParseException;

/**
 * @author Letsplaybar
 *         Created on 06.06.2017.
 */
public class CommandListener extends ListenerAdapter {

    /**
     * wird aufgerufen wenn eine nachricht mit dem CMDZeichen beginnt um zu schauen ob es sich um einen Command handelt
     * ist dies der Fall so wird dieser ausgeführt
     * @param event
     */
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(event.getMessage().getContentDisplay().startsWith(CommandModule.getInstance().getCommand())){
            System.out.println(event.getAuthor().getName()+": "+event.getMessage().getContentDisplay());
            try {
                CommandHandler.handleCommand(CommandHandler.parse.parse(event.getMessage().getContentDisplay(),event));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
