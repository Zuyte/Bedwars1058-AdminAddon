package me.zuyte.admin.commands;

import me.zuyte.admin.Admin;
import me.zuyte.admin.commands.subcommands.*;
import me.zuyte.admin.utils.TextUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            new HelpSubCommand(sender);
        } else {
            if (args[0].equalsIgnoreCase("help")) {
                new HelpSubCommand(sender);
                return true;
            } else if (args[0].equalsIgnoreCase("forcejoin")) {
                new ForceJoinSubCommand(sender, args);
                return true;
            } else if (args[0].equalsIgnoreCase("setteam")) {
                new SetTeamSubCommand(sender, args);
                return true;
            } else if (args[0].equalsIgnoreCase("setbed")) {
                new SetBedSubCommand(sender, args);
                return true;
            } else if (args[0].equalsIgnoreCase("skipevent")) {
                new SkipEventSubCommand(sender, args);
                return true;
            } else if (args[0].equalsIgnoreCase("nextevent")) {
                new NextEventSubCommand(sender, args);
                return true;
            } else if (args[0].equalsIgnoreCase("troll")) {
                new TrollSubCommand(sender, args);
                return true;
            }
            TextUtils.sendDefaultConfigString("admin-message.team.not-found", sender);
        }
        return true;
    }
}
