package de.letsplaybar.discordbot.music.manager;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import de.letsplaybar.discordbot.main.Bot;
import lombok.Getter;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackManager extends AudioEventAdapter {

    private final AudioPlayer player;
    private final Queue<AudioInfo> queue;

    public TrackManager(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrack track, Member author) {
        AudioInfo info = new AudioInfo(track, author);
        queue.add(info);

        if (player.getPlayingTrack() == null) {
            player.playTrack(track);
        }
    }

    public void queue(AudioTrack track) {
        Bot.getInstance().getBot().getGuilds().stream().forEach(guild ->{
            if(guild.getAudioManager().isConnected()){
                Member member = guild.getMember(Bot.getInstance().getBot().getSelfUser());
                AudioInfo info = new AudioInfo(track, member);
                queue.add(info);

                if (player.getPlayingTrack() == null) {
                    player.playTrack(track);
                }
            }
        });
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        AudioInfo info = queue.element();
        VoiceChannel vChan = null;
        if(isNotConnect()){
            if(info.getAuthor().getVoiceState().inVoiceChannel()){
                vChan = info.getAuthor().getVoiceState().getChannel();
            }else return;
            if(vChan == null){
                player.stopTrack();
            }else{
                info.getAuthor().getGuild().getAudioManager().openAudioConnection(vChan);
            }
        }

    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (!queue.isEmpty()) {
            player.playTrack(queue.element().getTrack());
        }else {
            Bot.getInstance().getBot().getAudioManagers().stream().forEach(am ->{
                if(am.isConnected())
                    am.closeAudioConnection();
            });
        }
    }

    public void shuffleQueue() {
        List<AudioInfo> tQueue = new ArrayList<>(getQueuedTracks());
        AudioInfo current = tQueue.get(0);
        tQueue.remove(0);
        Collections.shuffle(tQueue);
        tQueue.add(0, current);
        purgeQueue();
        queue.addAll(tQueue);
    }

    public Set<AudioInfo> getQueuedTracks() {
        return new LinkedHashSet<>(queue);
    }

    public void purgeQueue() {
        queue.clear();
    }

    public void remove(AudioInfo entry) {
        queue.remove(entry);
    }

    public AudioInfo getTrackInfo(AudioTrack track) {
        return queue.stream().filter(audioInfo -> audioInfo.getTrack().equals(track)).findFirst().orElse(null);
    }

    private boolean isNotConnect(){
        for(AudioManager a : Bot.getInstance().getBot().getAudioManagers()){
            if(a.isConnected())
                return false;
        }
        return true;
    }
}
