package me.zuyte.admin.listeners.bw1058;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.player.PlayerLeaveArenaEvent;
import me.zuyte.admin.Admin;
import me.zuyte.admin.storage.Cache_BW1058;
import me.zuyte.admin.utils.ExtraUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.material.Bed;

public class ArenaListener implements Listener {
    private final Admin instance;

    public ArenaListener(Admin instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onGameStateChange(GameStateChangeEvent e) {
        if (e.getNewState() == GameState.starting) {
            for (ITeam team : e.getArena().getTeams()) {
                if (team.isBedDestroyed()) continue;
                if (team.getBed().getBlock().getType() == Material.AIR) {
                    Admin.getInstance().getLogger().severe("Couldn't find bed block for team " + team.getName() + " in arena " + e.getArena().getArenaName() + ", revive and setbed command may raise errors!");
                    continue;
                }
                if (ExtraUtils.MAIN_VERSION < 13) {
                    Bed bedBlock = ((Bed) team.getBed().getBlock().getState().getData());
                    Cache_BW1058.setArenaBedsCache(team, bedBlock.getFacing());
                } else {
                    Cache_BW1058.setArenaBedsCache(team, getBedFacing(team.getBed().getBlock()));
                }
            }
        }

        if (e.getNewState() == GameState.playing) {
            for (Player player : e.getArena().getPlayers())
                Cache_BW1058.setPlayerReviveTeam(player, e.getArena().getTeam(player).getName());
        }

        if (e.getNewState() == GameState.restarting) {
            for (ITeam team : e.getArena().getTeams())
                Cache_BW1058.removeArenaBedsCache(team);
        }
    }

    @EventHandler
    public void onPlayerLeaveArena(PlayerLeaveArenaEvent e) {
        if (Cache_BW1058.containsPlayerTeam(e.getPlayer())) {
            if (Cache_BW1058.getPlayerTeam(e.getPlayer()).getArena().equals(e.getArena())) {
                Cache_BW1058.removePlayerTeam(e.getPlayer());
            }
        }
        if (Cache_BW1058.containsMLGCache(e.getPlayer())) {
            Cache_BW1058.removeMLGCache(e.getPlayer());
        }
        if (Cache_BW1058.containsKaboomCache(e.getPlayer())) {
            Cache_BW1058.removeKaboomCache(e.getPlayer());
        }
    }

    public BlockFace getBedFacing(Block bedBlock) {
        try {
            Object blockData = bedBlock.getClass().getMethod("getBlockData").invoke(bedBlock);
            org.bukkit.block.data.type.Bed bed = (org.bukkit.block.data.type.Bed) blockData;
            if (bed.getPart() != org.bukkit.block.data.type.Bed.Part.HEAD) {
                Block bedBlock1 = bedBlock.getRelative(bed.getFacing().getOppositeFace());
                Object blockData1 = bedBlock.getClass().getMethod("getBlockData").invoke(bedBlock1);
                org.bukkit.block.data.type.Bed bed1 = (org.bukkit.block.data.type.Bed) blockData1;
                return bed1.getFacing();
            }
            return bed.getFacing();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
