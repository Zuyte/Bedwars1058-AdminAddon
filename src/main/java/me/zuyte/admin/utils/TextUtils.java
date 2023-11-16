package me.zuyte.admin.utils;

import me.zuyte.admin.storage.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TextUtils {
    public static String getColoredString(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
    public static List<String> getColoredStringList(List<String> msg) {
        return msg.stream().map(TextUtils::getColoredString).collect(Collectors.toList());
    }

    // BedWars1058
    public static String getPlayerConfigStringBW1058(String path, Player player) {
        return getColoredString(Messages.getPlayerBedWars1058Message(path, player));
    }

    public static String getDefaultConfigStringBW1058(String path) {
        return getColoredString(Messages.getDefaultBedWars1058Message(path));
    }

    public static void sendPlayerConfigStringBW1058(String path, Player player) {
        String msg = getPlayerConfigStringBW1058(path, player);
        if (!msg.isEmpty())
            player.sendMessage(msg);
    }

    public static void sendDefaultConfigStringBW1058(String path, CommandSender sender) {
        sender.sendMessage(getDefaultConfigStringBW1058(path));
    }

    // BedWars2023
    public static String getPlayerConfigStringBW2023(String path, Player player) {
        return getColoredString(Messages.getPlayerBedWars2023Message(path, player));
    }
    public static String getDefaultConfigStringBW2023(String path) {
        return getColoredString(Messages.getDefaultBedWars2023Message(path));
    }

    public static void sendPlayerConfigStringBW2023(String path, Player player) {
        String msg = getPlayerConfigStringBW2023(path, player);
        if (!msg.isEmpty())
            player.sendMessage(msg);
    }
    public static void sendDefaultConfigStringBW2023(String path, CommandSender sender) {
        sender.sendMessage(getDefaultConfigStringBW2023(path));
    }

    // BedWarsProxy
    public static String getPlayerConfigStringBWProxy(String path, Player player) {
        return getColoredString(Messages.getPlayerProxyMessage(path, player));
    }
    public static String getDefaultConfigStringBWProxy(String path) {
        return getColoredString(Messages.getDefaultProxyMessage(path));
    }

    public static void sendPlayerConfigStringBWProxy(String path, Player player) {
        String msg = getPlayerConfigStringBWProxy(path, player);
        if (!msg.isEmpty())
            player.sendMessage(msg);
    }
    public static void sendDefaultConfigStringBWProxy(String path, CommandSender sender) {
        sender.sendMessage(getDefaultConfigStringBWProxy(path));
    }

    public static String getPlayerConfigStringBWProxy2023(String path, Player player) {
        return getColoredString(Messages.getPlayerProxy2023Message(path, player));
    }
    public static String getDefaultConfigStringBWProxy2023(String path) {
        return getColoredString(Messages.getDefaultProxy2023Message(path));
    }

    public static void sendPlayerConfigStringBWProxy2023(String path, Player player) {
        String msg = getPlayerConfigStringBWProxy2023(path, player);
        if (!msg.isEmpty())
            player.sendMessage(msg);
    }
    public static void sendDefaultConfigStringBWProxy2023(String path, CommandSender sender) {
        sender.sendMessage(getDefaultConfigStringBWProxy2023(path));
    }
}
