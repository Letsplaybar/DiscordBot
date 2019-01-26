package de.letsplaybar.discordbot.gui.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import de.letsplaybar.discordbot.music.manager.AudioPlayerSendHandler;
import de.letsplaybar.discordbot.music.manager.Player;
import de.letsplaybar.discordbot.music.manager.TrackManager;
import net.dv8tion.jda.api.entities.Guild;

import java.util.AbstractMap;

public class GuiPlayer extends Player {

    @Override
    public AudioPlayer createPlayer(Guild guild) {
        AudioPlayer nPlayer = myManager.createPlayer();
        TrackManager manager = new GuiTrackManager(nPlayer,this);
        nPlayer.addListener(manager);
        guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(nPlayer));
        players.put(guild.getId(), new AbstractMap.SimpleEntry<>(nPlayer, manager));
        return nPlayer;
    }

    @Override
    public void stop(Guild guild){
        if(getTrackManager(guild) != null)
            ((GuiTrackManager)getTrackManager(guild)).setPos(-1);
        skip(guild);
    }
}
