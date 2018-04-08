package de.letsplaybar.discordbot.main;

import de.letsplaybar.discordbot.command.CommandModule;
import de.letsplaybar.discordbot.main.module.Module;
import de.letsplaybar.discordbot.main.module.ModuleLoader;
import de.letsplaybar.discordbot.sql.SQLModule;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class Main {

    private @Getter static Main instance;

    public  Main(){
        instance = this;
        try {
            registerModule();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ModuleLoader.getInstance().load();
                }
            }).start();

            synchronized (instance){
                instance.wait();
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            ModuleLoader.getInstance().unload();
        }
    }


    public static void main(String[] args){
        new Main();
    }

    private static void registerModule() throws InstantiationException, IllegalAccessException {
        String disable = System.getProperty("Module-Deaktive");
        List<String> disMod = Arrays.asList(disable.split(","));

        ModuleLoader.getInstance().registerModule(Bot.class);
        if(!disMod.contains("SQLModule"))
            ModuleLoader.getInstance().registerModule(SQLModule.class);
        if(!disMod.contains("CommandModule"))
            ModuleLoader.getInstance().registerModule(CommandModule.class);

    }

    public static void stop(){
        synchronized (instance){
            instance.notify();
        }
        System.exit(0);
    }
}
