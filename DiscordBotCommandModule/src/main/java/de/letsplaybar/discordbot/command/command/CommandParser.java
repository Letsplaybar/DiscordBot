package de.letsplaybar.discordbot.command.command;

import de.letsplaybar.discordbot.command.CommandModule;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;

/**
 * @author Letsplaybar
 *         Created on 09.03.2017.
 */
public class CommandParser {
    public CommandContainer parse(String rw, GuildMessageReceivedEvent e) {

        ArrayList<String> split = new ArrayList<>();

        String raw = rw;
        String beheaded = raw.replaceFirst(CommandModule.getInstance().getCommand(), "");
        String[] splitBeheaded = beheaded.split(" ");

        for (String s : splitBeheaded) {
            split.add(s);
        }

        String invoke = split.get(0);
        String[] args = new String[split.size()-1];
        split.subList(1, split.size()).toArray(args);

        return new CommandContainer(raw, beheaded, splitBeheaded, invoke, args, e);
    }

    public class CommandContainer {

        public final String raw;
        public final String beheaded;
        public final String[] splitBeheaded;
        public final String invoke;
        public final String[] args;
        public final GuildMessageReceivedEvent event;

        public CommandContainer(String rw, String beheaded, String[] splitBeheaded, String invoke, String[] args, GuildMessageReceivedEvent e) {
            this.raw = rw;
            this.beheaded = beheaded;
            this.splitBeheaded = splitBeheaded;
            this.invoke = invoke;
            this.args = args;
            this.event = e;
        }
    }
}

