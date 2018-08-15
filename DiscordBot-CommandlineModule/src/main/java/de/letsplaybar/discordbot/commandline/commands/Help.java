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
        System.out.println("discord start - Start the Bot");
        System.out.println("discord stop - Stop the Bot");
        System.out.println("discord restart - Restart the Bot");
    }

    @Override
    public void executed(boolean success) {

    }

    @Override
    public String help() {
        return null;
    }
}
