package de.letsplaybar.discordbot.main;

import de.letsplaybar.discordbot.main.module.Module;
import de.letsplaybar.discordbot.main.module.ModuleLoader;
import de.letsplaybar.discordbot.main.utils.Spielst;
import lombok.Getter;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

public class Bot implements Module{

    private @Getter static Bot instance;
    private @Getter
    JDA bot;

    @Override
    public List<String> getRequementsModule() {
        ArrayList<String> mod = new ArrayList<>();
        mod.add("SQLModule");
        return mod;
    }

    @Override
    public void register() {
        instance = this;
    }

    @Override
    public void load()  {

    }

    @Override
    public void unload() {
        stopBot();
    }


    /**
     * Startet den Discordbot
     * @param token ist der Key damit der Bot sich einloggen kann
     * @param spiel Nachricht die der Bot anzeigt in der Leiste
     * @param url Link auf den er verweißt bei stramt
     * @param type {@link net.dv8tion.jda.api.entities.Activity.ActivityType} was er tut zuhören, sehen, spielen, streamen
     * @param status {@link OnlineStatus} der Onlinemode vom bot
     * @param state kleine beschreibung vom Rich
     * @param details kleine beschreibung vom Rich
     * @param application_id die id wird benötigt um die bilder zu laden
     * @param large_image id vom großen bild der aplication
     * @param small_image id vom klienen Bild der aplication
     * @param large_text Text vom Großen Biöd
     * @param small_text Text vom kleinen Bild
     * @param timestamp seit wann
     * @param since doppelt seit wann
     * @param listeners Listeners die benutzt werden sollem
     * @throws LoginException wenn der Token falsch ist
     */
    public void startBot(String token, String spiel, String url, Activity.ActivityType type, OnlineStatus status,
                         String state, String details, String application_id,
                         String large_image, String small_image, String large_text, String small_text, long timestamp,
                         long since, ListenerAdapter... listeners) throws LoginException {
        if(bot == null){
            JDABuilder builder = new JDABuilder(AccountType.CLIENT)
                    .setToken(token)
                    .setAudioEnabled(true)
                    .setStatus(status)
                    .setActivity(Spielst.getSpielt(spiel,url,type))
                    .setBulkDeleteSplittingEnabled(false);
            for(ListenerAdapter listener: listeners)
                if(listener != null)
                    builder.addEventListeners(listener);
            bot = builder.build();
            bot.setAutoReconnect(true);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (bot == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Spielst.getSpielt(spiel, url, type).setPresence(status, state, details, application_id, large_image,
                    small_image, large_text,small_text,timestamp, since);
        }
    }

    /**
     * Stoppt den Bot
     */
    public  void stopBot(){
        if(bot != null)
            bot.shutdownNow();
        bot = null;
    }


}
