package de.letsplaybar.discordbot.main;

import de.letsplaybar.discordbot.main.module.Module;
import de.letsplaybar.discordbot.main.utils.Spielst;
import lombok.Getter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Bot implements Module{

    private @Getter static Bot instance;
    private @Getter JDA bot;


    @Override
    public void register() {
        instance = this;
    }

    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }

    public void startBot(String token, String spiel, String url, Game.GameType type, OnlineStatus status,
                         String state, String details, String application_id,
                         String large_image, String small_image, String large_text, String small_text,
                         ListenerAdapter... listeners) throws LoginException {
        if(bot == null){
            JDABuilder builder = new JDABuilder(AccountType.CLIENT)
                    .setToken(token)
                    .setAudioEnabled(true)
                    .setBulkDeleteSplittingEnabled(false);
            for(ListenerAdapter listener: listeners)
                builder.addEventListener(listener);
            bot = builder.buildAsync();
            bot.setAutoReconnect(true);
            Spielst.getSpielt(spiel, url, type).setPresence(status, state, details, application_id, large_image,
                    small_image, large_text,small_text );
        }
    }

    public  void stopBot(){
        if(bot != null)
            bot.shutdownNow();
    }


}
