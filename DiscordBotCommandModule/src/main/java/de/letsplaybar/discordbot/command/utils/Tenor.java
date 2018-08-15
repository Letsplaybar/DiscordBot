package de.letsplaybar.discordbot.command.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

/**
 * @author Letsplaybar
 *         Created on 21.08.2017.
 */
public class Tenor {

    private String[] url;
    private String name;
    private final String giphy = "https://api.tenor.com/v1/search?q=%s&limit=%a&key=LIVDSRZULELA";
    private int amount;

    public Tenor(String name, int amount){
        this.name = name;
        this.amount = amount;
    }

    public void load() throws IOException, JSONException {
        JSONObject json = new JSONGetter().readJsonFromUrl(giphy.replace("%s",name).replace("%a",String.valueOf(amount)));
        if(json.get("results")instanceof JSONArray) {
            JSONArray results = json.getJSONArray("results");
            url = new String[results.length()];
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.getJSONObject(i);
                if (obj.get("media") instanceof JSONArray) {
                    JSONArray media = obj.getJSONArray("media");
                    JSONObject gobj = media.getJSONObject(0);
                    JSONObject gif = gobj.getJSONObject("gif");
                    url[i] = (String) gif.get("url");
                }
            }
        }
    }


    public String getRndGif(int i){
        String rnd = url[new Random().nextInt(url.length)];
        return rnd;
    }

}
