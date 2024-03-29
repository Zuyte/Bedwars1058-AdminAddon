package me.zuyte.admin.subcommands.bw1058;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import me.zuyte.admin.Admin;
import me.zuyte.admin.storage.Cache_BW1058;
import me.zuyte.admin.utils.ExtraUtils;
import me.zuyte.admin.utils.TextUtils;
import me.zuyte.admin.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ReviveSubCommand {
    public ReviveSubCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player)
            player((Player) sender, args);
        else if (sender instanceof ConsoleCommandSender)
            console((ConsoleCommandSender) sender, args);
    }

    BedWars.ArenaUtil arenaUtil = Admin.getInstance().bw1058.getArenaUtil();

    public void player(Player p, String[] args) {
        if (!p.hasPermission("bw.admin.revive")) {
            TextUtils.sendPlayerConfigStringBW1058("defaults.no-permission", p);
            return;
        }
        if (args.length > 2) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                TextUtils.sendPlayerConfigStringBW1058("defaults.player-not-found", p);
                return;
            }
            if (arenaUtil.getArenaByPlayer(player) == null) {
                TextUtils.sendPlayerConfigStringBW1058("defaults.player-not-found", p);
                return;
            }
            IArena arena = arenaUtil.getArenaByPlayer(player);
            if (args.length >= 3) {
                if (args[2].equalsIgnoreCase("bed")) {
                    if (revive(p, arena, player, true)) return;
                    p.sendMessage(TextUtils.getPlayerConfigStringBW1058("admin-message.revive.success", p).replace("{player}", player.getName()));
                    return;
                } else if (args[2].equalsIgnoreCase("final")) {
                    if (revive(p, arena, player, false)) return;
                    p.sendMessage(TextUtils.getPlayerConfigStringBW1058("admin-message.revive.success", p).replace("{player}", player.getName()));
                    return;
                }
            }
        }
        TextUtils.sendPlayerConfigStringBW1058("usage.revive", p);
    }

    public void console(ConsoleCommandSender c, String[] args) {
        if (args.length > 2) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                TextUtils.sendDefaultConfigStringBW1058("defaults.player-not-found", c);
                return;
            }
            if (arenaUtil.getArenaByPlayer(player) == null) {
                TextUtils.sendDefaultConfigStringBW1058("defaults.player-not-found", c);
                return;
            }
            IArena arena = arenaUtil.getArenaByPlayer(player);
            if (args.length >= 3) {
                if (args[2].equalsIgnoreCase("bed")) {
                    revive(c, arena, player, true);
                    c.sendMessage(TextUtils.getDefaultConfigStringBW1058("admin-message.revive.success").replace("{player}", player.getName()));
                    return;
                } else if (args[2].equalsIgnoreCase("final")) {
                    revive(c, arena, player, false);
                    c.sendMessage(TextUtils.getDefaultConfigStringBW1058("admin-message.revive.success").replace("{player}", player.getName()));
                    return;
                }
            }
        }
        TextUtils.sendDefaultConfigStringBW1058("usage.revive", c);
    }

    private boolean revive(CommandSender p, IArena arena, Player player, boolean isBed) {
        if (!arena.getPlayers().contains(player)) arena.getPlayers().add(player);
        ITeam playerTeam = null;
        String oldTeamName = Cache_BW1058.getPlayerReviveTeam(player);

        for (ITeam t : arena.getTeams()) {
            if (t.getName().equalsIgnoreCase(oldTeamName)) playerTeam = t;
        }
        try {
            if (!playerTeam.getMembers().contains(player)) playerTeam.getMembers().add(player);
        } catch (Exception e) {
            if (p instanceof Player)
                p.sendMessage(TextUtils.getPlayerConfigStringBW1058("admin-message.revive.error", (Player) p));
            else if (p instanceof ConsoleCommandSender)
                p.sendMessage(TextUtils.getDefaultConfigStringBW1058("admin-message.revive.error"));
            return true;
        }
        arena.getSpectators().remove(player);
        arena.startReSpawnSession(player, 0);
        TitleUtils.sendTitle(player, TextUtils.getPlayerConfigStringBW1058("player-message.revive.title", player), TextUtils.getPlayerConfigStringBW1058("player-message.revive.subtitle", player).replace("{player}", p.getName()), 1, 6, 1);
        Admin.getInstance().bw1058.getScoreboardManager().giveSidebar(player, arena, true);
        playerTeam.setBedDestroyed(!isBed);
        if (isBed) ExtraUtils.placeBed_BW1058(playerTeam);
        player.setGameMode(GameMode.SURVIVAL);
        return false;
    }

}
