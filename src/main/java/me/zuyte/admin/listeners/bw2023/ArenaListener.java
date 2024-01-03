package me.zuyte.admin.listeners.bw2023;

import com.tomkeuper.bedwars.api.arena.GameState;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import com.tomkeuper.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.tomkeuper.bedwars.api.events.player.PlayerLeaveArenaEvent;
import me.zuyte.admin.Admin;
import me.zuyte.admin.storage.Cache_BW2023;
import org.bukkit.Material;
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
                ITeam playerTeam = e.getArena().getTeam(player);
                if (Cache_BW2023.getPlayerTeam(player) == null) continue;

                for (ITeam team : e.getArena().getTeams()) {
                    team.getMembers().remove(player);
                    if (Cache_BW2023.getPlayerTeam(player) == team) {
                        team.addPlayers(player);
                    }
                }
                Cache_BW2023.setPlayerTeam(player, playerTeam);
                Cache_BW2023.setPlayerReviveTeam(player, playerTeam);

                if (playerTeam.getSize() == 0 || (playerTeam.getMembers().contains(player) && playerTeam.getMembers().size() == 1)) {
                    playerTeam.setBedDestroyed(true);
                }
            }
            for (ITeam team : e.getArena().getTeams()) {
                if (team.isBedDestroyed()) continue;
                if (team.getBed().getBlock().getType() == Material.AIR) {
                    Admin.getInstance().getLogger().severe("Couldn't find bed block for team " + team.getName() + " in arena " + e.getArena().getArenaName() + ", revive and setbed command may raise errors!");
                }
                Bed bedBlock = ((Bed) team.getBed().getBlock().getState().getData());
                Cache_BW2023.setArenaBedsCache(team, bedBlock.getFacing());
            }
        } else if (e.getNewState() == GameState.restarting) {
            for (ITeam team : e.getArena().getTeams())
                Cache_BW2023.removeArenaBedsCache(team);
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
