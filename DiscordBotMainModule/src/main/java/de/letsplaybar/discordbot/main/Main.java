package de.letsplaybar.discordbot.main;

import com.google.common.reflect.ClassPath;
import de.letsplaybar.discordbot.main.module.Module;
import de.letsplaybar.discordbot.main.module.ModuleLoader;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private @Getter static Main instance;

    public  Main(){
        File data = new File(".data");
        System.setProperty("file.encoding" , "UTF-8");
        if(!data.exists()){
            data.mkdirs();
            if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
                Path path = Paths.get(data.getPath());
                try {
                    Files.setAttribute(path,"dos:hidden", true);
                } catch (UnsupportedOperationException x) {
                    System.err.println("DOS file" +
                            " attributes not supported:" + x);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        new ModuleLoader();
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

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            ModuleLoader.getInstance().unload();
            System.exit(0);
        }
    }


    public static void main(String[] args){
        new Main();
    }

    private static void registerModule() {
        String disable = (System.getProperty("Module-Inactive")!= null)? System.getProperty("Module-Inactive"):"";
        List<String> disMod = Arrays.asList(disable.split(","));
        getModules().stream().forEach(module ->{
            if(!disMod.contains(module.getSimpleName())) {
                try {
                    ModuleLoader.getInstance().registerModule(module);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    public static List<Class<? extends Module>> getModules(){
        ArrayList<Class<? extends Module>> list = new ArrayList<>();
        try{
            ClassPath path = ClassPath.from(new URLClassLoader(new URL[]{Main.class.getResource("module")}));
            for(ClassPath.ClassInfo info : path.getTopLevelClassesRecursive("de.letsplaybar.discordbot")){
                if(Arrays.asList(info.load().getInterfaces()).contains(Module.class)){
                    Class<?> clazz = info.load();
                    for(Class<?> c : clazz.getInterfaces())
                        if(c.equals(Module.class)) {
                            list.add((Class<? extends Module>) clazz);
                            break;
                        }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void stop(){
        synchronized (instance){
            instance.notify();
        }
    }
}
