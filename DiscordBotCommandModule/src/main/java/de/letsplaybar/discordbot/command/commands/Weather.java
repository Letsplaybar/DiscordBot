package de.letsplaybar.discordbot.command.commands;

import de.letsplaybar.discordbot.command.command.Command;
import de.letsplaybar.discordbot.sql.SQLModule;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Letsplaybar
 *         Created on 10.05.2017.
 */
public class Weather implements Command {

    String key;
    String url;

    public Weather(){
        key = SQLModule.getInstance().getKey();
    }

    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) {
        if(args.length >0)
            return false;
        return true;
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) throws ParseException, IOException {
        url = "http://api.openweathermap.org/data/2.5/forecast?q=%stadt&mode=xml&appid=%key&lang=de";
        String stadt = "";
        for(String s : args){
            stadt +=" "+s;
        }
        stadt = stadt.substring(1);
        url = url.replace("%stadt",stadt.replace(" ","%20")).replace("%key",key);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            Document document = factory.newDocumentBuilder().parse(url);
            NodeList times = document.getElementsByTagName("time");
            if(eventGuild != null)
                eventGuild.getChannel().sendMessage("Die Wettervorhersage für "+stadt+" ist:").queue();
            if(eventPrivat !=  null)
                eventPrivat.getChannel().sendMessage("Die Wettervorhersage für "+stadt+" ist:").queue();
            for (int i = 0;i<8;i+=2) {
                Node time = times.item(i);
                NamedNodeMap timeAttributes =time.getAttributes();
                Node from = timeAttributes.getNamedItem("from");
                String t =from.getNodeValue();
                t = t.replace("T", " ");
                SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = format.parse(t);
                SimpleDateFormat end = new SimpleDateFormat("EEEEEEEEEE dd-MM-yy HH:mm");;
                NodeList child = time.getChildNodes();
                String mintemp = "";
                String maxtemp="";
                String clouds="";
                for(int x = 0; x<child.getLength();x++){
                    Node node = child.item(x);
                    if(node.getNodeName().equalsIgnoreCase("temperature")){
                        mintemp = node.getAttributes().getNamedItem("min").getNodeValue();
                        maxtemp = node.getAttributes().getNamedItem("max").getNodeValue();
                    }else if(node.getNodeName().equalsIgnoreCase("clouds")){
                        clouds = node.getAttributes().getNamedItem("value").getNodeValue();
                    }
                }
                double min= Double.valueOf(mintemp);
                double max= Double.valueOf(maxtemp);
                min -=273.15;
                max -=273.15;
                if(eventGuild != null)
                    eventGuild.getChannel().sendMessage(end.format(date)+": Das Wetter wird "+clouds+" bei einer Max Temperatur von: "+((Math.round(max*10))/10)+"°C und einer Min Temperatur von: "+((Math.round(min*10))/10)+"°C.").queue();
                if(eventPrivat != null)
                    eventPrivat.getChannel().sendMessage(end.format(date)+": Das Wetter wird "+clouds+" bei einer Max Temperatur von: "+((Math.round(max*10))/10)+"°C und einer Min Temperatur von: "+((Math.round(min*10))/10)+"°C.").queue();
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void executed(boolean success, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) {
        if (success){
            if(eventGuild != null)
                eventGuild.getChannel().sendMessage("Bitte gebe eine Stadt an").queue();
            if(eventPrivat != null)
                eventPrivat.getChannel().sendMessage("Bitte gebe eine Stadt an").queue();
        }
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public String getPerm() {
        return "cmd.weather";
    }
}
