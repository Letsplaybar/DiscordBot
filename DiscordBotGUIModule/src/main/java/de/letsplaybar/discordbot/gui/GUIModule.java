package de.letsplaybar.discordbot.gui;

import de.letsplaybar.discordbot.gui.aplication.GUI;
import de.letsplaybar.discordbot.gui.music.GuiPlayer;
import de.letsplaybar.discordbot.main.module.Module;
import de.letsplaybar.discordbot.music.MusicModule;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GUIModule implements Module {

    private static  @Getter GUIModule instance;
    private @Getter @Setter GUI gui;


    @Override
    public void register() {
        instance = this;
        try {
            gui = new GUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {
        MusicModule.getInstance().setPlayer(new GuiPlayer());
        new Thread(new Runnable() {
            @Override
            public void run() {
                gui.start();
            }
        }).start();
    }

    @Override
    public void unload() {

    }

}
