package me.zuyte.admin.subcommands.bw2023;

import com.tomkeuper.bedwars.api.BedWars;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import me.zuyte.admin.Admin;
import me.zuyte.admin.storage.Cache_BW2023;
import me.zuyte.admin.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SetBedSubCommand {

    BedWars.ArenaUtil arenaUtil = Admin.getInstance().bw2023.getArenaUtil();

    public SetBedSubCommand(CommandSender commandSender, String[] args) {
        if (commandSender instanceof Player)
            player((Player) commandSender, args);
        else if (commandSender instanceof ConsoleCommandSender)
            console((ConsoleCommandSender) commandSender, args);
    }

    private void player(Player p, String[] args) {
        if (!p.hasPermission("bw.admin.setbed")) {
            TextUtils.sendPlayerConfigStringBW2023("defaults.no-permission", p);
            return;
        }
        if (args.length > 1) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                TextUtils.sendPlayerConfigStringBW2023("defaults.player-not-found", p);
                return;
            }
            if (arenaUtil.getArenaByPlayer(player) == null) {
                TextUtils.sendPlayerConfigStringBW2023("player-not-joined-arena", p);
                return;
            }
            IArena arena = arenaUtil.getArenaByPlayer(player);
            if (args.length >= 3) {
                if (args[2].equalsIgnoreCase("true")) {
                    ITeam playerTeam = arena.getTeam(player);
                    playerTeam.setBedDestroyed(false);
                    placeBed(playerTeam);
                    TextUtils.sendPlayerConfigStringBW2023("defaults.success", p);
                    return;
                } else if (args[2].equalsIgnoreCase("false")) {
                    ITeam playerTeam = arena.getTeam(player);
                    playerTeam.setBedDestroyed(true);
                    TextUtils.sendPlayerConfigStringBW2023("defaults.success", p);
                    return;
                }
            }
        }
        TextUtils.sendPlayerConfigStringBW2023("usage.setbed", p);
    }

    private void console(ConsoleCommandSender c, String[] args) {
        if (args.length > 1) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                TextUtils.sendDefaultConfigStringBW2023("defaults.player-not-found", c);
                return;
            }
            if (arenaUtil.getArenaByPlayer(player) == null) {
                TextUtils.sendDefaultConfigStringBW2023("defaults.player-not-joined-arena", c);
                return;
            }
            IArena arena = arenaUtil.getArenaByPlayer(player);
            if (args.length >= 3) {
                if (args[2].equalsIgnoreCase("true")) {
                    ITeam playerTeam = arena.getTeam(player);
                    playerTeam.setBedDestroyed(false);
                    placeBed(playerTeam);
                    TextUtils.sendDefaultConfigStringBW2023("defaults.success", c);
                    return;
                } else if (args[2].equalsIgnoreCase("false")) {
                    ITeam playerTeam = arena.getTeam(player);
                    playerTeam.setBedDestroyed(true);
                    TextUtils.sendDefaultConfigStringBW2023("defaults.success", c);
                    return;
                }
            }
        }
        TextUtils.sendDefaultConfigStringBW2023("usage.setbed", c);
    }

    private void placeBed(ITeam playerTeam) {
        BlockFace face = Cache_BW2023.getArenaBedsCache(playerTeam);
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

