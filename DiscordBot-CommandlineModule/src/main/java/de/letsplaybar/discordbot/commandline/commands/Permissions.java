package de.letsplaybar.discordbot.commandline.commands;

import de.letsplaybar.discordbot.commandline.command.Command;
import de.letsplaybar.discordbot.main.Bot;
import de.letsplaybar.discordbot.sql.SQLModule;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class Permissions implements Command {
    @Override
    public boolean called(String[] args) {
        return false;
    }

    //permission add <User> <perm>
    //permission remove <User> <perm>
    //permission remove <User>
    @Override
    public void action(String[] args) throws ParseException, IOException {
        SQLModule sql = SQLModule.getInstance();
        if(args.length == 2){
            if(args[0].equalsIgnoreCase("remove")){
                try {
                    sql.removeUser(args[1]);
                    System.out.println("User with id="+args[1]+" removed");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("help");
            }
        }else if(args.length == 3){
            if(args[0].equalsIgnoreCase("add")){
                try {
                    if(!sql.getSql().isInDatabase(args[1],"Permissions","Userid"))
                        sql.addUser(Bot.getInstance().getBot().getUserById(args[2]).getName(),args[2]);
                    sql.addPermisson(args[1],args[2]);
                    System.out.println("User with id="+args[1]+" permission="+args[2]+" addet");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else if(args[0].equalsIgnoreCase("remove")){
                try {
                    sql.removePermisson(args[1],args[2]);
                    System.out.println("User with id="+args[1]+" permission="+args[2]+" removed");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("help");
            }
        }else {
            System.out.println("help");
        }
    }

    @Override
    public void executed(boolean success) {

    }

    @Override
    public String help() {
        return null;
    }
}
