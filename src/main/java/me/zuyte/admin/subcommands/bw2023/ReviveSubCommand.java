package me.zuyte.admin.subcommands.bw2023;

import com.tomkeuper.bedwars.api.BedWars;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import me.zuyte.admin.Admin;
import me.zuyte.admin.storage.Cache_BW2023;
import me.zuyte.admin.utils.TextUtils;
import me.zuyte.admin.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ReviveSubCommand {
    public ReviveSubCommand(CommandSender sender, String[] args) {
        player((Player) sender, args);
    }

    BedWars.ArenaUtil arenaUtil = Admin.getInstance().bw2023.getArenaUtil();

    public void player(Player p, String[] args) {
        if (!p.hasPermission("bw.admin.revive")) {
            TextUtils.sendPlayerConfigStringBW2023("defaults.no-permission", p);
            return;
        }
        if (args.length > 2) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                TextUtils.sendPlayerConfigStringBW2023("defaults.player-not-found", p);
                return;
            }
            if (arenaUtil.getArenaByPlayer(player) == null) {
                TextUtils.sendPlayerConfigStringBW2023("defaults.player-not-found", p);
                return;
            }
            IArena arena = arenaUtil.getArenaByPlayer(player);
            if (args.length >= 3) {
                if (args[2].equalsIgnoreCase("bed")) {
                    if (!arena.getPlayers().contains(player)) arena.getPlayers().add(player);
                    ITeam playerTeam = Cache_BW2023.getPlayerTeam(player);
                    if (!playerTeam.getMembers().contains(player)) playerTeam.getMembers().add(player);
                    arena.getSpectators().remove(player);
                    arena.startReSpawnSession(player, 0);
                    TitleUtils.sendTitle(player, TextUtils.getPlayerConfigStringBW2023("player-message.revive.title", player), TextUtils.getPlayerConfigStringBW2023("player-message.revive.subtitle", player).replace("{player}", p.getName()), 1, 6, 1);
                    playerTeam.setBedDestroyed(false);
                    BlockFace targetFace = Cache_BW2023.getArenaBedsCache(playerTeam);
                    placeBed(playerTeam.getBed(), targetFace.getOppositeFace());
                    p.sendMessage(TextUtils.getPlayerConfigStringBW2023("admin-message.revive.success", p).replace("{player}", player.getName()));
                    return;
                } else if (args[2].equalsIgnoreCase("final")) {
                    if (!arena.getPlayers().contains(player)) arena.getPlayers().add(player);
                    ITeam playerTeam = Cache_BW2023.getPlayerTeam(player);
                    if (!playerTeam.getMembers().contains(player)) playerTeam.getMembers().add(player);
                    arena.getSpectators().remove(player);
                    arena.startReSpawnSession(player, 0);
                    TitleUtils.sendTitle(player, TextUtils.getPlayerConfigStringBW2023("player-message.revive.title", player), TextUtils.getPlayerConfigStringBW2023("player-message.revive.subtitle", player).replace("{player}", p.getName()), 1, 6, 1);
                    playerTeam.setBedDestroyed(true);
                    p.sendMessage(TextUtils.getPlayerConfigStringBW2023("admin-message.revive.success", p).replace("{player}", player.getName()));
                    return;
                }
            }
        }
        TextUtils.sendPlayerConfigStringBW2023("usage.revive", p);
    }

    public void console(ConsoleCommandSender c, String[] args) {
        if (args.length > 2) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                TextUtils.sendDefaultConfigStringBW2023("defaults.player-not-found", c);
                return;
            }
            if (arenaUtil.getArenaByPlayer(player) == null) {
                TextUtils.sendDefaultConfigStringBW2023("defaults.player-not-found", c);
                return;
            }
            IArena arena = arenaUtil.getArenaByPlayer(player);
            if (args.length >= 3) {
                if (args[2].equalsIgnoreCase("bed")) {
                    if (!arena.getPlayers().contains(player)) arena.getPlayers().add(player);
                    ITeam playerTeam = Cache_BW2023.getPlayerTeam(player);
                    if (!playerTeam.getMembers().contains(player)) playerTeam.getMembers().add(player);
                    arena.getSpectators().remove(player);
                    arena.startReSpawnSession(player, 0);
                    TitleUtils.sendTitle(player, TextUtils.getPlayerConfigStringBW2023("player-message.revive.title", player), TextUtils.getPlayerConfigStringBW2023("player-message.revive.subtitle", player).replace("{player}", c.getName()), 1, 6, 1);
                    playerTeam.setBedDestroyed(false);
                    BlockFace targetFace = Cache_BW2023.getArenaBedsCache(playerTeam);
                    placeBed(playerTeam.getBed(), targetFace.getOppositeFace());
                    c.sendMessage(TextUtils.getDefaultConfigStringBW2023("admin-message.revive.success").replace("{player}", player.getName()));
                    return;
                } else if (args[2].equalsIgnoreCase("final")) {
                    if (!arena.getPlayers().contains(player)) arena.getPlayers().add(player);
                    ITeam playerTeam = Cache_BW2023.getPlayerTeam(player);
                    if (!playerTeam.getMembers().contains(player)) playerTeam.getMembers().add(player);
                    arena.getSpectators().remove(player);
                    arena.startReSpawnSession(player, 0);
                    TitleUtils.sendTitle(player, TextUtils.getPlayerConfigStringBW2023("player-message.revive.title", player), TextUtils.getPlayerConfigStringBW2023("player-message.revive.subtitle", player).replace("{player}", c.getName()), 1, 6, 1);
                    playerTeam.setBedDestroyed(true);
                    c.sendMessage(TextUtils.getDefaultConfigStringBW2023("admin-message.revive.success").replace("{player}", player.getName()));
                    return;
                }
            }
        }
        TextUtils.sendDefaultConfigStringBW2023("usage.revive", c);
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
