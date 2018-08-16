package de.letsplaybar.discordbot.commandline.commands;

import de.letsplaybar.discordbot.command.CommandModule;
import de.letsplaybar.discordbot.commandline.command.Command;
import de.letsplaybar.discordbot.sql.SQLModule;

import java.io.IOException;
import java.text.ParseException;

public class Set implements Command {
    @Override
    public boolean called(String[] args) {
        return false;
    }

    @Override
    public void action(String[] args) throws ParseException, IOException {
        if(args.length == 2){
            SQLModule sql = SQLModule.getInstance();
            if(args[0].equalsIgnoreCase("Token")){
                sql.setToken(args[1]);
                System.out.println("Token gesetzt");
            } else if(args[0].equalsIgnoreCase("WeatherAPI")){
                sql.setKey(args[1]);
                System.out.println("WeatherAPI-Token gesetzt");
            }else if(args[0].equalsIgnoreCase("Lizenz")){
                sql.setLizenz(args[1]);
                System.out.println("Lizenz gesetzt");
            }else if(args[0].equalsIgnoreCase("Giphy")){
                sql.setGiphy(args[1]);
                System.out.println("Giphy-Token gesetzt");
            }else if(args[0].equalsIgnoreCase("Bing")){
                sql.setBingAPI(args[1]);
                System.out.println("BingAPI-Token gesetzt");
            }else if(args[0].equalsIgnoreCase("Command")){
                CommandModule.getInstance().setCommand(args[1]);
                System.out.println("Command-Zeichen gestezt");
            }else{
                System.out.println("help");
            }
        }else {
            System.out.println("help");
        }
    }

    @Override
    public void executed(boolean success) {

    }

    @Override
    public String help() {
        return null;
    }
}
