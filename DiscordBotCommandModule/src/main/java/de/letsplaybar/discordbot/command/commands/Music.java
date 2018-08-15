package de.letsplaybar.discordbot.command.commands;

import de.letsplaybar.discordbot.command.CommandModule;
import de.letsplaybar.discordbot.command.command.Command;
import de.letsplaybar.discordbot.main.module.ModuleLoader;
import de.letsplaybar.discordbot.music.MusicModule;
import lombok.Setter;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Letsplaybar
 *         Created on 07.06.2017.
 */
public class Music implements Command {

    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent event) {
        return false;
    }

    private void sendHelpMessage(GuildMessageReceivedEvent event){
        event.getChannel().sendMessage(help()).queue();
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) throws ParseException, IOException {
        switch (args.length) {
            case 0: // Show help message
                sendHelpMessage(event);
                break;

            case 1:
            case 2:
                switch (args[0].toLowerCase()) {

                    case "help":
                        MusicModule.getInstance().getPlayer().sendHelpMessage(event.getChannel(),help());
                        break;

                    case "now":
                    case "current":
                    case "nowplaying":
                    case "info": // Display song info
                        event.getChannel().sendMessage(MusicModule.getInstance().getPlayer().buildQueuemessage(MusicModule.getInstance().getPlayer().getInfo(event.getGuild()))).queue();
                        break;
                    case "skip":
                        MusicModule.getInstance().getPlayer().skip(event.getGuild());
                        break;

                    case "stop":
                        MusicModule.getInstance().getPlayer().stop(event.getGuild());
                        break;

                    case "shuffle":
                        MusicModule.getInstance().getPlayer().shuffle(event.getGuild());
                        break;

                    case "pause":
                    case "resume":
                        MusicModule.getInstance().getPlayer().toglePause(event.getGuild());
                        break;
                }

            default:
                String input = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                switch (args[0].toLowerCase()) {
                    case "ytplay": // Query YouTube for a music video
                        input = "ytsearch: " + input;
                        // no break;

                    case "play": // Music a track
                        if (args.length <= 1) {
                            event.getChannel().sendMessage(":warning:  Please include a valid source.").queue();
                        } else {
                            if(ModuleLoader.getInstance().getRegisterModulesName().contains("GUIModule")){
                                event.getChannel().sendMessage(":warning:  GUI Aktiv, Please use GUI.").queue();
                                return;
                            }
                            MusicModule.getInstance().getPlayer().play(event.getMember(),input);
                        }
                        break;
                }
                break;
        }

    }

    @Override
    public void executed(boolean success, GuildMessageReceivedEvent event) {

    }

    @Override
    public String help() {
        String co = CommandModule.getInstance().getCommand();
        return
                ":musical_note:  **MUSIC PLAYER**  :musical_note: \n\n" +
                        "` "+co+"music play <yt/soundcloud - URL> `  -  Start playing a track / Add a track to queue / Add a playlist to queue\n" +
                        "` "+co+"music ytplay <Search string for yt> `  -  Same like *play*, just let youtube search for a track you enter\n" +
                        "` "+co+"music skip `  -  Skip the current track in queue\n" +
                        "` "+co+"music now `  -  Show info about the now playing track\n" +
                        "` "+co+"music stop `  -  Stop the music player"
                ;

    }

    @Override
    public String getPerm() {
        return "cmd.music";
    }
}
