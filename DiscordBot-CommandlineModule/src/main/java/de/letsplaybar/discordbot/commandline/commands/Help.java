package de.letsplaybar.discordbot.commandline.commands;

import de.letsplaybar.discordbot.commandline.command.Command;

import java.io.IOException;
import java.text.ParseException;

public class Help implements Command {
    @Override
    public boolean called(String[] args) {
        return false;
    }

    @Override
    public void action(String[] args) throws ParseException, IOException {
        System.out.println("help - display all commands");
        System.out.println("exit - stop Programm");
        System.out.println("discord start - Start the Bot");
        System.out.println("discord stop - Stop the Bot");
        System.out.println("discord restart - Restart the Bot");
        System.out.println("set Token <token> - set UserDiscord-Token");
        System.out.println("set Weather <token> - set WeatherAPI-Token");
        System.out.println("set Lizenz <token - set Lizenz-Token");
        System.out.println("set Giphy <token> - set GiphyAPI-Token");
        System.out.println("set Bing <token> - set BingAPI-Token");
        System.out.println("set Command <commandsymbol> - set Commandsymbol for Discordcommands");
        System.out.println("permission remove <Userid> - remove User all Permissions");
        System.out.println("permission add <Userid> <Permission> - add User Permission");
        System.out.println("permission remove <Userid> <Permission> - remove User Permission");
    }

    @Override
    public void executed(boolean success) {

    }

    @Override
    public String help() {
        return null;
    }
}
