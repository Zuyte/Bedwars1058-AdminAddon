package me.zuyte.admin.listeners.bw1058;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.player.PlayerLeaveArenaEvent;
import me.zuyte.admin.Admin;
import me.zuyte.admin.storage.Cache_BW1058;
import org.bukkit.Bukkit;
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
            for (Player player : e.getArena().getPlayers()) {
                if (Cache_BW1058.getPlayerTeam(player) == null) continue;
                Bukkit.getScheduler().runTaskAsynchronously(Admin.getInstance(), () -> {
                    for (ITeam team : e.getArena().getTeams()) {
                        team.getMembers().remove(player);
                        if (Cache_BW1058.getPlayerTeam(player) == team) {
                            team.addPlayers(player);
                        }
                    }
                    Cache_BW1058.setPlayerTeam(player, e.getArena().getTeam(player));
                });

                if (e.getArena().getTeam(player).getSize() == 0 || (e.getArena().getTeam(player).getMembers().contains(player) && e.getArena().getTeam(player).getMembers().size() == 1)) {
                    e.getArena().getTeam(player).setBedDestroyed(true);
                }

                Bukkit.getScheduler().runTaskAsynchronously(Admin.getInstance(), () -> {
                    if (e.getArena().getTeam(player) != Cache_BW1058.getPlayerTeam(player)) {
                        if (e.getArena() != null) e.getArena().getTeam(player).getMembers().remove(player);
                        Cache_BW1058.getPlayerTeam(player).addPlayers(player);
                    }
                });
            }
            for (ITeam team : e.getArena().getTeams()) {
                if (team.isBedDestroyed()) continue;
                Bed bedBlock = ((Bed)team.getBed().getBlock().getState().getData());
                Cache_BW1058.setArenaBedsCache(team, bedBlock.getFacing());
            }
        } else if (e.getNewState() == GameState.restarting) {
            Bukkit.getScheduler().runTaskAsynchronously(Admin.getInstance(), () -> {
                for (ITeam team : e.getArena().getTeams())
                    Cache_BW1058.removeArenaBedsCache(team);
            });
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
}
