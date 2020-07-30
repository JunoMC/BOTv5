package main.bot.events;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class REACTION extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        TextChannel cur_channel = event.getChannel();
        if (event.getUser().isBot()) return;
        String id = "";
        try {
            String canonicalPath = new File(".").getCanonicalPath();
            File ticketFile = new File(canonicalPath, "config/ticket-channel.yml");
            Scanner scanner = new Scanner(ticketFile);
            id = scanner.next();
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(id);

        if (cur_channel.getId().equalsIgnoreCase(id)) {
            event.getReaction().removeReaction(event.getUser()).queue();

            TextChannel ticket = cur_channel.getParent().createTextChannel("ticket-" + event.getUser().getAsTag()).complete();
        }
    }
}