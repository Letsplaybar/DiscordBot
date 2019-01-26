package de.letsplaybar.discordbot.command.command;

import de.letsplaybar.discordbot.command.CommandModule;
import net.dv8tion.jda.api.events.Event;

import java.util.ArrayList;

/**
 * @author Letsplaybar
 *         Created on 09.03.2017.
 */
public class CommandParser {
    /**
     * parst die empfangende Message zu einem {@link CommandContainer}
     * @param rw string de rzu parsen ist
     * @param e das event von der Message
     * @return {@link CommandContainer}
     */
    public CommandContainer parse(String rw, Event e) {

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
        public final Event event;

        /**
         * erzeugt einen CommandContainer
         * @param rw String der MSG
         * @param beheaded String ohne CMDZeichen
         * @param splitBeheaded argument Container mit command
         * @param invoke der Command
         * @param args Argument container
         * @param e Event vom Command
         */
        public CommandContainer(String rw, String beheaded, String[] splitBeheaded, String invoke, String[] args,
                                Event e) {
            this.raw = rw;
            this.beheaded = beheaded;
            this.splitBeheaded = splitBeheaded;
            this.invoke = invoke;
            this.args = args;
            this.event = e;
        }
    }
}

