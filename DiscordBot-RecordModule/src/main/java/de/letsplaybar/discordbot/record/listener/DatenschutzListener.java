package de.letsplaybar.discordbot.record.listener;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class DatenschutzListener extends ListenerAdapter {

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        if(event.getGuild().getAudioManager().isConnected())
            if(event.getChannelJoined().getId().equals(event.getGuild().getAudioManager().getConnectedChannel().getId())){
                if(event.getGuild().getAudioManager().getReceiveHandler().canReceiveCombined()) {
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
                }
            }
    }

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
        if(event.getGuild().getAudioManager().isConnected())
            if(event.getChannelJoined().getId().equals(event.getGuild().getAudioManager().getConnectedChannel().getId())){
                if(event.getGuild().getAudioManager().getReceiveHandler().canReceiveCombined()) {
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
                }
            }
    }
}
