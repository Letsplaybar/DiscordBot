package de.letsplaybar.discordbot.gui.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import de.letsplaybar.discordbot.gui.GUIModule;
import de.letsplaybar.discordbot.main.Bot;
import de.letsplaybar.discordbot.music.manager.Player;
import de.letsplaybar.discordbot.music.manager.TrackManager;
import de.letsplaybar.discordbot.sql.SQLModule;
import javafx.application.Platform;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.sql.SQLException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GuiTrackManager  extends TrackManager {

    private @Getter Timer timer;
    private Player player;
    private @Setter int pos;

    public GuiTrackManager(AudioPlayer player, Player play) {
        super(player);
        timer = new Timer();
        this.player = play;
        pos = 0;
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        GUIModule gui =GUIModule.getInstance();
        gui.getGui().getController().getDuration().setProgress(0);
        gui.getGui().getController().getCurrent().setText("0");
        gui.getGui().getController().getTimeEnd().setText(getTimeStamp(track.getDuration()));
        gui.getGui().getController().getTitle().setText(track.getInfo().author + " - "+track.getInfo().title);
        if(pos == -1)
            pos = 0;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                 gui.getGui().getController().getCurrent_playlist().getSelectionModel().select(pos);
            }
        });
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                gui.getGui().getController().getDuration().setProgress((double)track.getPosition()/(double)track.getDuration());
                gui.getGui().getController().getCurrent().setText(getTimeStamp(track.getPosition()));
                gui.getGui().getController().getTitle().setText(track.getInfo().author +" - "+ track.getInfo().title);
                if(track.getDuration() == track.getPosition()){
                    timer.cancel();
                }
            }
        };
        timer= new Timer();
        timer.schedule(task,1,60);
    }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        if(timer != null)
            timer.cancel();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        GUIModule gui =GUIModule.getInstance();
        gui.getGui().getController().getDuration().setProgress(0);
        if(timer != null){
            timer.cancel();
        }
        gui.getGui().getController().getCurrent().setText("0");
        gui.getGui().getController().getTimeEnd().setText("--:--");
        if(pos == -1)
            return;
        int size = gui.getGui().getController().getCurrent_playlist().getItems().size();
        if(gui.getGui().getController().getShuffle().isSelected()){
            pos = new Random().nextInt(size);
        }else {
            pos++;
            if(pos >= size)
                pos = 0;
        }
        try {
            this.player.play(getChannel().getGuild(), SQLModule.getInstance().getSongURL(gui.getGui().getController().getCurr_play(),gui.getGui().getController().getCurrent_playlist().getItems().get(pos)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public VoiceChannel getChannel(){
        for(AudioManager manager :Bot.getInstance().getBot().getAudioManagers())
            if(manager.isConnected())
                return manager.getConnectedChannel();
        throw new NullPointerException("Is not Connected");
    }

    private String getTimeStamp(long millis){
        long seconds = millis/1000;
        long hours = Math.floorDiv(seconds,3600);
        seconds = seconds -(hours*3600);
        long mins = Math.floorDiv(seconds,60);
        seconds = seconds -(mins*60);return  (hours==0?"":hours+":")+String.format("%02d",mins)+":"+String.format("%02d",seconds);
    }
}
