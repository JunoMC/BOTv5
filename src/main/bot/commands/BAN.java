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

public class BAN {
    private final String cmd;
    private final String[] args;
    private final Message msg;

    public BAN (String cmd, @Nullable String[] args, Message msg) {
        this.cmd = cmd;
        this.args = args;
        this.msg = msg;
    }

    public void execute(String prefix, User sender, TextChannel textChannel, List<String> staffs, String adminRole) {
        if (!cmd.equalsIgnoreCase("BAN")) return;

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

            embed.setColor(Color.HSBtoRGB(0, 100, 57));
            embed.setTitle("USAGE: " + prefix + "BAN <USER> [REASON]");
            embed.setDescription("\n<> - required\n[] - optional");
            embed.setFooter("https://autobuy.io/@PurseAlts");
            embed.setTimestamp(msg.getTimeCreated());

            textChannel.sendMessage(embed.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
            return;
        }

        String userID = args[0].substring(args[0].indexOf("!") + 1).replace(">", "");

        boolean isUser = false;

        for (User u : textChannel.getJDA().getUsers()) {
            if (u.getId().equalsIgnoreCase(userID)) {
                isUser = true;
                break;
            }
        }

        if (!isUser) {
            EmbedBuilder embed = new EmbedBuilder();

            embed.setColor(Color.HSBtoRGB(0, 100, 57));
            embed.setDescription("user is not found, try with mention user!");
            embed.setFooter("https://autobuy.io/@PurseAlts");
            embed.setTimestamp(msg.getTimeCreated());

            textChannel.sendMessage(embed.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
            return;
        }

        User banUser = textChannel.getJDA().getUserById(userID);

        if (banUser == sender) {
            EmbedBuilder embed = new EmbedBuilder();

            embed.setColor(Color.HSBtoRGB(0, 100, 57));
            embed.setDescription("You can not ban your self!");
            embed.setFooter("https://autobuy.io/@PurseAlts");
            embed.setTimestamp(msg.getTimeCreated());

            textChannel.sendMessage(embed.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
            return;
        }

        StringBuilder reason = new StringBuilder();

        if (args.length >= 2) {
            for (int i = 1; i < args.length; i++) {
                if (i == args.length - 1) {
                    reason.append(args[i]);
                } else {
                    reason.append(args[i]).append(" ");
                }
            }
        } else reason = null;

        assert banUser != null;
        textChannel.getGuild().ban(Objects.requireNonNull(textChannel.getGuild().getMember(banUser)), 7, reason == null ? "" : reason.toString()).queue();

        EmbedBuilder embed = new EmbedBuilder();

        embed.setColor(Color.GREEN);
        embed.setThumbnail(banUser.getAvatarUrl());
        embed.setFooter("https://autobuy.io/@PurseAlts");
        embed.setTimestamp(msg.getTimeCreated());
        embed.addField("BANNED:", banUser.getAsTag(), true);

        if (reason != null) {
            embed.addField("REASON:", reason.toString(), true);
        } else {
            embed.addField("BY:", sender.getAsTag(), true);
        }

        embed.addField("DAY:", "∞", true);

        textChannel.sendMessage(embed.build()).queue();
    }
}