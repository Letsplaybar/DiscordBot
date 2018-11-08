package de.letsplaybar.discordbot.record.commands;

import de.letsplaybar.discordbot.command.command.Command;
import de.letsplaybar.discordbot.command.utils.Plot;
import de.letsplaybar.discordbot.record.RecordModule;
import de.letsplaybar.discordbot.record.utils.RecordingHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

public class Record implements Command {

    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) throws ParseException, IOException {
        if(args.length ==1){
            if(args[0].equalsIgnoreCase("start")){
                event.getGuild().getAudioManager().getConnectedChannel().getMembers().forEach(user ->{
                    PrivateChannel channel = event.getMember().getUser().openPrivateChannel().complete();
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Datenschutzerklärung");
                    builder.addField("Hinweis", "In diesem Channel wird grade jeder User aufgenommen," +
                            " wenn dir das nicht recht ist verlasse den channel bitte", false);
                    builder.addField("Verarbeitung", "Diese Aufnahme wird nur kurzfristig gespeichert," +
                            " sofern es keine Audiomessage ist. Diese wird nur in den Channel gesendet," +
                            " wo der Befehl ausgeführt wurde. Sonst wird die Aufnahme nur genutzt um den Bot" +
                            " Sprachbefehle zu übergeben", false);
                    builder.addField("Einverständniserklärung", "Mit dem Aufenthalt in dem Channel stimmst" +
                                    " du der Verarbeitung und kurzzeitigen Speicherung zu!",
                            false);
                    channel.sendMessage(builder.build()).queue();
                });
                RecordModule.getInstance().setHandler(new RecordingHandler("Testcall"));
                event.getGuild().getAudioManager().setReceivingHandler(RecordModule.getInstance().getHandler());
            }else if(args[0].equalsIgnoreCase("end")){
                RecordingHandler handler =RecordModule.getInstance().getHandler();
                event.getGuild().getAudioManager().setReceivingHandler(null);
                handler.end();

                event.getChannel().sendFile(handler.getFile()).queue();
            }else if(args[0].equalsIgnoreCase("create")){
                System.out.println("Start");
                File file = null;
                try {
                    file = new Plot().savePlot(300,300,
                            Arrays.asList(new Plot.Point[]{
                                    new Plot.Point(10,10,0),new Plot.Point(10,20,0),new Plot.Point(20,10,0),
                                    new Plot.Point(100,100,1),new Plot.Point(110,100,1),new Plot.Point(100,110,1),
                                    new Plot.Point(10,100,2),new Plot.Point(10,110,2),new Plot.Point(20,100,2),
                                    new Plot.Point(100,10,3),new Plot.Point(110,10,3),new Plot.Point(100,20,3)
                            }),"testplot");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                event.getChannel().sendFile(file).queue();

            }
        }

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
        return "cmd.record";
    }
}
