package de.letsplaybar.discordbot.command.commands;

import de.letsplaybar.discordbot.command.CommandModule;
import de.letsplaybar.discordbot.command.command.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class Users implements Command {
    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) {
        return false;
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent eventGuild, PrivateMessageReceivedEvent eventPrivat) throws ParseException, IOException {
        if(eventPrivat != null)
            return;
        if(args.length == 0){
            sendUserInfo(eventGuild.getMember(),eventGuild.getChannel());
        }else if (args.length == 1){
            if(eventGuild.getMessage().getMentionedMembers().size() > 0){
                sendUserInfo(eventGuild.getMessage().getMentionedMembers().get(0),eventGuild.getChannel());
            }else {
                eventGuild.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Help")
                        .setDescription("for help make"+ CommandModule.getInstance().getCommand()+"help").build())
                        .queue();
            }
        }else {
            eventGuild.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Help")
                    .setDescription("for help make"+ CommandModule.getInstance().getCommand()+"help").build()).queue();
        }
    }


    private void sendUserInfo(Member user, TextChannel channel){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss 'UTC'XXX yyyy");
        EmbedBuilder builder =new EmbedBuilder().setTitle(":smiling_imp: Userinfos://"+user.getUser().getName())
                .setColor(Integer.valueOf("F2F5A9",16))
                .addField("Nickname",(user.getNickname()== null ? user.getEffectiveName():user.getNickname()),false)
                .addField("UserTag",user.getUser().getName()
                        + "#" + user.getUser().getDiscriminator(),false)
                .addField("UserID",user.getUser().getId(),false)
                .addField("Onlinestatus",user.getOnlineStatus().getKey(),false);
            try {
                builder.addField("Spielt",user.getGame().getType()+"\n"+
                    user.getGame().getName()+"\n"+
                    (user.getGame().isRich()? user.getGame().asRichPresence().getDetails(): ""),false);
            }catch (Exception ex){

            }
            builder.addField("Rollen",user.getRoles().stream().map(s -> s.getName()+", ")
                        .collect(Collectors.joining()),false)
                .addField("Server betreten",user.getJoinDate().format(formatter),false)
                .addField("Discord beigetreten",user.getUser().getCreationTime().format(formatter),false)
                .setThumbnail(user.getUser().getAvatarUrl());

        channel.sendMessage( builder.build()).queue();
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
        return "cmd.user";
    }
}
