package me.zuyte.admin.command.bwproxy;

import me.zuyte.admin.subcommand.bwproxy.ForceJoinProxySubCommand;
import me.zuyte.admin.subcommand.bwproxy.HelpProxySubCommand;
import me.zuyte.admin.util.TextUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminProxyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            new HelpProxySubCommand(sender);
            return true;
        } else if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("help")) {
                new HelpProxySubCommand(sender);
                return true;
            } else if (args[0].equalsIgnoreCase("forcejoin")) {
                new ForceJoinProxySubCommand(sender, args);
                return true;
            }
            TextUtils.sendDefaultConfigStringBWProxy("defaults.unknown-command", sender);
            return true;
        }
        return true;
    }
}
