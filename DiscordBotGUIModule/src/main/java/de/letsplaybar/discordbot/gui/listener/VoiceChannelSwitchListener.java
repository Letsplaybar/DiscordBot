package de.letsplaybar.discordbot.gui.listener;

import de.letsplaybar.discordbot.gui.GUIModule;
import de.letsplaybar.discordbot.main.Bot;
import javafx.application.Platform;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VoiceChannelSwitchListener extends ListenerAdapter {


    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(event.getMember().equals(event.getGuild().getMemberById(Bot.getInstance().getBot().getSelfUser().getId())))
                    GUIModule.getInstance().getGui().getController().getChannel().setValue(event.getChannelJoined().getName());
            }
        });
    }

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(event.getMember().equals(event.getGuild().getMemberById(Bot.getInstance().getBot().getSelfUser().getId())))
                    GUIModule.getInstance().getGui().getController().getChannel().setValue(event.getChannelJoined().getName());
            }
        });
    }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(event.getMember().equals(event.getGuild().getMemberById(Bot.getInstance().getBot().getSelfUser().getId())))
                    GUIModule.getInstance().getGui().getController().getChannel().setValue(null);
            }
        });
    }
}
