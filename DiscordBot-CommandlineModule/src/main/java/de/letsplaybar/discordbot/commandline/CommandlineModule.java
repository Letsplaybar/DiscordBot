package de.letsplaybar.discordbot.commandline;

import de.letsplaybar.discordbot.commandline.command.Command;
import de.letsplaybar.discordbot.commandline.command.CommandHandler;
import de.letsplaybar.discordbot.commandline.command.CommandParser;
import de.letsplaybar.discordbot.commandline.commands.Discord;
import de.letsplaybar.discordbot.commandline.commands.Help;
import de.letsplaybar.discordbot.commandline.commands.Permissions;
import de.letsplaybar.discordbot.commandline.commands.Set;
import de.letsplaybar.discordbot.main.module.Module;
import lombok.Getter;

import java.text.ParseException;
import java.util.Scanner;

public class CommandlineModule implements Module {

    private static @Getter CommandlineModule instance;
    private Scanner scanner;
    private Thread thread;

    @Override
    public void register() {
        instance = this;
    }

    @Override
    public void load() {
        scanner = new Scanner(System.in);
        registerCommand("discord", new Discord());
        registerCommand("help", new Help());
        registerCommand("set", new Set());
        registerCommand("permission", new Permissions());
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (scanner.hasNextLine()){
                    try {
                        CommandHandler.handleCommand(new CommandParser().parse(scanner.nextLine()));
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
        scanner.close();
        thread.interrupt();
        unregisterCommand("discord");
        unregisterCommand("help");
        unregisterCommand("set");
        unregisterCommand("permission");
    }
}
