package de.letsplaybar.discordbot.music;

import de.letsplaybar.discordbot.main.module.Module;
import de.letsplaybar.discordbot.music.manager.Player;
import lombok.Getter;
import lombok.Setter;

public class MusicModule implements Module {

    private static @Getter MusicModule instance;

    private @Getter @Setter
    Player player;

    @Override
    public void register() {
        instance = this;
        player =new Player();
    }

    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }
}
