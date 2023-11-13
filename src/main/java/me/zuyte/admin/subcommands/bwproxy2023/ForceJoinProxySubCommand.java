package me.zuyte.admin.subcommands.bwproxy2023;

import com.tomkeuper.bedwars.proxy.api.CachedArena;
import com.tomkeuper.bedwars.proxy.arenamanager.ArenaManager;
import me.zuyte.admin.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ForceJoinProxySubCommand {
    public ForceJoinProxySubCommand(CommandSender commandSender, String[] args) {
        if (commandSender instanceof Player)
            player((Player) commandSender, args);
        else if (commandSender instanceof ConsoleCommandSender)
            console((ConsoleCommandSender) commandSender, args);
    }

    private void player(Player p, String[] args) {
        if (!p.hasPermission("bw.admin.forcejoin")) {
            TextUtils.sendPlayerConfigStringBWProxy2023("defaults.no-permission", p);
            return;
        }
        if (args.length > 1) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                TextUtils.sendPlayerConfigStringBWProxy2023("defaults.player-not-found", p);
                return;
            }
            if (args.length >= 3) {
                String arenaWorld = args[2];
                for (CachedArena cachedArena : ArenaManager.getArenas()) {
                    if (cachedArena.getArenaName().equals(arenaWorld)) {
                        cachedArena.addPlayer(player, "true");
                        p.sendMessage(TextUtils.getPlayerConfigStringBWProxy2023("player-message.force-join-success", p).replace("{player}", player.getName()).replace("{world}", arenaWorld));
                        player.sendMessage(TextUtils.getPlayerConfigStringBWProxy2023("player-message.force-join", player).replace("{world}", arenaWorld));
                        return;
                    }
                }
                if (ArenaManager.getInstance().joinRandomFromGroup(player, args[2])) {
                    p.sendMessage(TextUtils.getPlayerConfigStringBWProxy2023("player-message.force-join-success", p).replace("{player}", player.getName()).replace("{world}", arenaWorld));
                    player.sendMessage(TextUtils.getPlayerConfigStringBWProxy2023("player-message.force-join", player).replace("{world}", arenaWorld));
                } else {
                    TextUtils.sendPlayerConfigStringBWProxy2023("defaults.arena-group-not-found", p);
                }
                return;
            }
        }
        TextUtils.sendPlayerConfigStringBWProxy2023("usage.forcejoin", p);
    }

    private void console(ConsoleCommandSender c, String[] args) {
        if (args.length > 1) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                TextUtils.sendDefaultConfigStringBWProxy2023("defaults.player-not-found", c);
                return;
            }
            if (args.length >= 3) {
                String arenaWorld = args[2];
                for (CachedArena cachedArena : ArenaManager.getArenas()) {
                    if (cachedArena.getArenaName().equals(arenaWorld)) {
                        cachedArena.addPlayer(player, "true");
                        c.sendMessage(TextUtils.getDefaultConfigStringBWProxy2023("player-message.force-join-success").replace("{player}", player.getName()).replace("{world}", arenaWorld));
                        player.sendMessage(TextUtils.getPlayerConfigStringBWProxy2023("player-message.force-join", player).replace("{world}", arenaWorld));
                        return;
                    }
                }
                if (ArenaManager.getInstance().joinRandomFromGroup(player, args[2])) {
                    c.sendMessage(TextUtils.getDefaultConfigStringBWProxy2023("player-message.force-join-success").replace("{player}", player.getName()).replace("{world}", arenaWorld));
                    player.sendMessage(TextUtils.getPlayerConfigStringBWProxy2023("player-message.force-join", player).replace("{world}", arenaWorld));
                } else {
                    TextUtils.sendDefaultConfigStringBWProxy2023("defaults.arena-group-not-found", c);
                }
                return;
            }
        }
        TextUtils.sendDefaultConfigStringBWProxy2023("usage.forcejoin", c);
    }
}
