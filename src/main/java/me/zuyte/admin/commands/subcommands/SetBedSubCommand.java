package me.zuyte.admin.commands.subcommands;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import me.zuyte.admin.Admin;
import me.zuyte.admin.commands.storage.Cache;
import me.zuyte.admin.utils.TextUtils;
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
            TextUtils.sendPlayerConfigString("defaults.no-permission", p);
            return;
        }
        if (args.length > 1) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                TextUtils.sendPlayerConfigString("defaults.player-not-found", p);
                return;
            }
            if (arenaUtil.getArenaByPlayer(player) == null) {
                TextUtils.sendPlayerConfigString("player-not-joined-arena", p);
                return;
            }
            IArena arena = arenaUtil.getArenaByPlayer(player);
            if (args.length >= 3) {
                if (args[2].equalsIgnoreCase("true")) {
                    ITeam playerTeam = arena.getTeam(player);
                    playerTeam.setBedDestroyed(false);
                    BlockFace targetFace = Cache.getArenaBedsCache(playerTeam);
                    placeBed(playerTeam.getBed(), targetFace.getOppositeFace());
                    TextUtils.sendPlayerConfigString("defaults.success", p);
                    return;
                } else if (args[2].equalsIgnoreCase("false")) {
                    ITeam playerTeam = arena.getTeam(player);
                    playerTeam.setBedDestroyed(true);
                    TextUtils.sendPlayerConfigString("defaults.success", p);
                    return;
                }
            }
        }
        TextUtils.sendPlayerConfigString("usage.setbed", p);
    }

    private void console(ConsoleCommandSender c, String[] args) {
        if (args.length > 1) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                TextUtils.sendDefaultConfigString("defaults.player-not-found", c);
                return;
            }
            if (arenaUtil.getArenaByPlayer(player) == null) {
                TextUtils.sendDefaultConfigString("defaults.player-not-joined-arena", c);
                return;
            }
            IArena arena = arenaUtil.getArenaByPlayer(player);
            if (args.length >= 3) {
                if (args[2].equalsIgnoreCase("true")) {
                    ITeam playerTeam = arena.getTeam(player);
                    playerTeam.setBedDestroyed(false);
                    BlockFace targetFace = Cache.getArenaBedsCache(playerTeam);
                    placeBed(playerTeam.getBed(), targetFace.getOppositeFace());
                    TextUtils.sendDefaultConfigString("defaults.success", c);
                    return;
                } else if (args[2].equalsIgnoreCase("false")) {
                    ITeam playerTeam = arena.getTeam(player);
                    playerTeam.setBedDestroyed(true);
                    TextUtils.sendDefaultConfigString("defaults.success", c);
                    return;
                }
            }
        }
        TextUtils.sendDefaultConfigString("usage.setbed", c);
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
