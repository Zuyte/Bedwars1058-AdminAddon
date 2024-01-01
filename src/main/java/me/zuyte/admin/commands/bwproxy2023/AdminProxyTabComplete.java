package me.zuyte.admin.commands.bwproxy2023;

import com.tomkeuper.bedwars.proxy.api.CachedArena;
import com.tomkeuper.bedwars.proxy.arenamanager.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminProxyTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("bwa")) {
            if (args.length == 1) {
                return Arrays.asList("help", "forcejoin");
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("forcejoin")) {
                    List<String> playerNames = new ArrayList<>();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        playerNames.add(player.getName());
                    }
                    return playerNames;
                }
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("forcejoin")) {
                    List<String> arenaNames = new ArrayList<>();
                    for (CachedArena cachedArena : ArenaManager.getArenas()) {
                        arenaNames.add(cachedArena.getArenaName());
                    }
                    return arenaNames;
                }
            }
        }
        return null;
    }
}
