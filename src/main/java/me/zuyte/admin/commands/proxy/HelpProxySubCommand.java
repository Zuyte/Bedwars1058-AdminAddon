package me.zuyte.admin.commands.proxy;

import com.google.common.base.Joiner;
import me.zuyte.admin.Admin;
import me.zuyte.admin.utils.TextUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HelpProxySubCommand {
    public HelpProxySubCommand(CommandSender sender) {
        sendMessage(sender);
    }

    private void sendMessage(CommandSender c) {
        if (!c.hasPermission("bw.admin")) {
            c.sendMessage(ChatColor.RED + "You dont have permission to use this command.");
            return;
        }
        List<String> helpMessage = new ArrayList<>(Collections.emptyList());
        helpMessage.add(TextUtils.getColoredString("&r\n&8&lþ &6BedWars1058 Admin v" + Admin.getInstance().getDescription().getVersion() + " &7- &cCommands"));
        helpMessage.add(TextUtils.getColoredString("&r "));
        helpMessage.add(TextUtils.getColoredString("&6• &7/bwa forcejoin <player> <arena> &8- &eForcejoin player into an arena"));

        c.sendMessage(Joiner.on("\n").join(helpMessage));
    }
}
