package de.letsplaybar.discordbot.commandline;

import de.letsplaybar.discordbot.commandline.command.Command;
import de.letsplaybar.discordbot.commandline.command.CommandHandler;
import de.letsplaybar.discordbot.commandline.command.CommandParser;
import de.letsplaybar.discordbot.commandline.commands.*;
import de.letsplaybar.discordbot.commandline.util.CancelableReader;
import de.letsplaybar.discordbot.main.module.Module;
import lombok.Getter;

import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Scanner;

public class CommandlineModule implements Module {

    private static @Getter CommandlineModule instance;
    private @Getter CancelableReader scanner;
    private Thread thread;

    @Override
    public void register() {
        instance = this;
    }

    @Override
    public void load() {
        scanner = new CancelableReader(new InputStreamReader(System.in));
        registerCommand("discord", new Discord());
        registerCommand("help", new Help());
        registerCommand("set", new Set());
        registerCommand("permission", new Permissions());
        registerCommand("exit", new Exit());
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String line;
                while ((line =scanner.readLine())!= null){
                    try {
                        System.out.println(line);
                        CommandHandler.handleCommand(new CommandParser().parse(line));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    private void registerCommand(String cmd, Command command){
        CommandHandler.commands.put(cmd,command);
    }

    private void unregisterCommand(String cmd){
        CommandHandler.commands.remove(cmd);
    }

    @Override
    public void unload() {
        scanner.cancelRead();
        thread.interrupt();
        unregisterCommand("discord");
        unregisterCommand("help");
        unregisterCommand("set");
        unregisterCommand("permission");
        unregisterCommand("exit");
    }
}
