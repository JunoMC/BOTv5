package main.bot.commands;

import main.bot.BOT;
import main.java.com.amihaiemil.eoyaml.YamlSequence;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MODULE extends ListenerAdapter {
    private final List<String> cmds = Arrays.asList(
            "BAN",
            "KICK",
            "CLEAR",
            "TICKET"
    );

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        User sender = event.getAuthor();
        TextChannel cur_channel = event.getChannel();

        Date date = new Date();
        String time = "[" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + " " + date.getDate() + "/" + (date.getMonth() + 1) + "/" + (date.getYear() + 1900) + "]: ";

        String prefix = BOT.config.yamlMapping("settings").yamlMapping("commands").string("start");
        YamlSequence channels = BOT.config.yamlMapping("settings").yamlMapping("commands").yamlSequence("channels");

        if (sender.isBot()) return;

        List<String> channelList = Collections.synchronizedList(new ArrayList<>());

        channels.forEach(nodes -> channelList.add(nodes.asScalar().value()));

        if (!channelList.contains(cur_channel.getId())) {
            BOT.WriteFile(BOT.logFile.getPath(), time + sender.getAsTag() + ": " + event.getMessage().getContentStripped() + " [" + cur_channel.getParent().getName() + "/" + cur_channel.getName() + "]");
            return;
        }

        event.getMessage().delete().queue();

        if (!msg.startsWith(prefix)) {
            EmbedBuilder embed = new EmbedBuilder();

            embed.setColor(Color.HSBtoRGB(0, 100, 57));
            embed.setDescription("Hi " + sender.getAsMention() + ", you can not chat here!");
            embed.setFooter("https://autobuy.io/@PurseAlts");
            embed.setTimestamp(event.getMessage().getTimeCreated());

            cur_channel.sendMessage(embed.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
            BOT.WriteFile(BOT.logFile.getPath(), time + sender.getAsTag() + ": " + event.getMessage().getContentStripped() + " [" + cur_channel.getParent().getName() + "/" + cur_channel.getName() + "]");
            return;
        }

        BOT.WriteFile(BOT.logFile.getPath(), time + sender.getAsTag() + " had use command: " + event.getMessage().getContentStripped());

        String cmd;
        String[] args;

        if (msg.contains(" ")) {
            cmd = msg.substring(1, msg.indexOf(" "));
            args = msg.substring(msg.indexOf(" ") + 1).split(" ");
        } else {
            cmd = msg.substring(1);
            args = null;
        }

        cmd = cmd.toUpperCase();

        if (!cmds.contains(cmd)) {
            EmbedBuilder embed = new EmbedBuilder();

            embed.setColor(Color.HSBtoRGB(0, 100, 57));
            embed.setDescription("Hi " + sender.getAsMention() + ", command **" + msg + "** is not exist!");
            embed.setFooter("https://autobuy.io/@PurseAlts");
            embed.setTimestamp(event.getMessage().getTimeCreated());

            cur_channel.sendMessage(embed.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
            return;
        }

        YamlSequence staffs = BOT.config.yamlMapping("settings").yamlSequence("staff-member-id");
        String adminRole = BOT.config.yamlMapping("settings").string("admin-role-id");

        if (sender.isBot()) return;

        List<String> staffList = Collections.synchronizedList(new ArrayList<>());

        staffs.forEach(nodes -> staffList.add(nodes.asScalar().value()));

        new BAN(cmd, args, event.getMessage()).execute(prefix, sender, cur_channel, staffList, adminRole);
        new KICK(cmd, args, event.getMessage()).execute(prefix, sender, cur_channel, staffList, adminRole);
        new CLEAR(cmd, args, event.getMessage()).execute(prefix, sender, cur_channel, staffList, adminRole);
        new TICKET(cmd, args, event.getMessage()).execute(prefix, sender, cur_channel, staffList, adminRole);
    }
}