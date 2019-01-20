package de.letsplaybar.discordbot.main.utils;

import de.letsplaybar.discordbot.main.Bot;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.RichPresence;
import net.dv8tion.jda.internal.JDAImpl;
import net.dv8tion.jda.internal.managers.PresenceImpl;
import org.json.JSONObject;

import javax.annotation.Nullable;


/**
 * @author Letsplaybar
 * Created on 05.09.2017.
 */
public class Spielst implements Activity {

    private String name;
    private String url;
    private ActivityType type;

    /**
     * holt dir das Game element
     * @param name
     * @param url
     * @param typ
     * @return
     */
    public static  Spielst getSpielt(String name, String url, ActivityType typ){
        return new Spielst(name,url,typ);
    }


    protected Spielst(String name, String url, ActivityType typ) {
        this.name = name;
        this.url = url;
        this.type = typ;
    }

    @Override
    public boolean isRich() {
        return false;
    }

    @Override
    public RichPresence asRichPresence() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public ActivityType getType() {
        return type;
    }

    @Nullable
    @Override
    public Timestamps getTimestamps() {
        return null;
    }

    /**
     * Setzt das Rich Presence element
     * @param status
     * @param state
     * @param details
     * @param application_id
     * @param large_image
     * @param small_image
     * @param large_text
     * @param small_text
     * @param timestamp
     * @param since
     */
    public void setPresence(OnlineStatus status, String state, String details, String application_id,
                            String large_image, String small_image, String large_text, String small_text,
                            long timestamp, long since){
        if(Bot.getInstance().getBot() == null)
            return;
        PresenceImpl presence = new PresenceImpl((JDAImpl) Bot.getInstance().getBot()).setCacheActivity(this)
                .setCacheStatus(status);
        JSONObject data = presence.getFullPresence();
        JSONObject game = data.getJSONObject("game");
        game.put("state",state);
        game.put("details", details);
        game.put("application_id", application_id);
        JSONObject assets = new JSONObject();
        game.put("timestamps",new JSONObject().put("start",timestamp));
        assets.put("large_image", large_image);
        assets.put("small_image", small_image);
        assets.put("large_text", large_text);
        assets.put("small_text", small_text);
        game.put("assets",assets);
        game.put("since",since);
        data.put("game",game);
        ((JDAImpl) Bot.getInstance().getBot())
                .getClient().send((new JSONObject()).put("d", data).put("op", 3).toString());
        System.out.println((new JSONObject()).put("d", data).put("op", 3).toString());
    }
}
