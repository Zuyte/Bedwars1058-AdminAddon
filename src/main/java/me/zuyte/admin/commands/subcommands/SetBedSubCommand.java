package me.zuyte.admin.commands.subcommands;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import me.zuyte.admin.Admin;
import me.zuyte.admin.commands.storage.Cache;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SetBedSubCommand {

    BedWars.ArenaUtil arenaUtil = Admin.getInstance().bw.getArenaUtil();

    public SetBedSubCommand(CommandSender commandSender, String[] args) {
        if (commandSender instanceof Player)
            player((Player) commandSender, args);
        else if (commandSender instanceof ConsoleCommandSender)
            console((ConsoleCommandSender) commandSender, args);
    }

    private void player(Player p, String[] args) {
        if (!p.hasPermission("bw.admin.setbed")) {
            p.sendMessage(ChatColor.RED + "You dont have permission to use this command.");
            return;
        }
        if (args.length > 1) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                p.sendMessage(ChatColor.RED + "Player not found");
                return;
            }
            if (arenaUtil.getArenaByPlayer(player) == null) {
                p.sendMessage(ChatColor.RED + "Player has not joined an arena");
                return;
            }
            IArena arena = arenaUtil.getArenaByPlayer(player);
            if (args.length >= 3) {
                if (args[2].equalsIgnoreCase("true")) {
                    ITeam playerTeam = arena.getTeam(player);
                    playerTeam.setBedDestroyed(false);
                    BlockFace targetFace = Cache.getArenaBedsCache(playerTeam);
                    placeBed(playerTeam.getBed(), targetFace.getOppositeFace());
                    p.sendMessage(ChatColor.GREEN + "Success!");
                    return;
                } else if (args[2].equalsIgnoreCase("false")) {
                    ITeam playerTeam = arena.getTeam(player);
                    playerTeam.setBedDestroyed(true);
                    p.sendMessage(ChatColor.GREEN + "Success!");
                    return;
                }
            }
        }
        p.sendMessage(ChatColor.RED + "Usage: /bw setbed <player> <true ┃ false>");
    }

    private void console(ConsoleCommandSender c, String[] args) {
        if (args.length > 1) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                c.sendMessage(ChatColor.RED + "Player not found");
                return;
            }
            if (arenaUtil.getArenaByPlayer(player) == null) {
                c.sendMessage(ChatColor.RED + "Player has not joined an arena");
                return;
            }
            IArena arena = arenaUtil.getArenaByPlayer(player);
            if (args.length >= 3) {
                if (args[2].equalsIgnoreCase("true")) {
                    ITeam playerTeam = arena.getTeam(player);
                    playerTeam.setBedDestroyed(false);
                    BlockFace targetFace = Cache.getArenaBedsCache(playerTeam);
                    placeBed(playerTeam.getBed(), targetFace.getOppositeFace());
                    c.sendMessage(ChatColor.GREEN + "Success!");
                    return;
                } else if (args[2].equalsIgnoreCase("false")) {
                    ITeam playerTeam = arena.getTeam(player);
                    playerTeam.setBedDestroyed(true);
                    c.sendMessage(ChatColor.GREEN + "Success!");
                    return;
                }
            }
        }
        c.sendMessage(ChatColor.RED + "Usage: /bw setbed <player> <true ┃ false>");
    }

    private void placeBed(Location loc, BlockFace face) {

        BlockState bedFoot = loc.getBlock().getState();
        BlockState bedHead = bedFoot.getBlock().getRelative(face.getOppositeFace()).getState();

        bedFoot.setType(Material.BED_BLOCK);
        bedHead.setType(Material.BED_BLOCK);

        bedFoot.setRawData((byte) face.ordinal());
        bedHead.setRawData((byte) (face.ordinal() + 8));

        bedFoot.update(true, false);
        bedHead.update(true, true);

    }

}
