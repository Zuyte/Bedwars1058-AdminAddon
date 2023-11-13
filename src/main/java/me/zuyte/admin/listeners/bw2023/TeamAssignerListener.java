package me.zuyte.admin.listeners.bw2023;

import com.tomkeuper.bedwars.api.arena.team.ITeam;
import com.tomkeuper.bedwars.api.events.gameplay.TeamAssignEvent;
import me.zuyte.admin.Admin;
import me.zuyte.admin.storage.Cache_BW2023;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TeamAssignerListener implements Listener {
    private Admin instance;
    public TeamAssignerListener(Admin instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onTeamAssign(TeamAssignEvent e) {
        if (Cache_BW2023.containsPlayerTeam(e.getPlayer())) {
            ITeam firstTeam = e.getTeam();
            firstTeam.getMembers().remove(e.getPlayer());
            ITeam team = Cache_BW2023.getPlayerTeam(e.getPlayer());
            if (team.getArena().equals(e.getArena())) {
                e.setCancelled(true);
                team.addPlayers(e.getPlayer());
            }
        }
    }
}
