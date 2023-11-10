package me.zuyte.admin.listeners.bw1058;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.TeamAssignEvent;
import me.zuyte.admin.Admin;
import me.zuyte.admin.storage.Cache_BW1058;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TeamAssignerListener implements Listener {
    private Admin instance;
    public TeamAssignerListener(Admin instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onTeamAssign(TeamAssignEvent e) {
        if (Cache_BW1058.containsPlayerTeam(e.getPlayer())) {
            ITeam team = Cache_BW1058.getPlayerTeam(e.getPlayer());
            if (team.getArena().equals(e.getArena())) {
                e.setCancelled(true);
                team.addPlayers(e.getPlayer());
            }
        }
    }
}
