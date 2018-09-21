package de.letsplaybar.discordbot.command.commands;

import de.letsplaybar.discordbot.command.command.Command;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Answer implements Command {

    private List<String> positiv;
    private List<String> negativ;

    public Answer(){
        positiv = new ArrayList<>();
        negativ = new ArrayList<>();
        positiv.add("Bin dafür");
        positiv.add("Ja");
        positiv.add("Aber sowas von");
        positiv.add("Sicher Kumpel!");
        positiv.add("Aufjedenfall");
        positiv.add("Jo");
        positiv.add("Stehe voll hinter dir");
        negativ.add("Bin dagegen");
        negativ.add("Nein");
        negativ.add("Sag mal spinnst du?");
        negativ.add("Sicher nicht");
        negativ.add("Nur über meine Leiche");
        negativ.add("Wenn du dich traust");
        negativ.add("wirklich?");
    }


    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) throws ParseException, IOException {
        Random random = new Random();
        event.getChannel().sendMessage("Frage: "+ Arrays.asList(args).stream().map(s-> s+" ")
                .collect(Collectors.joining())+"\n"+
                "Antwort: "+(random.nextInt()%2 == 0? positiv.get(random.nextInt(positiv.size())) :
                negativ.get(random.nextInt(negativ.size())))).queue();
    }

    @Override
    public void executed(boolean success, GuildMessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public String getPerm() {
        return "cmd.answer";
    }
}
