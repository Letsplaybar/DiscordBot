package de.letsplaybar.discordbot.gui.aplication.listener;

import de.letsplaybar.discordbot.gui.GUIModule;
import de.letsplaybar.discordbot.gui.aplication.Controller;
import de.letsplaybar.discordbot.gui.music.GuiTrackManager;
import de.letsplaybar.discordbot.main.Bot;
import de.letsplaybar.discordbot.music.MusicModule;
import de.letsplaybar.discordbot.sql.SQLModule;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;

import java.sql.SQLException;

public class PlaylistChangeListener implements ChangeListener<Number> {
    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        MusicModule music = MusicModule.getInstance();
        Controller controller = GUIModule.getInstance().getGui().getController();
        Guild guild = Bot.getInstance().getBot().getVoiceChannelById(controller.getId().get(controller.getChannel().getValue())).getGuild();
        int pos = newValue.intValue();
        System.out.println(pos);
        if(Bot.getInstance().getBot() == null)
            return;
        if(guild == null || !guild.getAudioManager().isConnected())
            return;
        if(controller.getCurrent_playlist().getItems().size() == 0)
            return;
        music.getPlayer().stop(guild);
        try {
            music.getPlayer().play(guild,SQLModule.getInstance().getSongURL(controller.getCurr_play(),controller.getCurrent_playlist().getItems().get(pos)));
            ((GuiTrackManager)music.getPlayer().getTrackManager(guild)).setPos(pos);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
