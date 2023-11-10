package me.zuyte.admin.commands.bwproxy;

import me.zuyte.admin.subcommands.bwproxy.ForceJoinProxySubCommand;
import me.zuyte.admin.subcommands.bwproxy.HelpProxySubCommand;
import me.zuyte.admin.utils.TextUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
