package me.zuyte.admin.listeners.bw2023;

import com.tomkeuper.bedwars.api.arena.GameState;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import com.tomkeuper.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.tomkeuper.bedwars.api.events.player.PlayerLeaveArenaEvent;
import me.zuyte.admin.Admin;
import me.zuyte.admin.storage.Cache_BW1058;
import me.zuyte.admin.storage.Cache_BW2023;
import org.bukkit.Bukkit;
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
        if (e.getNewState() == GameState.playing) {
            Bukkit.getScheduler().runTaskAsynchronously(Admin.getInstance(), () -> {
                for (Player player : e.getArena().getPlayers())
                    Cache_BW2023.setPlayerTeam(player, e.getArena().getTeam(player));
                for (ITeam team : e.getArena().getTeams()) {
                    if (team.isBedDestroyed()) continue;
                    Bed bedBlock = ((Bed)team.getBed().getBlock().getState().getData());
                    Cache_BW2023.setArenaBedsCache(team, bedBlock.getFacing());
                }
            });
        } else if (e.getNewState() == GameState.restarting) {
            Bukkit.getScheduler().runTaskAsynchronously(Admin.getInstance(), () -> {
                for (ITeam team : e.getArena().getTeams())
                    Cache_BW2023.removeArenaBedsCache(team);
            });
        }
    }

    @EventHandler
    public void onPlayerLeaveArena(PlayerLeaveArenaEvent e) {
        if (Cache_BW2023.containsPlayerTeam(e.getPlayer())) {
            if (Cache_BW2023.getPlayerTeam(e.getPlayer()).getArena().equals(e.getArena())) {
                Cache_BW2023.removePlayerTeam(e.getPlayer());
            }
        }
        if (Cache_BW2023.containsMLGCache(e.getPlayer())) {
            Cache_BW2023.removeMLGCache(e.getPlayer());
        }
        if (Cache_BW2023.containsKaboomCache(e.getPlayer())) {
            Cache_BW2023.removeKaboomCache(e.getPlayer());
        }
    }
}
