package me.zuyte.admin.listeners;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.player.PlayerLeaveArenaEvent;
import me.zuyte.admin.Admin;
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
                Admin.getInstance().beds.put(e.getArena().getArenaName() + team.getName() + "Main", targetFace);
            }
            if (Admin.getInstance().temp.containsValue(e.getArena().getArenaName())) {
                for (Player player : e.getArena().getPlayers()) {
                    String playerName = player.getName();
                    if (Admin.getInstance().temp.containsKey(playerName)) {
                        String value = Admin.getInstance().temp.get(playerName);
                        if (value.equals(e.getArena().getArenaName())) {
                            Admin.getInstance().temp.remove(playerName);
                        }
                    }
                    Admin.getInstance().temp.remove(playerName + "-team");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerLeaveArena(PlayerLeaveArenaEvent e) {
        if (Admin.getInstance().temp.containsKey(e.getPlayer().getName())) {
            if (Admin.getInstance().temp.get(e.getPlayer().getName()) == e.getArena().getArenaName()) {
                Admin.getInstance().temp.remove(e.getPlayer().getName());
            }
        }
        if (Admin.getInstance().mlg.containsKey(e.getPlayer().getName())) {
            Admin.getInstance().mlg.remove(e.getPlayer().getName());
        }
        if (Admin.getInstance().temp.containsKey(e.getPlayer().getName() + "-team")) {
            Admin.getInstance().temp.remove(e.getPlayer().getName() + "-team");
        }
        if (Admin.getInstance().kaboom.containsKey(e.getPlayer().getName())) {
            Admin.getInstance().kaboom.remove(e.getPlayer().getName());
        }
    }
}
