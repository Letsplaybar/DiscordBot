package de.letsplaybar.discordbot.commandline.commands;

import de.letsplaybar.discordbot.commandline.CommandlineModule;
import de.letsplaybar.discordbot.commandline.command.Command;
import de.letsplaybar.discordbot.main.Main;

import java.io.IOException;
import java.text.ParseException;

public class Exit implements Command {
    @Override
    public boolean called(String[] args) {
        return false;
    }

    @Override
    public void action(String[] args) throws ParseException, IOException {

    }

    @Override
    public void executed(boolean success) {
        CommandlineModule.getInstance().getScanner().cancelRead();
        Main.stop();
    }

    @Override
    public String help() {
        return null;
    }
}
