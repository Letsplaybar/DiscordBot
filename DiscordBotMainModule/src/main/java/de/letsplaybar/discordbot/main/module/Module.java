package de.letsplaybar.discordbot.main.module;

import java.util.ArrayList;
import java.util.List;

public interface Module {

    /**
     * Module die benötigt werden, damit dieses läd
     * @return
     */
    default List<String> getRequementsModule(){
        return new ArrayList<>();
    };

    /**
     * Sachen die beim regestrieren des modules gemacht werden sollen
     */
    void register();

    /**
     * sachen die beim laden des modules gemacht werden sollen
     */
    void load();

    /**
     * sachen die beim entladen des Modules gemacht werden sollen
     */
    void unload();

    /**
     * der name des Modules
     * @return
     */
    default String getSimpleName(){
        return getClass().getSimpleName();
    }

}
