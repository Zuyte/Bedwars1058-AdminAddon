package me.zuyte.admin.commands.proxy;

import com.andrei1058.bedwars.proxy.api.CachedArena;
import com.andrei1058.bedwars.proxy.arenamanager.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
                for (CachedArena cachedArena : ArenaManager.getArenas()) {
                    if (cachedArena.getArenaName().equals(arenaWorld)) {
                        cachedArena.addPlayer(player, "true");
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aForce joined " + player.getName() + " to " + arenaWorld));
                        return;
                    }
                }
                if (ArenaManager.getInstance().joinRandomFromGroup(player, args[2])) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aForce joined " + player.getName() + " to " + arenaWorld));
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cArena/Group not found"));
                }
                return;
            }
        }
        p.sendMessage(ChatColor.RED + "Usage: /bwa forcejoin <player> <arena>");
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
                for (CachedArena cachedArena : ArenaManager.getArenas()) {
                    if (cachedArena.getArenaName().equals(arenaWorld)) {
                        cachedArena.addPlayer(player, "true");
                        c.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aForce joined " + player.getName() + " to " + arenaWorld));
                        return;
                    }
                }
                if (ArenaManager.getInstance().joinRandomFromGroup(player, args[2])) {
                    c.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aForce joined " + player.getName() + " to " + arenaWorld));
                } else {
                    c.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cArena/Group not found"));
                }
                return;
            }
        }
        c.sendMessage(ChatColor.RED + "Usage: /bwa forcejoin <player> <arena>");
    }
}
