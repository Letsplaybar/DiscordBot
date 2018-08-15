package de.letsplaybar.discordbot.command.utils;

import de.letsplaybar.discordbot.sql.SQLModule;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

/**
 * @author Letsplaybar
 *         Created on 20.08.2017.
 */
public class Giphy {

    private String[] url;
    private String name;
    private final String giphy = "http://api.giphy.com/v1/gifs/search?q=%s&api_key=%k&limit=%a";
    private int amount;

    public Giphy(String name, int amount){
        this.name = name;
        this.amount = amount;
    }

    public void load() throws IOException, JSONException {
        String key = SQLModule.getInstance().getGiphy();
            JSONObject json = new JSONGetter().readJsonFromUrl(giphy.replace("%s",name).replace("%k",key).replace("%a",String.valueOf(amount)));
            if(json.get("data") instanceof JSONArray){
                JSONArray data = (JSONArray) json.get("data");
                url = new String[data.length()];
                for(int i =0; i<data.length();i++){
                    JSONObject obj = data.getJSONObject(i);
                    if(obj.get("images") instanceof JSONObject){
                        JSONObject images = (JSONObject) obj.get("images");
                        if(images.get("original") instanceof JSONObject){
                            JSONObject original = (JSONObject) images.get("original");
                            url[i] = (String) original.get("url");
                        }
                    }
                }
            }
    }

    public String getRndGif(int i){
        String rnd = url[new Random().nextInt(url.length)];
        return rnd;
    }





}
