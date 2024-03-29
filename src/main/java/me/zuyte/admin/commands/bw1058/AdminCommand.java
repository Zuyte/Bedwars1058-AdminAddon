package me.zuyte.admin.commands.bw1058;

import me.zuyte.admin.subcommands.bw1058.*;
import me.zuyte.admin.utils.TextUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
            } else if (args[0].equalsIgnoreCase("revive")) {
                new ReviveSubCommand(sender, args);
                return true;
            }
            TextUtils.sendDefaultConfigStringBW1058("defaults.unknown-command", sender);
        }
        return true;
    }
}
