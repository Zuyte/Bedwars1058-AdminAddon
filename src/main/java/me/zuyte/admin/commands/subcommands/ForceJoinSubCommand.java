package me.zuyte.admin.commands.subcommands;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import me.zuyte.admin.Admin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import me.zuyte.admin.utils.TextUtils;

import java.util.List;

public class ForceJoinSubCommand {
    BedWars.ArenaUtil arenaUtil = Admin.getInstance().bw.getArenaUtil();

    public ForceJoinSubCommand(CommandSender commandSender, String[] args) {
        if (commandSender instanceof Player)
            player((Player) commandSender, args);
        else if (commandSender instanceof ConsoleCommandSender)
            console((ConsoleCommandSender) commandSender, args);
    }

    private void player(Player p, String[] args) {
        if (!p.hasPermission("bw.admin.forcejoin")) {
            p.sendMessage(ChatColor.RED + "You dont have permission to use this command.");
            return;
        }
        if (args.length > 1) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                p.sendMessage(ChatColor.RED + "Player not found");
                return;
            }
            if (args.length >= 3) {
                String arenaWorld = args[2];
                List<String> arenaGroups = Admin.getInstance().bw.getConfigs().getMainConfig().getYml().getStringList(".arenaGroups");
                boolean isAGroup = false;
                for (String group : arenaGroups) {
                    if (group.equals(arenaWorld)) {
                        isAGroup = true;
                        break;
                    }
                }
                if (!isAGroup && arenaUtil.getArenaByName(arenaWorld) == null) {
                    p.sendMessage(ChatColor.RED + "Arena/Group not found");
                    return;
                }
                if (!isAGroup)
                    arenaUtil.getArenaByName(arenaWorld).addPlayer(player, true);
                else
                    arenaUtil.joinRandomFromGroup(player, arenaWorld);

                p.sendMessage(TextUtils.getColoredString("&aForce joined " + player.getName() + " to " + (isAGroup ? "group " : "arena ") + arenaWorld));
                return;
            }
        }
        p.sendMessage(ChatColor.RED + "Usage: /bw forcejoin <player> <arena>");
    }

    private void console(ConsoleCommandSender c, String[] args) {
        if (args.length > 1) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                c.sendMessage(ChatColor.RED + "Player not found");
                return;
            }
            if (args.length >= 3) {
                String arenaWorld = args[2];
                List<String> arenaGroups = Admin.getInstance().bw.getConfigs().getMainConfig().getYml().getStringList(".arenaGroups");
                boolean isAGroup = false;
                for (String group : arenaGroups) {
                    if (group.equals(args[2])) {
                        isAGroup = true;
                        break;
                    }
                }
                if (!isAGroup && arenaUtil.getArenaByName(arenaWorld) == null) {
                    c.sendMessage(ChatColor.RED + "Arena/Group not found");
                    return;
                }
                if (!isAGroup)
                    arenaUtil.getArenaByName(arenaWorld).addPlayer(player, true);
                else
                    arenaUtil.joinRandomFromGroup(player, args[2]);

                c.sendMessage(TextUtils.getColoredString("&aForce joined " + player.getName() + " to " + (isAGroup ? "group " : "arena ") + arenaWorld));
                return;
            }
        }
        c.sendMessage(ChatColor.RED + "Usage: /bw forcejoin <player> <arena>");
    }
}