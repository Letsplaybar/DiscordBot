package de.letsplaybar.discordbot.command.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author Letsplaybar
 *         Created on 21.08.2017.
 */
public class JSONGetter {
    /**
     * bekomme aus einer url ein {@link JSONObject}
     * @param url restAPI link
     * @return
     * @throws IOException
     * @throws JSONException
     */
    JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        return readJsonFromUrl(is);
    }

    /**
     * bekomme aus einem Input ein {@link JSONObject}
     * @param is Imputstream
     * @return
     * @throws IOException
     * @throws JSONException
     */
    JSONObject readJsonFromUrl(InputStream is) throws IOException, JSONException {
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    /**
     * erstellt aus einen reader einen String
     * @param rd
     * @return
     * @throws IOException
     */
    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}
