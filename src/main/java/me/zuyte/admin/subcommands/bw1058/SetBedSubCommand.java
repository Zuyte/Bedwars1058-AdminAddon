package me.zuyte.admin.subcommands.bw1058;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import me.zuyte.admin.Admin;
import me.zuyte.admin.storage.Cache_BW1058;
import me.zuyte.admin.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.Bed;
import org.bukkit.material.MaterialData;

public class SetBedSubCommand {

    BedWars.ArenaUtil arenaUtil = Admin.getInstance().bw1058.getArenaUtil();

    public SetBedSubCommand(CommandSender commandSender, String[] args) {
        if (commandSender instanceof Player)
            player((Player) commandSender, args);
        else if (commandSender instanceof ConsoleCommandSender)
            console((ConsoleCommandSender) commandSender, args);
    }

    private void player(Player p, String[] args) {
        if (!p.hasPermission("bw.admin.setbed")) {
            TextUtils.sendPlayerConfigStringBW1058("defaults.no-permission", p);
            return;
        }
        if (args.length > 1) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                TextUtils.sendPlayerConfigStringBW1058("defaults.player-not-found", p);
                return;
            }
            if (arenaUtil.getArenaByPlayer(player) == null) {
                TextUtils.sendPlayerConfigStringBW1058("player-not-joined-arena", p);
                return;
            }
            IArena arena = arenaUtil.getArenaByPlayer(player);
            if (args.length >= 3) {
                if (args[2].equalsIgnoreCase("true")) {
                    ITeam playerTeam = arena.getTeam(player);
                    playerTeam.setBedDestroyed(false);
                    placeBed(playerTeam);
                    TextUtils.sendPlayerConfigStringBW1058("defaults.success", p);
                    return;
                } else if (args[2].equalsIgnoreCase("false")) {
                    ITeam playerTeam = arena.getTeam(player);
                    playerTeam.setBedDestroyed(true);
                    TextUtils.sendPlayerConfigStringBW1058("defaults.success", p);
                    return;
                }
            }
        }
        TextUtils.sendPlayerConfigStringBW1058("usage.setbed", p);
    }

    private void console(ConsoleCommandSender c, String[] args) {
        if (args.length > 1) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                TextUtils.sendDefaultConfigStringBW1058("defaults.player-not-found", c);
                return;
            }
            if (arenaUtil.getArenaByPlayer(player) == null) {
                TextUtils.sendDefaultConfigStringBW1058("defaults.player-not-joined-arena", c);
                return;
            }
            IArena arena = arenaUtil.getArenaByPlayer(player);
            if (args.length >= 3) {
                if (args[2].equalsIgnoreCase("true")) {
                    ITeam playerTeam = arena.getTeam(player);
                    playerTeam.setBedDestroyed(false);
                    placeBed(playerTeam);
                    TextUtils.sendDefaultConfigStringBW1058("defaults.success", c);
                    return;
                } else if (args[2].equalsIgnoreCase("false")) {
                    ITeam playerTeam = arena.getTeam(player);
                    playerTeam.setBedDestroyed(true);
                    TextUtils.sendDefaultConfigStringBW1058("defaults.success", c);
                    return;
                }
            }
        }
        TextUtils.sendDefaultConfigStringBW1058("usage.setbed", c);
    }

    private void placeBed(ITeam playerTeam) {
        BlockFace face = Cache_BW1058.getArenaBedsCache(playerTeam);
        if (face == null) {
            face = BlockFace.SELF;
        }
        BlockState bedFoot = playerTeam.getBed().getBlock().getState();
        BlockState bedHead = bedFoot.getBlock().getRelative(face.getOppositeFace()).getState();
        bedFoot.setType(Material.BED_BLOCK);
        bedHead.setType(Material.BED_BLOCK);
        bedFoot.setRawData((byte) face.ordinal());
        bedHead.setRawData((byte) (face.ordinal() + 8));
        bedFoot.update(true, false);
        bedHead.update(true, true);
    }
}

