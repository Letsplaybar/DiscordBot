package de.letsplaybar.discordbot.command.utils;


import de.letsplaybar.discordbot.sql.SQLModule;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

/**
 * Created by Letsplaybar on 21.08.17.
 */
public class Image {

    private String[] url;
    private String name;
    private int amount;

    /**
     *
     * @param name nach dem Bild was gesucht werden soll
     * @param amount die anzahl der zuladenen bilder
     */
    public Image(String name, int amount){
        this.name = name;
        this.amount = amount;
        url = new String[amount];
    }

    /**
     * läd bilder aus Bing runter und cached sie
     * @throws IOException
     * @throws URISyntaxException
     */
    public void load() throws IOException, URISyntaxException {
        HttpClient httpclient = HttpClients.createDefault();

        URIBuilder builder = new URIBuilder("https://api.cognitive.microsoft.com/bing/v7.0/images/search");

        builder.setParameter("q", name);
        builder.setParameter("count", ""+amount);
        builder.setParameter("mkt", "de-de");
        builder.setParameter("safeSearch", "Off");

        URI uri = builder.build();
        HttpGet request = new HttpGet(uri);
        request.setHeader("Ocp-Apim-Subscription-Key",  SQLModule.getInstance().getBingAPI());

        HttpResponse response = httpclient.execute(request);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            JSONObject bing = new JSONGetter().readJsonFromUrl(entity.getContent());
            if(bing.get("value")instanceof JSONArray){
                JSONArray value = bing.getJSONArray("value");
                for(int i = 0; i<value.length();i++){
                    JSONObject obj = value.getJSONObject(i);
                    url[i] = (String) obj.get("contentUrl");
                }
            }
        }
    }


    /**
     * sucht ein rnd Bild aus den gechacheden raus
     * @return
     */
    public String getRndImage(){
        return url[new Random().nextInt(url.length)];
    }


}
