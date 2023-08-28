package me.zuyte.admin.commands.proxy;

import me.zuyte.admin.Admin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class AdminProxyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
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
            p.sendMessage(ChatColor.RED + "Command not found.");
            return true;
        }
        return true;
    }
}
