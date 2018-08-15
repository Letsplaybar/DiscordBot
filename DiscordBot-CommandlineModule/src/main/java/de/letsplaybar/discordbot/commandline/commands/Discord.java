package de.letsplaybar.discordbot.commandline.commands;

import de.letsplaybar.discordbot.command.utils.CommandListener;
import de.letsplaybar.discordbot.commandline.command.Command;
import de.letsplaybar.discordbot.gui.GUIModule;
import de.letsplaybar.discordbot.gui.aplication.Controller;
import de.letsplaybar.discordbot.main.Bot;
import de.letsplaybar.discordbot.main.module.ModuleLoader;
import de.letsplaybar.discordbot.sql.SQLModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.text.ParseException;

public class Discord implements Command {
    @Override
    public boolean called(String[] args) {
        return false;
    }

    @Override
    public void action(String[] args) throws ParseException, IOException {

        if(args.length == 1){
            if(args[0].equalsIgnoreCase("start")){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SQLModule sql = SQLModule.getInstance();
                        try {
                            Bot.getInstance().startBot(sql.getToken(),sql.getSpielt(),"https://twitch.tv/letsplaybar", Game.GameType.valueOf(sql.getStreamt()),sql.getOnline(),sql.getState(),sql.getDetails(),sql.getAID(),sql.getLI(),sql.getSI(),sql.getLT(),sql.getST(),System.currentTimeMillis(),System.currentTimeMillis(),(ModuleLoader.getInstance().getActivateModules().contains("CommandModule"))?new CommandListener():null);
                        } catch (LoginException e) {
                            e.printStackTrace();
                        }
                        if(ModuleLoader.getInstance().getRegisterModulesName().contains("GUIModule")){
                            Controller con = GUIModule.getInstance().getGui().getController();
                            ObservableList<String> list = FXCollections.observableArrayList();
                            Bot.getInstance().getBot().getGuilds().stream().forEach(g ->{
                                list.add("==========["+g.getName()+"]==========");
                                g.getVoiceChannels().stream().forEach(v->{
                                    list.add(v.getName());
                                    con.getId().put(v.getName(),v.getIdLong());
                                });
                            });
                            con.getChannel().setItems(list);
                        }
                        System.out.println("Bot Started");
                    }
                }).start();
            }else if(args[0].equalsIgnoreCase("stop")){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bot.getInstance().stopBot();
                        if(ModuleLoader.getInstance().getRegisterModulesName().contains("GUIModule")){
                            Controller con = GUIModule.getInstance().getGui().getController();
                            con.getChannel().setValue(null);
                            con.getChannel().getItems().clear();
                        }
                        System.out.println("Bot stopped");
                    }
                }).start();
            }else if(args[0].equalsIgnoreCase("restart")){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bot.getInstance().stopBot();
                        if(ModuleLoader.getInstance().getRegisterModulesName().contains("GUIModule")){
                            Controller con = GUIModule.getInstance().getGui().getController();
                            con.getChannel().setValue(null);
                            con.getChannel().getItems().clear();
                        }
                        System.out.println("Bot stopped");
                        SQLModule sql = SQLModule.getInstance();
                        try {
                            Bot.getInstance().startBot(sql.getToken(),sql.getSpielt(),"https://twitch.tv/letsplaybar", Game.GameType.valueOf(sql.getStreamt()),sql.getOnline(),sql.getState(),sql.getDetails(),sql.getAID(),sql.getLI(),sql.getSI(),sql.getLT(),sql.getST(),System.currentTimeMillis(),System.currentTimeMillis(),(ModuleLoader.getInstance().getActivateModules().contains("CommandModule"))?new CommandListener():null);
                        } catch (LoginException e) {
                            e.printStackTrace();
                        }
                        if(ModuleLoader.getInstance().getRegisterModulesName().contains("GUIModule")){
                            Controller con = GUIModule.getInstance().getGui().getController();
                            ObservableList<String> list = FXCollections.observableArrayList();
                            Bot.getInstance().getBot().getGuilds().stream().forEach(g ->{
                                list.add("==========["+g.getName()+"]==========");
                                g.getVoiceChannels().stream().forEach(v->{
                                    list.add(v.getName());
                                    con.getId().put(v.getName(),v.getIdLong());
                                });
                            });
                            con.getChannel().setItems(list);
                        }
                        System.out.println("Bot Started");
                    }
                }).start();
            }else {
                System.out.println("help");
            }
        }else{
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
