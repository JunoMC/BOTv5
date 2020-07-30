package main.bot.commands;

import com.vdurmont.emoji.EmojiParser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TICKET {
    private final String cmd;
    private final String[] args;
    private final Message msg;

    public TICKET(String cmd, @Nullable String[] args, Message msg) {
        this.cmd = cmd;
        this.args = args;
        this.msg = msg;
    }

    public void execute(String prefix, User sender, TextChannel textChannel, List<String> staffs, String adminRole) {
        if (!cmd.equalsIgnoreCase("TICKET")) return;

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

        if (args == null || args.length != 1) {
            EmbedBuilder embed = new EmbedBuilder();

            embed.setColor(Color.HSBtoRGB(0, 100, 57));
            embed.setTitle("USAGE: " + prefix + "TICKET SETUP");
            embed.setFooter("https://autobuy.io/@PurseAlts");
            embed.setTimestamp(msg.getTimeCreated());

            textChannel.sendMessage(embed.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
            return;
        }

        if (!args[0].equalsIgnoreCase("SETUP")) {
            EmbedBuilder embed = new EmbedBuilder();

            embed.setColor(Color.HSBtoRGB(0, 100, 57));
            embed.setTitle("USAGE: " + prefix + "TICKET SETUP");
            embed.setFooter("https://autobuy.io/@PurseAlts");
            embed.setTimestamp(msg.getTimeCreated());

            textChannel.sendMessage(embed.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
            return;
        }


        TextChannel ticketChannel = textChannel.getGuild().createCategory("TICKET").complete().createTextChannel("new-ticket").complete();

        try {
            String canonicalPath = new File(".").getCanonicalPath();
            File ticketFile = new File(canonicalPath, "config/ticket-channel.yml");

            if (!ticketFile.exists()) {
                ticketFile.getParentFile().mkdirs();
                try {
                    ticketFile.createNewFile();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            Files.write(ticketFile.toPath(), ticketChannel.getId().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.GREEN);
        embed.setFooter("https://autobuy.io/@PurseAlts");
        embed.setTimestamp(msg.getTimeCreated());

        embed.setDescription("Hi there! If you have any issue, please react :ticket:!");
        ticketChannel.sendMessage(embed.build()).complete().addReaction(EmojiParser.parseToUnicode(":ticket:")).queue();

        embed.setDescription("Created ticket channel successful!");
        textChannel.sendMessage(embed.build()).complete().delete().queueAfter(3, TimeUnit.SECONDS);
    }
}