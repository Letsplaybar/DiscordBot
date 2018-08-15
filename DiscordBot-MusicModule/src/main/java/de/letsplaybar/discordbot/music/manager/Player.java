package de.letsplaybar.discordbot.music.manager;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import lombok.Getter;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

import java.awt.*;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class Player {

    protected  @Getter
    final AudioPlayerManager myManager = new DefaultAudioPlayerManager();
    protected  @Getter final Map<String, Map.Entry<AudioPlayer, TrackManager>> players = new HashMap<>();

    public Player(){
        AudioSourceManagers.registerRemoteSources(myManager);
        AudioSourceManagers.registerLocalSource(myManager);
    }


    public boolean hasPlayer(Guild guild) {
        return players.containsKey(guild.getId());
    }

    public AudioPlayer getPlayer(Guild guild) {
        AudioPlayer p;
        if (hasPlayer(guild)) {
            p = players.get(guild.getId()).getKey();
        } else {
            p = createPlayer(guild);
        }
        return p;
    }

    public TrackManager getTrackManager(Guild guild) {
        if(players.get(guild.getId()) == null)
            return null;
        return players.get(guild.getId()).getValue();
    }

    public AudioPlayer createPlayer(Guild guild) {
        AudioPlayer nPlayer = myManager.createPlayer();
        TrackManager manager = new TrackManager(nPlayer);
        nPlayer.addListener(manager);
        guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(nPlayer));
        players.put(guild.getId(), new AbstractMap.SimpleEntry<>(nPlayer, manager));
        return nPlayer;
    }

    public void reset(Guild guild) {
        players.remove(guild.getId());
        getPlayer(guild).destroy();
        getTrackManager(guild).purgeQueue();
        guild.getAudioManager().closeAudioConnection();
    }

    public void loadTrack(String identifier, Member author) {

        Guild guild = author.getGuild();
        getPlayer(guild); // Make sure this guild has a player.

        myManager.loadItemOrdered(guild, identifier, new AudioLoadResultHandler() {

            public void trackLoaded(AudioTrack track) {

                getTrackManager(guild).queue(track, author);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                if (playlist.getSelectedTrack() != null) {
                    trackLoaded(playlist.getSelectedTrack());
                } else if (playlist.isSearchResult()) {
                    trackLoaded(playlist.getTracks().get(0));
                } else {

                    for (int i = 0; i < playlist.getTracks().size(); i++) {
                        getTrackManager(guild).queue(playlist.getTracks().get(i), author);
                    }
                }
            }
            @Override
            public void noMatches() {
                System.err.println("No Match");
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                System.err.println(exception.getCause());
            }
        });
    }

    public void loadTrack(String identifier, Guild guild) {

        getPlayer(guild); // Make sure this guild has a player.

        myManager.loadItemOrdered(guild, identifier, new AudioLoadResultHandler() {

            public void trackLoaded(AudioTrack track) {

                getTrackManager(guild).queue(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                if (playlist.getSelectedTrack() != null) {
                    trackLoaded(playlist.getSelectedTrack());
                } else if (playlist.isSearchResult()) {
                    trackLoaded(playlist.getTracks().get(0));
                } else {

                    for (int i = 0; i < playlist.getTracks().size(); i++) {
                        getTrackManager(guild).queue(playlist.getTracks().get(i));
                    }
                }
            }
            @Override
            public void noMatches() {
                System.out.println("No Match");
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                System.err.println(exception.getCause());
            }
        });
    }

    public String buildQueueMessage(AudioInfo info) {
        AudioTrackInfo trackInfo = info.getTrack().getInfo();
        String title = trackInfo.title;
        long length = trackInfo.length;
        return "`[ " + getTimestamp(length) + " ]` " + title + "\n";
    }

    public String getTimestamp(long milis) {
        long seconds = milis / 1000;
        long hours = Math.floorDiv(seconds, 3600);
        seconds = seconds - (hours * 3600);
        long mins = Math.floorDiv(seconds, 60);
        seconds = seconds - (mins * 60);
        return (hours == 0 ? "" : hours + ":") + String.format("%02d", mins) + ":" + String.format("%02d", seconds);
    }

    private boolean isIdle(Guild g){
        return  !hasPlayer(g) || getPlayer(g).getPlayingTrack() == null;
    }

    public void skip(Guild g){
        if(isIdle(g))
            return;
        getPlayer(g).stopTrack();
    }

    public void shuffle(Guild guild){
        getTrackManager(guild).shuffleQueue();
    }

    public AudioInfo getInfo(Guild guild){
        return getTrackManager(guild).getTrackInfo(getPlayer(guild).getPlayingTrack());
    }

    public String title(Guild guild){
        return getPlayer(guild).getPlayingTrack().getInfo().title;
    }

    public long duration(Guild guild){
        return getPlayer(guild).getPlayingTrack().getInfo().length;
    }

    private void sendErrorMessage(TextChannel channel, String content){
        channel.sendMessage(new EmbedBuilder().setColor(Color.red).setDescription(content).build()).queue();
    }

    public void sendHelpMessage(TextChannel channel, String content){
        channel.sendMessage(new EmbedBuilder().setColor(Color.cyan).setDescription(content).build()).queue();
    }

    public void play(Member member, String titel){
        loadTrack(titel,member);
    }

    public void play(Guild guild, String titel){
        loadTrack(titel,guild);
    }

    public void stop(Guild guild){
        if(isIdle(guild))
            return;
        getTrackManager(guild).purgeQueue();
        skip(guild);
    }

    public void setVolume(Guild guild, int volume){
        getPlayer(guild).setVolume(volume);
        lastVolume = 0;
    }

    private int lastVolume = 0;

    public void togleMute(Guild guild){
        int temp = lastVolume;
        lastVolume = getPlayer(guild).getVolume();
        getPlayer(guild).setVolume(temp);
    }

    public void toglePause(Guild guild){
        getPlayer(guild).setPaused(!getPlayer(guild).isPaused());
    }

    public String buildQueuemessage(AudioInfo info){
        AudioTrackInfo trackInfo = info.getTrack().getInfo();
        String title = trackInfo.title;
        long length = trackInfo.length;
        return " [ "+ this.getTimestamp(length)+" ] "+ title+"\n";
    }
}
