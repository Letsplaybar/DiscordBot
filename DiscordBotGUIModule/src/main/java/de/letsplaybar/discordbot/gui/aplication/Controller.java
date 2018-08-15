package de.letsplaybar.discordbot.gui.aplication;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import de.letsplaybar.discordbot.command.utils.CommandListener;
import de.letsplaybar.discordbot.gui.aplication.help.about.About;
import de.letsplaybar.discordbot.gui.aplication.help.log.Log;
import de.letsplaybar.discordbot.gui.aplication.listener.PlaylistChangeListener;
import de.letsplaybar.discordbot.gui.aplication.playlist.create.Playlist;
import de.letsplaybar.discordbot.gui.aplication.playlist.delete.Delete;
import de.letsplaybar.discordbot.gui.aplication.playlist.edit.Playlistedit;
import de.letsplaybar.discordbot.gui.aplication.settings.perms.Permission;
import de.letsplaybar.discordbot.gui.aplication.settings.presence.Presence;
import de.letsplaybar.discordbot.gui.aplication.settings.status.Status;
import de.letsplaybar.discordbot.gui.aplication.settings.token.Token;
import de.letsplaybar.discordbot.gui.aplication.settings.weiteres.Weiteres;
import de.letsplaybar.discordbot.gui.listener.VoiceChannelSwitchListener;
import de.letsplaybar.discordbot.main.Bot;
import de.letsplaybar.discordbot.main.module.ModuleLoader;
import de.letsplaybar.discordbot.music.MusicModule;
import de.letsplaybar.discordbot.gui.music.GuiTrackManager;
import de.letsplaybar.discordbot.sql.SQLModule;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="timeEnd"
    private @Getter Text timeEnd; // Value injected by FXMLLoader

    @FXML // fx:id="current_playlist"
    private @Getter ChoiceBox<String> current_playlist; // Value injected by FXMLLoader

    @FXML // fx:id="playlists"
    private Menu playlists; // Value injected by FXMLLoader

    @FXML // fx:id="channel"
    private @Getter ChoiceBox<String> channel; // Value injected by FXMLLoader

    @FXML // fx:id="start"
    private  Button start; // Value injected by FXMLLoader

    @FXML // fx:id="skip"
    private  Button skip; // Value injected by FXMLLoader

    @FXML // fx:id="mute"
    private  Button mute; // Value injected by FXMLLoader

    @FXML // fx:id="title"
    private @Getter Text title; // Value injected by FXMLLoader

    @FXML // fx:id="pause"
    private  Button pause; // Value injected by FXMLLoader

    @FXML // fx:id="duration"
    private @Getter ProgressBar duration; // Value injected by FXMLLoader

    @FXML // fx:id="volume"
    private ScrollBar volume; // Value injected by FXMLLoader

    @FXML // fx:id="editPlaylist"
    private Menu editPlaylist; // Value injected by FXMLLoader

    @FXML // fx:id="current"
    private @Getter Text current; // Value injected by FXMLLoader

    @FXML // fx:id="stop"
    private Button stop; // Value injected by FXMLLoader

    @FXML // fx:id="deletePlaylist"
    private Menu deletePlaylist; // Value injected by FXMLLoader

    @FXML // fx:id="volume_size"
    private @Getter TextField volume_size; // Value injected by FXMLLoader

    @FXML // fx:id="shuffle"
    private @Getter ToggleButton shuffle; // Value injected by FXMLLoader

    private @Getter HashMap<String, Long> id = new HashMap<>();

    private @Getter @Setter Permission permission;

    private @Getter String curr_play;

    private PlaylistChangeListener listener;

    @FXML
    void startBot(ActionEvent event) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLModule sql = SQLModule.getInstance();
                try {
                    Bot.getInstance().startBot(sql.getToken(),sql.getSpielt(),"https://twitch.tv/letsplaybar", Game.GameType.valueOf(sql.getStreamt()),sql.getOnline(),sql.getState(),sql.getDetails(),sql.getAID(),sql.getLI(),sql.getSI(),sql.getLT(),sql.getST(),System.currentTimeMillis(),System.currentTimeMillis(),(ModuleLoader.getInstance().getActivateModules().contains("CommandModule"))?new CommandListener():null,new VoiceChannelSwitchListener());
                } catch (LoginException e) {
                    e.printStackTrace();
                }
                ObservableList<String> list = FXCollections.observableArrayList();
                Bot.getInstance().getBot().getGuilds().stream().forEach(g ->{
                    list.add("==========["+g.getName()+"]==========");
                    g.getVoiceChannels().stream().forEach(v->{
                        list.add(v.getName());
                        id.put(v.getName(),v.getIdLong());
                    });
                });
                channel.setItems(list);
            }
        }).start();
    }

    @FXML
    void stopBot(ActionEvent event) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bot.getInstance().stopBot();
                channel.setValue(null);
                channel.getItems().clear();
            }
        }).start();
    }

    @FXML
    void restartBot(ActionEvent event) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bot.getInstance().stopBot();
                channel.setValue(null);
                SQLModule sql = SQLModule.getInstance();
                try {
                    Bot.getInstance().startBot(sql.getToken(),sql.getSpielt(),"https://twitch.tv/letsplaybar", Game.GameType.valueOf(sql.getStreamt()),sql.getOnline(),sql.getState(),sql.getDetails(),sql.getAID(),sql.getLI(),sql.getSI(),sql.getLT(),sql.getST(),System.currentTimeMillis(),System.currentTimeMillis(),(ModuleLoader.getInstance().getActivateModules().contains("CommandModule"))?new CommandListener():null);
                } catch (LoginException e) {
                    e.printStackTrace();
                }
                ObservableList<String> list = FXCollections.observableArrayList();
                Bot.getInstance().getBot().getGuilds().stream().forEach(g ->{
                    list.add("==========["+g.getName()+"]==========");
                    g.getVoiceChannels().stream().forEach(v->{
                        list.add(v.getName());
                        id.put(v.getName(),v.getIdLong());
                    });
                });
                channel.setItems(list);
            }
        }).start();
    }

    @FXML
    void createPlaylist(ActionEvent event) {
        new Playlist();
    }

    @FXML
    void openToken(ActionEvent event) {
        new Token();
        ContextMenu m;

    }

    @FXML
    void openStatus(ActionEvent event) {
        new Status();
    }

    @FXML
    void openRichPresence(ActionEvent event) {
        new Presence();
    }

    @FXML
    void openSettings(ActionEvent event) {
        new Weiteres();
    }

    @FXML
    void openFAQ(ActionEvent event) {

    }

    @FXML
    void openLog(ActionEvent event) {
        new Log();
    }

    @FXML
    void openAbout(ActionEvent event) {
        new About();
    }

    @FXML
    void skip(ActionEvent event) {
        MusicModule music = MusicModule.getInstance();
        music.getPlayer().skip(Bot.getInstance().getBot().getVoiceChannelById(id.get(channel.getValue())).getGuild());
    }

    @FXML
    void start(ActionEvent event) {
        MusicModule music = MusicModule.getInstance();
        if(music.getPlayer().getTrackManager(Bot.getInstance().getBot().getVoiceChannelById(id.get(channel.getValue())).getGuild()) != null)
            ((GuiTrackManager)music.getPlayer().getTrackManager(Bot.getInstance().getBot().getVoiceChannelById(id.get(channel.getValue())).getGuild())).setPos(current_playlist.getSelectionModel().getSelectedIndex());
        try {
            if(current_playlist.getItems().size()>0)
            music.getPlayer().play(Bot.getInstance().getBot().getVoiceChannelById(id.get(channel.getValue())).getGuild(),SQLModule.getInstance().getSongURL(curr_play,current_playlist.getValue()== null? current_playlist.getItems().get(0):current_playlist.getValue()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void pause(ActionEvent event) {
        MusicModule music = MusicModule.getInstance();
        music.getPlayer().toglePause(Bot.getInstance().getBot().getVoiceChannelById(id.get(channel.getValue())).getGuild());
    }

    @FXML
    void stop(ActionEvent event) {
        MusicModule music = MusicModule.getInstance();
        ((GuiTrackManager)music.getPlayer().getTrackManager(Bot.getInstance().getBot().getVoiceChannelById(id.get(channel.getValue())).getGuild())).setPos(-1);
        music.getPlayer().stop(Bot.getInstance().getBot().getVoiceChannelById(id.get(channel.getValue())).getGuild());
    }

    @FXML
    void leave(ActionEvent event) {
        if(Bot.getInstance().getBot() != null)
            Bot.getInstance().getBot().getAudioManagers().stream().forEach(audioManager -> {
                if(audioManager.isConnected())
                    audioManager.closeAudioConnection();
            });
        channel.setValue(null);
    }

    @FXML
    void mute(ActionEvent event) {
        MusicModule music = MusicModule.getInstance();
        music.getPlayer().togleMute(Bot.getInstance().getBot().getVoiceChannelById(id.get(channel.getValue())).getGuild());
    }

    @FXML
    void perms(ActionEvent event){
        permission = new Permission();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert timeEnd != null : "fx:id=\"timeEnd\" was not injected: check your FXML file 'GUI.fxml'.";
        assert current_playlist != null : "fx:id=\"current_playlist\" was not injected: check your FXML file 'GUI.fxml'.";
        assert playlists != null : "fx:id=\"playlists\" was not injected: check your FXML file 'GUI.fxml'.";
        assert channel != null : "fx:id=\"channel\" was not injected: check your FXML file 'GUI.fxml'.";
        assert start != null : "fx:id=\"start\" was not injected: check your FXML file 'GUI.fxml'.";
        assert skip != null : "fx:id=\"skip\" was not injected: check your FXML file 'GUI.fxml'.";
        assert mute != null : "fx:id=\"mute\" was not injected: check your FXML file 'GUI.fxml'.";
        assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'GUI.fxml'.";
        assert pause != null : "fx:id=\"pause\" was not injected: check your FXML file 'GUI.fxml'.";
        assert duration != null : "fx:id=\"duration\" was not injected: check your FXML file 'GUI.fxml'.";
        assert volume != null : "fx:id=\"volume\" was not injected: check your FXML file 'GUI.fxml'.";
        assert editPlaylist != null : "fx:id=\"editPlaylist\" was not injected: check your FXML file 'GUI.fxml'.";
        assert current != null : "fx:id=\"current\" was not injected: check your FXML file 'GUI.fxml'.";
        assert stop != null : "fx:id=\"stop\" was not injected: check your FXML file 'GUI.fxml'.";
        assert deletePlaylist != null : "fx:id=\"deletePlaylist\" was not injected: check your FXML file 'GUI.fxml'.";
        assert volume_size != null : "fx:id=\"volume_size\" was not injected: check your FXML file 'GUI.fxml'.";
        assert shuffle != null : "fx:id=\"shuffle\" was not injected: check your FXML file 'GUI.fxml'.";
        listener = new PlaylistChangeListener();
        try {
            SQLModule.getInstance().getPlayList().stream().forEach(play -> {
                addPlaylist(play);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }

        current_playlist.getSelectionModel().selectedIndexProperty().addListener(listener);
        channel.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue != null && channel.getItems().size() >= newValue.intValue()) {
                    if(Bot.getInstance().getBot() ==null)
                        return;
                    if(newValue.intValue() == -1)
                        return;
                    if(channel.getItems().get(newValue.intValue()).startsWith("==========[")&&channel.getItems().get(newValue.intValue()).endsWith("]=========="))
                        return;
                    long ids = id.get(channel.getItems().get(newValue.intValue()));
                    Bot.getInstance()
                            .getBot()
                            .getVoiceChannelById(ids)
                            .getGuild()
                            .getAudioManager()
                            .openAudioConnection(Bot
                                    .getInstance()
                                    .getBot()
                                    .getVoiceChannelById(ids));
                }
            }
        });
        volume_size.setText(String.valueOf(100));

        volume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                volume_size.setText(String.valueOf(newValue));
                MusicModule music = MusicModule.getInstance();
                music.getPlayer().setVolume(Bot.getInstance().getBot().getVoiceChannelById(id.get(channel.getValue())).getGuild(),newValue.intValue());
            }
        });

    }

    public void addPlaylist(String name){
        MenuItem list = new MenuItem(name);
        MenuItem edit = new MenuItem(name);
        MenuItem delete = new MenuItem(name);
        list.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                current_playlist.getSelectionModel().selectedIndexProperty().removeListener(listener);
                current_playlist.getSelectionModel().clearSelection();
                current_playlist.getItems().clear();
                try {
                    SQLModule.getInstance().getPlaylistTitel(name).stream().forEach( song ->
                            current_playlist.getItems().add(song));
                    curr_play = name;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                current_playlist.getSelectionModel().selectedIndexProperty().addListener(listener);
            }
        });
        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    new Playlistedit(name);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new Delete(name);
            }
        });
        playlists.getItems().add(list);
        editPlaylist.getItems().add(edit);
        deletePlaylist.getItems().add(delete);
    }
    public void removePlaylist(String name){
        MenuItem item = null;
        for(MenuItem i : playlists.getItems())
            if( i.getText().equals(name)){
                item = i;
                break;
            }
         if(item != null){
             playlists.getItems().remove(item);
         }
         item = null;
        for(MenuItem i : editPlaylist.getItems())
            if( i.getText().equals(name)){
                item = i;
                break;
            }
        if(item != null){
            editPlaylist.getItems().remove(item);
        }
        item = null;
        for(MenuItem i : deletePlaylist.getItems())
            if( i.getText().equals(name)){
                item = i;
                break;
            }
        if(item != null){
            deletePlaylist.getItems().remove(item);
        }
    }



}