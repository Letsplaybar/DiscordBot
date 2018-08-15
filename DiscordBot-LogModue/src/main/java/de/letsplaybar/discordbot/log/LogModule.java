package de.letsplaybar.discordbot.log;

import de.letsplaybar.discordbot.log.utils.LogStreamParse;
import de.letsplaybar.discordbot.main.module.Module;
import javafx.scene.control.TextArea;
import lombok.Getter;

import java.io.IOException;

public class LogModule implements Module {

    private LogStreamParse logStreamParse;
    private static @Getter LogModule instance;

    @Override
    public void register() {
        instance = this;
        logStreamParse = new LogStreamParse();
        logStreamParse.register();
    }

    @Override
    public void load() {
        try {
            logStreamParse.onLoad();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unload() {
        logStreamParse.onUnload();
    }

    public void registerGUI(TextArea log){
        logStreamParse.setLog_field(log);
    }

    public void reset(){
        logStreamParse.reset();
    }

    public String getLog(){
        try {
            return logStreamParse.getLog();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("No Log Found");
    }
}
