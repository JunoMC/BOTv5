package main.bot;

import main.bot.commands.MODULE;
import main.bot.events.REACTION;
import main.java.com.amihaiemil.eoyaml.Yaml;
import main.java.com.amihaiemil.eoyaml.YamlMapping;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.Arrays;
import java.util.Date;

public class BOT {

    private static String[] yaml = new String[] {
            "#change xxxxxxxx to token of your bot to active bot",
            "token: xxxxxxxx",
            "",
            "#set status for your bot (ONLINE/INVISIBLE/IDLE/DO_NOT_DISTURB)",
            "status: DO_NOT_DISTURB",
            "",
            "#set the activity which bot doing when run (just show the active)",
            "activity:",
            "  #enable/disable bot's activity",
            "  enable: true",
            "",
            "  #type: PLAYING/WATCHING/STREAMING",
            "  type: PLAYING",
            "  name: IntelliJ",
    };

    public static YamlMapping config;
    public static File logFile;

    public static void WriteFile(String path, String... strings) {
        Arrays.asList(strings).forEach(str -> {
            FileWriter fr;
            try {
                fr = new FileWriter(path, true);
                BufferedWriter br = new BufferedWriter(fr);

                br.write(str);
                br.newLine();

                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) throws LoginException {
        Date date = new Date();
        String strDate = "[" + date.getHours() + "." + date.getMinutes() + "." + date.getSeconds() + "] " + date.getDate() + "-" + (date.getMonth() + 1) + "-" + (date.getYear() + 1900);

        try {
            String canonicalPath = new File(".").getCanonicalPath();
            File configFile = new File(canonicalPath, "config/config.yml");

            logFile = new File(canonicalPath, "logs/" + strDate + ".yml");

            if (!logFile.exists()) {
                logFile.getParentFile().mkdirs();
                try {
                    logFile.createNewFile();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (!configFile.exists()) {
                configFile.getParentFile().mkdirs();
                try {
                    configFile.createNewFile();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    WriteFile(configFile.getPath(), yaml);
                }
            }

            config = Yaml.createYamlInput(configFile).readYamlMapping();

            String token = config.string("token");
            String status = config.string("status");

            boolean activeEnable = Boolean.parseBoolean(config.yamlMapping("activity").string("enable"));
            String activeType = config.yamlMapping("activity").string("type");
            String activeName = config.yamlMapping("activity").string("name");

            JDABuilder bot = new JDABuilder(AccountType.BOT);

            bot.setToken(token);
            bot.setStatus(OnlineStatus.valueOf(status));

            if (activeEnable) {
                Activity activity = null;

                switch (activeType) {
                    case "PLAYING":
                        activity = Activity.playing(activeName);
                        break;
                    case "WATCHING":
                        activity = Activity.watching(activeName);
                        break;
                    case "STREAMING":
                        activity = Activity.streaming(activeName, "");
                        break;
                    case "LISTENING":
                        activity = Activity.listening(activeName);
                        break;
                }

                bot.setActivity(activity);
            }

            bot.addEventListeners(new MODULE(), new REACTION());

            bot.build();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}