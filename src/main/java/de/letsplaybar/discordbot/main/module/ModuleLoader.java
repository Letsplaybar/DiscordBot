package de.letsplaybar.discordbot.main.module;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleLoader {

    private @Getter static ModuleLoader instance;

    private Map<String, Module> modules;

    private List<String> activate;

    public ModuleLoader(){
        instance = this;
        modules = new HashMap<>();
        activate = new ArrayList<>();
    }

    public void registerModule(Class<? extends  Module> module) throws ModuleException, IllegalAccessException, InstantiationException {
        Module mod = module.newInstance();
        for (String s : mod.getRequementsModule())
            if(!getRegisterModulesName().contains(s))
                throw new ModuleException("Requied Module missing: "+ s);
        modules.put(module.getSimpleName(),mod);
        mod.register();
    }

    public void load(){
        modules.keySet().stream().forEach(a -> load(a));
    }

    public void load(String name){
        if(!activate.contains(name)){
            modules.get(name).load();
            activate.add(name);
        }
    }

    public void unload(){
        modules.keySet().stream().forEach(a -> unload(a));
    }

    public void unload(String name){
        if(activate.contains(name)){
            modules.get(name).unload();
            activate.remove(name);
        }
    }

    public List<String> getRegisterModulesName(){
        return (List<String>) modules.keySet();
    }

    public List<String> getActivateModules() {
        return activate;
    }
}
