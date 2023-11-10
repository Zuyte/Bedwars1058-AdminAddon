package me.zuyte.admin.commands.bw1058;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import me.zuyte.admin.Admin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AdminTabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("bwa")) {
            if (args.length == 1) {
                return Arrays.asList("help", "forcejoin", "setteam", "setbed", "skipevent", "nextevent", "troll", "revive");
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("forcejoin") || args[0].equalsIgnoreCase("setteam") || args[0].equalsIgnoreCase("setbed") || args[0].equalsIgnoreCase("revive")) {
                    List<String> playerNames = new ArrayList<>();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        playerNames.add(player.getName());
                    }
                    return playerNames;
                } else if (args[0].equalsIgnoreCase("nextevent") || args[0].equalsIgnoreCase("skipevent")) {
                    List<String> arenaNames = new ArrayList<>();
                    for (IArena arena : Admin.getInstance().bw1058.getArenaUtil().getArenas()) {
                        arenaNames.add(arena.getArenaName());
                    }
                    return arenaNames;
                } else if (args[0].equalsIgnoreCase("troll")) {
                    return Arrays.asList("mlg", "cage", "blind", "slowhands", "kaboom", "toystick", "ghast", "mobattack");
                }
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("forcejoin")) {
                    List<String> arenaNames = new ArrayList<>();
                    for (IArena arena : Admin.getInstance().bw1058.getArenaUtil().getArenas()) {
                        arenaNames.add(arena.getArenaName());
                    }
                    return arenaNames;
                }
            if (args[0].equalsIgnoreCase("revive")) {
                return Arrays.asList("final", "bed");
            }
                if (args[0].equalsIgnoreCase("setbed")) {
                    return Arrays.asList("true", "false");
                }
                if (args[0].equalsIgnoreCase("nextevent")) {
                    return Arrays.asList("diamond-2", "diamond-3", "emerald-2", "emerald-3", "bed-destroy", "dragon", "end");
                }
                if (args[0].equalsIgnoreCase("setteam")) {
                    BedWars.ArenaUtil arenaUtil = Admin.getInstance().bw1058.getArenaUtil();
                    if (Bukkit.getPlayerExact(args[1]) != null && arenaUtil.getArenaByPlayer(Bukkit.getPlayerExact(args[1])) != null) {
                        Player player = Bukkit.getPlayerExact(args[1]);
                        List<String> teamNames = new ArrayList<>();
                        for (ITeam team : arenaUtil.getArenaByPlayer(player).getTeams()) {
                            teamNames.add(team.getName());
                        }
                        return teamNames;
                    }
                }
            }
            if (args[0].equalsIgnoreCase("troll")) {
                List<String> playerNames = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    playerNames.add(player.getName());
                }
                return playerNames;
            }
        }
        return Collections.emptyList();
    }
}
