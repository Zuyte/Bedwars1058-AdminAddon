package me.zuyte.admin.subcommands.bw1058;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.NextEvent;
import me.zuyte.admin.Admin;
import me.zuyte.admin.utils.TextUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class NextEventSubCommand {
    BedWars.ArenaUtil arenaUtil = Admin.getInstance().bw1058.getArenaUtil();

    public NextEventSubCommand(CommandSender commandSender, String[] args) {
        if (commandSender instanceof Player)
            player((Player) commandSender, args);
        else if (commandSender instanceof ConsoleCommandSender)
            console((ConsoleCommandSender) commandSender, args);
    }

    private void player(Player p, String[] args) {
        if (!p.hasPermission("bw.admin.nextevent")) {
            TextUtils.sendPlayerConfigStringBW1058("defaults.no-permission", p);
            return;
        }
        if (args.length > 1) {
            if (arenaUtil.getArenaByName(args[1]) == null) {
                TextUtils.sendPlayerConfigStringBW1058("defaults.arena-not-found", p);
                return;
            }
            IArena arena = arenaUtil.getArenaByName(args[1]);
            if (args.length >= 3) {
                boolean found = false;
                if (args[2].equalsIgnoreCase("diamond-2")) {
                    arena.setNextEvent(NextEvent.DIAMOND_GENERATOR_TIER_II);
                    found = true;
                } else if (args[2].equalsIgnoreCase("diamond-3")) {
                    arena.setNextEvent(NextEvent.DIAMOND_GENERATOR_TIER_III);
                    found = true;
                } else if (args[2].equalsIgnoreCase("emerald-2")) {
                    arena.setNextEvent(NextEvent.EMERALD_GENERATOR_TIER_II);
                    found = true;
                } else if (args[2].equalsIgnoreCase("emerald-3")) {
                    arena.setNextEvent(NextEvent.EMERALD_GENERATOR_TIER_III);
                    found = true;
                } else if (args[2].equalsIgnoreCase("bed-destroy")) {
                    arena.setNextEvent(NextEvent.BEDS_DESTROY);
                    found = true;
                } else if (args[2].equalsIgnoreCase("dragon")) {
                    arena.setNextEvent(NextEvent.ENDER_DRAGON);
                    found = true;
                } else if (args[2].equalsIgnoreCase("end")) {
                    arena.setNextEvent(NextEvent.GAME_END);
                    found = true;
                }
                if (found)
                    TextUtils.sendPlayerConfigStringBW1058("defaults.success", p);
                else
                    TextUtils.sendPlayerConfigStringBW1058("admin-message.nextevent.error", p);
                return;
            }
        }
        TextUtils.sendPlayerConfigStringBW1058("usage.nextevent", p);
    }

    private void console(ConsoleCommandSender c, String[] args) {
        if (args.length > 1) {
            if (arenaUtil.getArenaByName(args[1]) == null) {
                TextUtils.sendDefaultConfigStringBW1058("defaults.arena-not-found", c);
                return;
            }
            IArena arena = arenaUtil.getArenaByName(args[1]);
            if (args.length >= 3) {
                boolean found = false;
                if (args[2].equalsIgnoreCase("diamond-2")) {
                    arena.setNextEvent(NextEvent.DIAMOND_GENERATOR_TIER_II);
                    found = true;
                } else if (args[2].equalsIgnoreCase("diamond-3")) {
                    arena.setNextEvent(NextEvent.DIAMOND_GENERATOR_TIER_III);
                    found = true;
                } else if (args[2].equalsIgnoreCase("emerald-2")) {
                    arena.setNextEvent(NextEvent.EMERALD_GENERATOR_TIER_II);
                    found = true;
                } else if (args[2].equalsIgnoreCase("emerald-3")) {
                    arena.setNextEvent(NextEvent.EMERALD_GENERATOR_TIER_III);
                    found = true;
                } else if (args[2].equalsIgnoreCase("bed-destroy")) {
                    arena.setNextEvent(NextEvent.BEDS_DESTROY);
                    found = true;
                } else if (args[2].equalsIgnoreCase("dragon")) {
                    arena.setNextEvent(NextEvent.ENDER_DRAGON);
                    found = true;
                } else if (args[2].equalsIgnoreCase("end")) {
                    arena.setNextEvent(NextEvent.GAME_END);
                    found = true;
                }
                if (found)
                    TextUtils.sendDefaultConfigStringBW1058("defaults.success", c);
                else
                    TextUtils.sendDefaultConfigStringBW1058("admin-message.nextevent.error", c);
                return;
            }
        }
        TextUtils.sendDefaultConfigStringBW1058("usage.nextevent", c);
    }

}
