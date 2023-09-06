package me.zuyte.admin.utils;

import me.zuyte.admin.storage.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TextUtils {
    public static String getColoredString(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
    public static List<String> getColoredStringList(List<String> msg) {
        if (msg.size() == 1 && msg.get(0).equals("")) {
            return msg;
        }
        List<String> ret = new ArrayList<String>();
        try {
            for (String line : msg) {
                ret.add(ChatColor.translateAlternateColorCodes('&', line));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return ret;
    }

    public static String getPlayerConfigString(String path, Player player) {
        return getColoredString(Messages.getPlayerBedWarsMessage(path, player));
    }

    public static void sendPlayerConfigString(String path, Player player) {
        String msg = getPlayerConfigString(path, player);
        if (!msg.isEmpty())
            player.sendMessage(msg);
    }

    public static String getDefaultConfigString(String path) {
        return getColoredString(Messages.getDefaultBedWarsMessage(path));
    }

    public static void sendDefaultConfigString(String path, CommandSender sender) {
        sender.sendMessage(getDefaultConfigString(path));
    }
}
