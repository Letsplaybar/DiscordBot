package de.letsplaybar.discordbot.main.utils;

import de.letsplaybar.discordbot.main.Bot;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.ActivityFlag;
import net.dv8tion.jda.api.entities.RichPresence;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;


/**
 * @author Letsplaybar
 * Created on 05.09.2017.
 */
public class Spielst implements RichPresence {

    private String name;
    private String url;
    private ActivityType type;
    private OnlineStatus status;
    private String state;
    private String details;
    private String application_id;
    private String large_image;
    private String small_image;
    private String large_text;
    private String small_text;
    private long timestamp;
    private long since;



    /**
     * holt dir das Game element
     * @param name
     * @param url
     * @param typ
     * @return
     */
    public static  Spielst getSpielt(String name, String url, ActivityType typ,OnlineStatus status, String state, String details, String application_id,
                                     String large_image, String small_image, String large_text, String small_text,
                                     long timestamp, long since){
        return new Spielst(name,url,typ,status,state,details,application_id,large_image,small_image,large_text,small_text,timestamp, since);
    }


    protected Spielst(String name, String url, ActivityType typ,OnlineStatus status, String state, String details, String application_id,
                      String large_image, String small_image, String large_text, String small_text,
                      long timestamp, long since) {
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



    public void setPresence(){
        Bot.getInstance().getBot().getPresence().setActivity(this);
    }

    @Override
    public long getApplicationIdLong() {
        return Long.parseLong(application_id);
    }

    @Nonnull
    @Override
    public String getApplicationId() {
        return application_id;
    }

    @Nullable
    @Override
    public String getSessionId() {
        return "4b2fdce12f639de8bfa7e3591b71a0d679d7c93f";
    }

    @Nullable
    @Override
    public String getSyncId() {
        return "e7eb30d2ee025ed05c71ea495f770b76454ee4e0";
    }

    @Override
    public int getFlags() {
        return ActivityFlag.JOIN_REQUEST.getOffset();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getSince() {
        return since;
    }

    @Override
    public EnumSet<ActivityFlag> getFlagSet() {
        return EnumSet.of(ActivityFlag.JOIN_REQUEST);
    }

    @Nullable
    @Override
    public String getState() {
        return state;
    }

    @Nullable
    @Override
    public String getDetails() {
        return details;
    }

    @Nullable
    @Override
    public Party getParty() {
        return new Party("party1234", 1, 6);
    }

    @Nullable
    @Override
    public Image getLargeImage() {
        return new Image(getApplicationIdLong(), large_image, large_text);
    }

    @Nullable
    @Override
    public Image getSmallImage() {
        return new Image(getApplicationIdLong(), small_image, small_text);
    }
}
