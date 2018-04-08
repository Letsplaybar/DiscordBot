package de.letsplaybar.discordbot.main.module;

import java.util.ArrayList;
import java.util.List;

public interface Module {

    default List<String> getRequementsModule(){
        return new ArrayList<>();
    };

    void register();

    void load();

    void unload();

    default String getSimpleName(){
        return getClass().getSimpleName();
    }

}
