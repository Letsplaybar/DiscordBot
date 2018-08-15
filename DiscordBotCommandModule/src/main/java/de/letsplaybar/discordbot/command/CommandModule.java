package de.letsplaybar.discordbot.command;

import de.letsplaybar.discordbot.command.command.Command;
import de.letsplaybar.discordbot.command.command.CommandHandler;
import de.letsplaybar.discordbot.command.commands.*;
import de.letsplaybar.discordbot.main.module.Module;
import de.letsplaybar.discordbot.sql.SQLModule;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandModule implements Module {

    private @Getter static CommandModule instance;

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
    public void load() {
        registerCommand("weather",new Weather());
        registerCommand("umfrage",new Umfrage());
        registerCommand("help",new Help());
        registerCommand("gifg",new GIPHY());
        registerCommand("gift",new TENOR());
        registerCommand("image",new IMAGE());
        registerCommand("music", new Music());
    }

    @Override
    public void unload() {
        unregisterCommand("weather");
        unregisterCommand("umfrage");
        unregisterCommand("help");
        unregisterCommand("gifg");
        unregisterCommand("gift");
        unregisterCommand("image");
        unregisterCommand("music");
    }

    public String getCommand() {
        ResultSet rs =SQLModule.getInstance().getSql().getResult("SELECT * FROM Settings WHERE Key='Command'");
        try {
            while(rs.next())
                return rs.getString("Value");
        } catch (SQLException e) {
        }
        return "/";
    }

    public void setCommand(String key){
        try {
            if(SQLModule.getInstance().getSql().isInDatabase("Command","Settings","Key"))
                SQLModule.getInstance().getSql().update("DELETE FROM Settings WHERE Key='Command'");
            SQLModule.getInstance().getSql().update("INSERT INTO Settings(Key,Value) VALUES ('Command','"+key+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registerCommand(String cmd, Command command){
        CommandHandler.commands.put(cmd,command);
    }


    public void unregisterCommand(String cmd){
        CommandHandler.commands.remove(cmd);
    }
}
