package de.letsplaybar.discordbot.command.commands;

import de.letsplaybar.discordbot.command.command.Command;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Answer implements Command {

    private List<String> positiv;
    private List<String> neutral;
    private List<String> negativ;

    public Answer(){
        positiv = new ArrayList<>();
        negativ = new ArrayList<>();
        neutral = new ArrayList<>();
        positiv.add("Bin dafür");
        positiv.add("Ja");
        positiv.add("Aber sowas von");
        positiv.add("Sicher Kumpel!");
        positiv.add("Aufjedenfall");
        positiv.add("Jo");
        positiv.add("Stehe voll hinter dir");
        neutral.add("vieleicht");
        neutral.add("ist mir latte");
        neutral.add("interessiert mich nicht");
        negativ.add("Bin dagegen");
        negativ.add("Nein");
        negativ.add("Sag mal spinnst du?");
        negativ.add("Sicher nicht");
        negativ.add("Nur über meine Leiche");
        negativ.add("Wenn du dich traust");
        negativ.add("wirklich?");
    }


    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) {
        return false;
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) throws ParseException, IOException {
        Random random = new Random();
        int i = random.nextInt();
        if(eventGuild != null)
            eventGuild.getChannel().sendMessage("Frage: "+ Arrays.asList(args).stream().map(s-> s+" ")
                .collect(Collectors.joining())+"\n"+
                "Antwort: "+(i%3 == 0? positiv.get(random.nextInt(positiv.size())) : i%3 == 1?
                neutral.get(random.nextInt(neutral.size())) :
                negativ.get(random.nextInt(negativ.size())))).queue();
        if(eventPrivat != null)
            eventPrivat.getChannel().sendMessage("Frage: "+ Arrays.asList(args).stream().map(s-> s+" ")
                    .collect(Collectors.joining())+"\n"+
                    "Antwort: "+(i%3 == 0? positiv.get(random.nextInt(positiv.size())) : i%3 == 1?
                    neutral.get(random.nextInt(neutral.size())) :
                    negativ.get(random.nextInt(negativ.size())))).queue();
    }

    @Override
    public void executed(boolean success, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) {

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
