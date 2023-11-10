package me.zuyte.admin.subcommand.bw1058;

import com.andrei1058.bedwars.api.BedWars;
import me.zuyte.admin.Admin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import me.zuyte.admin.util.TextUtils;

import java.util.List;

public class ForceJoinSubCommand {
    BedWars.ArenaUtil arenaUtil = Admin.getInstance().bw1058.getArenaUtil();

    public ForceJoinSubCommand(CommandSender commandSender, String[] args) {
        if (commandSender instanceof Player)
            player((Player) commandSender, args);
        else if (commandSender instanceof ConsoleCommandSender)
            console((ConsoleCommandSender) commandSender, args);
    }

    private void player(Player p, String[] args) {
        if (!p.hasPermission("bw.admin.forcejoin")) {
            TextUtils.sendPlayerConfigStringBW1058("defaults.no-permission", p);
            return;
        }
        if (args.length > 1) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                TextUtils.sendPlayerConfigStringBW1058("defaults.player-not-found", p);
                return;
            }
            if (args.length >= 3) {
                String arenaWorld = args[2];
                List<String> arenaGroups = Admin.getInstance().bw1058.getConfigs().getMainConfig().getYml().getStringList(".arenaGroups");
                boolean isAGroup = false;
                for (String group : arenaGroups) {
                    if (group.equals(arenaWorld)) {
                        isAGroup = true;
                        break;
                    }
                }
                if (!isAGroup && arenaUtil.getArenaByName(arenaWorld) == null) {
                    TextUtils.sendPlayerConfigStringBW1058("defaults.arena-group-not-found", p);
                    return;
                }
                if (!isAGroup)
                    arenaUtil.getArenaByName(arenaWorld).addPlayer(player, true);
                else
                    arenaUtil.joinRandomFromGroup(player, arenaWorld);

                p.sendMessage(TextUtils.getPlayerConfigStringBW1058("admin-message.force-join", p).replace("{player}", player.getName()).replace("{world}", arenaWorld));
                player.sendMessage(TextUtils.getPlayerConfigStringBW1058("player-message.force-join", player).replace("{world}", arenaWorld));
                return;
            }
        }
        TextUtils.sendPlayerConfigStringBW1058("usage.forcejoin", p);
    }

    private void console(ConsoleCommandSender c, String[] args) {
        if (args.length > 1) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                TextUtils.sendDefaultConfigStringBW1058("defaults.player-not-found", c);
                return;
            }
            if (args.length >= 3) {
                String arenaWorld = args[2];
                List<String> arenaGroups = Admin.getInstance().bw1058.getConfigs().getMainConfig().getYml().getStringList(".arenaGroups");
                boolean isAGroup = false;
                for (String group : arenaGroups) {
                    if (group.equals(arenaWorld)) {
                        isAGroup = true;
                        break;
                    }
                }
                if (!isAGroup && arenaUtil.getArenaByName(arenaWorld) == null) {
                    TextUtils.sendDefaultConfigStringBW1058("defaults.arena-group-not-found", c);
                    return;
                }
                if (!isAGroup)
                    arenaUtil.getArenaByName(arenaWorld).addPlayer(player, true);
                else
                    arenaUtil.joinRandomFromGroup(player, arenaWorld);

                c.sendMessage(TextUtils.getDefaultConfigStringBW1058("admin-message.force-join").replace("{player}", player.getName()).replace("{world}", arenaWorld));
                player.sendMessage(TextUtils.getPlayerConfigStringBW1058("player-message.force-join", player).replace("{world}", arenaWorld));
                return;
            }
        }
        TextUtils.sendDefaultConfigStringBW1058("usage.forcejoin", c);
    }
}