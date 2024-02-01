package me.zuyte.admin.storage;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import me.zuyte.admin.utils.ExtraUtils;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Cache_BW1058 {
    private static Map<Player, ITeam> playerTeamCache = new HashMap<>();
    private static Map<Player, ITeam> playerPreviousTeamCache = new HashMap<>();
    private static Map<Player, ItemStack> playerMLGCache = new HashMap<>();
    private static HashSet<Player> playerKaboomCache = new HashSet<>();
    private static Map<ITeam, BlockFace> arenaBedsCache = new HashMap<>();
    private static Map<Player, String> playerReviveTeamCache = new HashMap<>();

    public static String getPlayerReviveTeam(Player player) {
        ExtraUtils.debug(playerReviveTeamCache.toString() + "  #1");
        return playerReviveTeamCache.get(player);
    }

    public static void setPlayerReviveTeam(Player player, String team) {
        ExtraUtils.debug(playerReviveTeamCache.toString() + "  #2");
        playerReviveTeamCache.put(player, team);
    }

    public static void setPlayerTeam(Player player, ITeam team) {
        ExtraUtils.debug(playerTeamCache.toString() + "  #3");
        playerTeamCache.put(player, team);
    }

    public static ITeam getPlayerTeam(Player player) {
        ExtraUtils.debug(playerTeamCache.toString() + "  #4");
        return playerTeamCache.get(player);
    }

    public static void removePlayerTeam(Player player) {
        ExtraUtils.debug(playerTeamCache.toString() + "  #5");
        playerTeamCache.remove(player);
    }

    public static boolean containsPlayerTeam(Player player) {
        return playerTeamCache.containsKey(player);
    }

    public static void setMLGCache(Player player, ItemStack replacedItem) {
        playerMLGCache.put(player, replacedItem);
    }

    public static void removeMLGCache(Player player) {
        playerMLGCache.remove(player);
    }

    public static ItemStack getMLGCache(Player player) {
        return playerMLGCache.get(player);
    }

    public static boolean containsMLGCache(Player player) {
        return playerMLGCache.containsKey(player);
    }

    public static void addKaboomCache(Player player) {
        playerKaboomCache.add(player);
    }

    public static void removeKaboomCache(Player player) {
        playerKaboomCache.remove(player);
    }

    public static boolean containsKaboomCache(Player player) {
        return playerKaboomCache.contains(player);
    }

    public static void setArenaBedsCache(ITeam team, BlockFace blockFace) {
        arenaBedsCache.put(team, blockFace);
    }

    public static void removeArenaBedsCache(ITeam team) {
        arenaBedsCache.remove(team);
    }

    public static BlockFace getArenaBedsCache(ITeam team) {
        return arenaBedsCache.getOrDefault(team, null);
    }
}
