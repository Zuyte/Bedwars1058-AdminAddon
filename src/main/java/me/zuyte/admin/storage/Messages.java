package me.zuyte.admin.storage;

import com.andrei1058.bedwars.api.language.Language;
import me.zuyte.admin.Admin;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Messages {
    private Admin instance = Admin.getInstance();
    private static final String ADDON_PATH = ".addons.admin.";

    public static void setupBedWarsMessages(YamlConfiguration yaml) {
        // Default Messages
        yaml.addDefault(ADDON_PATH + "defaults.unknown-command", "&cUnknown Command");
        yaml.addDefault(ADDON_PATH + "defaults.no-permission", "&cYou don't have permission");
        yaml.addDefault(ADDON_PATH + "defaults.player-not-found", "&cPlayer not found");
        yaml.addDefault(ADDON_PATH + "defaults.player-not-joined-arena", "&cPlayer hasn't joined an arena");
        yaml.addDefault(ADDON_PATH + "defaults.player-not-playing", "&cPlayer is not playing");
        yaml.addDefault(ADDON_PATH + "defaults.arena-group-not-found", "&cArena/Group not found");
        yaml.addDefault(ADDON_PATH + "defaults.arena-not-found", "&cCan't find the arena");
        yaml.addDefault(ADDON_PATH + "defaults.enter-number", "&cPlease enter a number");
        yaml.addDefault(ADDON_PATH + "defaults.success", "&aSuccess");
        yaml.addDefault(ADDON_PATH + "defaults.error", "&cAn error occurred, please contact the addon developer");

        // Player Messages
        yaml.addDefault(ADDON_PATH + "player-message.force-join", "&aYou were force joined to {world}");
        yaml.addDefault(ADDON_PATH + "player-message.mlg.start", "&bMLG!!!");
        yaml.addDefault(ADDON_PATH + "player-message.mlg.success", "&aSuccess!");
        yaml.addDefault(ADDON_PATH + "player-message.mlg.fail", "&cFailed!");

        // Admin Messages
        yaml.addDefault(ADDON_PATH + "admin-message.force-join", "&aForce joined {player} to {world}");
        yaml.addDefault(ADDON_PATH + "admin-message.team.success", "&aSuccessfully set {player}'s team to {team}");
        yaml.addDefault(ADDON_PATH + "admin-message.team.not-found", "&cCan't find team");
        yaml.addDefault(ADDON_PATH + "admin-message.team.full", "&cTeam is full");
        yaml.addDefault(ADDON_PATH + "admin-message.team.error-after-start", "&cCan't change player's team after the game has been started");
        yaml.addDefault(ADDON_PATH + "admin-message.nextevent.error", "&cEvent does not exist");
        yaml.addDefault(ADDON_PATH + "admin-message.skipevent.error", "&cYou are already at the last event");


        yaml.addDefault(ADDON_PATH + "admin-message.troll-cage", "&dTrapped {player}");
        yaml.addDefault(ADDON_PATH + "admin-message.troll-blind", "&e{player} is blind for {time} seconds");
        yaml.addDefault(ADDON_PATH + "admin-message.troll-slowhands", "&e{player} has mining fatigue for {time} seconds");
        yaml.addDefault(ADDON_PATH + "admin-message.troll-kaboom", "&eKaboom-ed {player}");
        yaml.addDefault(ADDON_PATH + "admin-message.troll-toystick", "&aGave {player} a toystick");
        yaml.addDefault(ADDON_PATH + "admin-message.troll-ghast", "&aSpawned ghast at {player}");
        yaml.addDefault(ADDON_PATH + "admin-message.troll-mobattack", "&aSpawned mobs at {player}");

        // Usage Messages
        yaml.addDefault(ADDON_PATH + "usage.forcejoin", "&cUsage: /bwa forcejoin <player> <arena/group>");
        yaml.addDefault(ADDON_PATH + "usage.setteam", "&cUsage: /bwa setteam <player> <team>");
        yaml.addDefault(ADDON_PATH + "usage.setbed", "&cUsage: /bwa setbed <player> <true/false>");
        yaml.addDefault(ADDON_PATH + "usage.nextevent", "&cUsage: /bwa nextevent <arena> <event>");
        yaml.addDefault(ADDON_PATH + "usage.skipevent", "&cUsage: /bwa skipevent <arena>");
        yaml.addDefault(ADDON_PATH + "usage.troll-mlg", "&cUsage: /bwa troll mlg <player>");
        yaml.addDefault(ADDON_PATH + "usage.troll-cage", "&cUsage: /bwa troll cage <player>");
        yaml.addDefault(ADDON_PATH + "usage.troll-blind", "&cUsage: /bwa troll blind <player> <seconds>");
        yaml.addDefault(ADDON_PATH + "usage.troll-slowhands", "&cUsage: /bwa troll slowhands <player> <seconds>");
        yaml.addDefault(ADDON_PATH + "usage.troll-kaboom", "&cUsage: /bwa troll kaboom <player>");
        yaml.addDefault(ADDON_PATH + "usage.troll-toystick", "&cUsage: /bwa troll toystick <player>");
        yaml.addDefault(ADDON_PATH + "usage.troll-ghast", "&cUsage: /bwa troll ghast <player>");
        yaml.addDefault(ADDON_PATH + "usage.troll-mobattack", "&cUsage: /bwa troll mobattack <player>");
    }

    public static void setupProxyMessages(com.andrei1058.bedwars.proxy.api.Language lang) {
        // Default Messages
        if (!lang.exists(ADDON_PATH + "defaults.unknown-command"))
            lang.set(ADDON_PATH + "defaults.unknown-command", "&cUnknown Command");
        if (!lang.exists(ADDON_PATH + "defaults.no-permission"))
            lang.set(ADDON_PATH + "defaults.no-permission", "&cYou don't have permission");
        if (!lang.exists(ADDON_PATH + "defaults.player-not-found"))
            lang.set(ADDON_PATH + "defaults.player-not-found", "&cPlayer not found");
        if (!lang.exists(ADDON_PATH + "defaults.arena-group-not-found"))
            lang.set(ADDON_PATH + "defaults.arena-group-not-found", "&cArena/Group not found");

        // Usage Messages
        if (!lang.exists(ADDON_PATH + "usage.forcejoin")) {
            lang.set(ADDON_PATH + "usage.forcejoin", "&cUsage: /bwa forcejoin <player> <arena>");
        }

        // Player Messages
        lang.set(ADDON_PATH + "player-message.force-join", "&aYou were force joined to {world}");
    }

    public static String getPlayerBedWars1058Message(String path, Player player) {
        Language lang = Admin.getInstance().bw1058.getPlayerLanguage(player);
        if (lang.exists(".addons.admin." + path))
            return lang.getString(".addons.admin." + path);
        else
            return "";
    }

    public static String getDefaultBedWars1058Message(String path) {
        Language lang = Admin.getInstance().bw1058.getDefaultLang();
        if (lang.exists(".addons.admin." + path))
            return lang.getString(".addons.admin." + path);
        else
            return "";
    }

    public static String getPlayerBedWars2023Message(String path, Player player) {
        com.tomkeuper.bedwars.api.language.Language lang = Admin.getInstance().bw2023.getPlayerLanguage(player);
        if (lang.exists(".addons.admin." + path))
            return lang.getString(".addons.admin." + path);
        else
            return "";
    }

    public static String getDefaultBedWars2023Message(String path) {
        com.tomkeuper.bedwars.api.language.Language lang = Admin.getInstance().bw2023.getDefaultLang();
        if (lang.exists(".addons.admin." + path))
            return lang.getString(".addons.admin." + path);
        else
            return "";
    }

    public static String getPlayerProxyMessage(String path, Player player) {
        com.andrei1058.bedwars.proxy.api.Language lang = Admin.getInstance().bwProxy.getLanguageUtil().getPlayerLanguage(player);
        if (lang.exists(".addons.admin." + path))
            return lang.getMsg(".addons.admin." + path);
        else
            return "";
    }
}
