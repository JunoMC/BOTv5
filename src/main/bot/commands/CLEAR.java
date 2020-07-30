package main.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class CLEAR {
    private final String cmd;
    private final String[] args;
    private final Message msg;

    public CLEAR(String cmd, @Nullable String[] args, Message msg) {
        this.cmd = cmd;
        this.args = args;
        this.msg = msg;
    }

    public void execute(String prefix, User sender, TextChannel textChannel, List<String> staffs, String adminRole) {
        if (!cmd.equalsIgnoreCase("CLEAR")) return;

        boolean isStaff = false;

        for (String staff : staffs) {
            if (staff.equalsIgnoreCase(sender.getAsTag())) {
                isStaff = true;
                break;
            }
        }

        boolean isAdminRole = Objects.requireNonNull(textChannel.getGuild().getMember(sender)).getRoles().contains(textChannel.getGuild().getRoleById(adminRole));

        if (!(isStaff || isAdminRole)) {
            EmbedBuilder embed = new EmbedBuilder();

            embed.setColor(Color.HSBtoRGB(0, 100, 57));
            embed.setDescription("Sorry " + sender.getAsMention() + ", you don't have permission to use this command!");
            embed.setFooter("https://autobuy.io/@PurseAlts");
            embed.setTimestamp(msg.getTimeCreated());

            textChannel.sendMessage(embed.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
            return;
        }

        if (args == null) {
            EmbedBuilder embed = new EmbedBuilder();

            embed.setColor(Color.GREEN);
            embed.setDescription("Hi guys, this channel will be clear in **5 seconds** by " + sender.getAsMention());
            embed.setFooter("https://autobuy.io/@PurseAlts");
            embed.setTimestamp(msg.getTimeCreated());
            textChannel.sendMessage(embed.build()).completeAfter(1, TimeUnit.SECONDS).delete().queueAfter(1, TimeUnit.SECONDS);

            embed.setDescription("Hi guys, this channel will be clear in **4 seconds** by " + sender.getAsMention());
            textChannel.sendMessage(embed.build()).completeAfter(1, TimeUnit.SECONDS).delete().queueAfter(1, TimeUnit.SECONDS);

            embed.setDescription("Hi guys, this channel will be clear in **3 seconds** by " + sender.getAsMention());
            textChannel.sendMessage(embed.build()).completeAfter(1, TimeUnit.SECONDS).delete().queueAfter(1, TimeUnit.SECONDS);

            embed.setDescription("Hi guys, this channel will be clear in **2 seconds** by " + sender.getAsMention());
            textChannel.sendMessage(embed.build()).completeAfter(1, TimeUnit.SECONDS).delete().queueAfter(1, TimeUnit.SECONDS);

            embed.setDescription("Hi guys, this channel will be clear in **1 seconds** by " + sender.getAsMention());
            textChannel.sendMessage(embed.build()).completeAfter(1, TimeUnit.SECONDS).delete().queueAfter(1, TimeUnit.SECONDS);

            try {
                List<Message> messages = textChannel.getHistory().retrievePast(100).complete();
                textChannel.deleteMessages(messages).queue();
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } finally {
                embed.setDescription("Yay!, this channel had been purged by" + sender.getAsMention());
                textChannel.sendMessage(embed.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }

            return;
        }

        if (args.length < 1) {
            EmbedBuilder embed = new EmbedBuilder();

            embed.setColor(Color.HSBtoRGB(0, 100, 57));
            embed.setTitle("USAGE: " + prefix + "CLEAR [CHANNEL]");
            embed.setDescription("\n[] - optional");
            embed.setFooter("https://autobuy.io/@PurseAlts");
            embed.setTimestamp(msg.getTimeCreated());

            textChannel.sendMessage(embed.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
            return;
        }

        String channelID = args[0].substring(args[0].indexOf("#") + 1).replace(">", "");

        boolean isChannelID = false;

        for (TextChannel u : textChannel.getJDA().getTextChannels()) {
            if (u.getId().equalsIgnoreCase(channelID)) {
                isChannelID = true;
                break;
            }
        }

        if (!isChannelID) {
            EmbedBuilder embed = new EmbedBuilder();

            embed.setColor(Color.HSBtoRGB(0, 100, 57));
            embed.setDescription("channel is not found, try with target the channel!");
            embed.setFooter("https://autobuy.io/@PurseAlts");
            embed.setTimestamp(msg.getTimeCreated());

            textChannel.sendMessage(embed.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
            return;
        }

        EmbedBuilder embed = new EmbedBuilder();

        TextChannel selected = textChannel.getJDA().getTextChannelById(channelID);

        embed.setColor(Color.GREEN);
        embed.setDescription("Hi guys, this channel will be clear in **5 seconds** by " + sender.getAsMention());
        embed.setFooter("https://autobuy.io/@PurseAlts");
        embed.setTimestamp(msg.getTimeCreated());
        assert selected != null;

        selected.sendMessage(embed.build()).completeAfter(1, TimeUnit.SECONDS).delete().queueAfter(1, TimeUnit.SECONDS);

        embed.setDescription("Hi guys, this channel will be clear in **4 seconds** by " + sender.getAsMention());
        selected.sendMessage(embed.build()).completeAfter(1, TimeUnit.SECONDS).delete().queueAfter(1, TimeUnit.SECONDS);

        embed.setDescription("Hi guys, this channel will be clear in **3 seconds** by " + sender.getAsMention());
        selected.sendMessage(embed.build()).completeAfter(1, TimeUnit.SECONDS).delete().queueAfter(1, TimeUnit.SECONDS);

        embed.setDescription("Hi guys, this channel will be clear in **2 seconds** by " + sender.getAsMention());
        selected.sendMessage(embed.build()).completeAfter(1, TimeUnit.SECONDS).delete().queueAfter(1, TimeUnit.SECONDS);

        embed.setDescription("Hi guys, this channel will be clear in **1 seconds** by " + sender.getAsMention());
        selected.sendMessage(embed.build()).completeAfter(1, TimeUnit.SECONDS).delete().queueAfter(1, TimeUnit.SECONDS);

        try {
            List<Message> messages = selected.getHistory().retrievePast(100).complete();
            selected.deleteMessages(messages).queue();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } finally {
            embed.setDescription("Yay!, this channel had been purged by" + sender.getAsMention());
            selected.sendMessage(embed.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }
    }
}