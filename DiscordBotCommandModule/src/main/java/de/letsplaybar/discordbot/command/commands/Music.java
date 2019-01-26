package de.letsplaybar.discordbot.command.commands;

import de.letsplaybar.discordbot.command.CommandModule;
import de.letsplaybar.discordbot.command.command.Command;
import de.letsplaybar.discordbot.main.module.ModuleLoader;
import de.letsplaybar.discordbot.music.MusicModule;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

/**
 * @author Letsplaybar
 *         Created on 07.06.2017.
 */
public class Music implements Command {

    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) {
        return false;
    }

    private void sendHelpMessage(GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat){
        if(eventGuild != null)
            eventGuild.getChannel().sendMessage(help()).queue();
        if(eventPrivat != null)
            eventPrivat.getChannel().sendMessage(help()).queue();
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) throws ParseException, IOException {
        if(eventPrivat != null)
            return;
        switch (args.length) {
            case 0: // Show help message
                sendHelpMessage(eventGuild,eventPrivat);
                break;

            case 1:
            case 2:
                switch (args[0].toLowerCase()) {

                    case "help":
                        MusicModule.getInstance().getPlayer().sendHelpMessage(eventGuild.getChannel(),help());
                        break;

                    case "now":
                    case "current":
                    case "nowplaying":
                    case "info": // Display song info
                        eventGuild.getChannel().sendMessage(MusicModule.getInstance().getPlayer().buildQueuemessage(MusicModule.getInstance().getPlayer().getInfo(eventGuild.getGuild()))).queue();
                        break;
                    case "skip":
                        MusicModule.getInstance().getPlayer().skip(eventGuild.getGuild());
                        break;

                    case "stop":
                        MusicModule.getInstance().getPlayer().stop(eventGuild.getGuild());
                        break;

                    case "shuffle":
                        MusicModule.getInstance().getPlayer().shuffle(eventGuild.getGuild());
                        break;

                    case "pause":
                    case "resume":
                        MusicModule.getInstance().getPlayer().toglePause(eventGuild.getGuild());
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
                            eventGuild.getChannel().sendMessage(":warning:  Please include a valid source.").queue();
                        } else {
                            if(ModuleLoader.getInstance().getRegisterModulesName().contains("GUIModule")){
                                eventGuild.getChannel().sendMessage(":warning:  GUI Aktiv, Please use GUI.").queue();
                                return;
                            }
                            MusicModule.getInstance().getPlayer().play(eventGuild.getMember(),input);
                        }
                        break;
                }
                break;
        }

    }

    @Override
    public void executed(boolean success, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) {

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
