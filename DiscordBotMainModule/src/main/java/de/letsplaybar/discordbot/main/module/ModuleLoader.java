package de.letsplaybar.discordbot.main.module;

import lombok.Getter;

import java.util.*;

public class ModuleLoader {

    private @Getter static ModuleLoader instance;

    private Map<String, Module> modules;

    private List<String> activate;

    public ModuleLoader(){
        instance = this;
        modules = new HashMap<>();
        activate = new ArrayList<>();
    }

    /**
     * regestriet ein Module damit es geladen wird
     * @param module {@link Module} das zu regestrierende Module
     * @throws ModuleException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void registerModule(Class<? extends  Module> module) throws ModuleException, IllegalAccessException, InstantiationException {
        Module mod = module.newInstance();
        for (String s : mod.getRequementsModule())
            if(!getRegisterModulesName().contains(s))
                throw new ModuleException("Requied Module missing: "+ s);
        modules.put(module.getSimpleName(),mod);
        mod.register();
    }

    /**
     * läd alle Regestrierten {@link Module}
     */
    public void load(){
        load("SQLModule");
        modules.keySet().stream().forEach(a -> load(a));
    }

    /**
     * läd ein bestimmtes module
     * @param name der Simpleclassname des {@link Module}
     */
    public void load(String name){
        if(!activate.contains(name)){
            modules.get(name).load();
            activate.add(name);
        }
    }

    /**
     * unläd ein {@link Module}
     */
    public void unload(){
        modules.keySet().stream().forEach(a -> unload(a));
    }

    /**
     * Unläd ein {@link Module}
     * @param name das zu unloadene {@link Module}
     */
    public void unload(String name){
        if(activate.contains(name)){
            modules.get(name).unload();
            activate.remove(name);
        }
    }

    /**
     * bekomme ein Module
     * @param module Name des Modules
     * @return {@link Module}
     */
    public Module getModule(String module){
        if(modules.keySet().contains(module)){
            return modules.get(module);
        }
        return null;
    }

    /**
     * bekomme eine Liste aller regstrierten Module
     * @return
     */
    public List<String> getRegisterModulesName(){
        return new ArrayList<>(modules.keySet());
    }

    /**
     * bekomme eine liste aller geladenen module
     * @return
     */
    public List<String> getActivateModules() {
        return activate;
    }
}
