package me.zuyte.admin.listeners;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.player.PlayerLeaveArenaEvent;
import me.zuyte.admin.Admin;
import me.zuyte.admin.commands.storage.Cache;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ArenaListener implements Listener {
    private final Admin instance;
    public ArenaListener(Admin instance) {
        this.instance = instance;
    }
    @EventHandler
    public void onGameStateChange(GameStateChangeEvent e) {
        if (e.getNewState() == GameState.playing) {
            for (ITeam team : e.getArena().getTeams()) {
                if (team.isBedDestroyed()) return;
                BlockFace targetFace = ((org.bukkit.material.Bed) team.getBed().getBlock().getState().getData()).getFacing();
                Cache.setArenaBedsCache(team, targetFace);
            }
        }
    }

    @EventHandler
    public void onPlayerLeaveArena(PlayerLeaveArenaEvent e) {
        if (Cache.containsPlayerTeam(e.getPlayer())) {
            if (Cache.getPlayerTeam(e.getPlayer()).getArena().equals(e.getArena())) {
                Cache.removePlayerTeam(e.getPlayer());
            }
        }
        if (Cache.containsMLGCache(e.getPlayer())) {
            Cache.removeMLGCache(e.getPlayer());
        }
        if (Cache.containsKaboomCache(e.getPlayer())) {
            Cache.removeKaboomCache(e.getPlayer());
        }
    }
}
