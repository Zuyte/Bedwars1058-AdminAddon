package me.zuyte.admin.utils;

import org.bukkit.ChatColor;

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
}
