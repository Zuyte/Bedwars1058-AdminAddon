package me.zuyte.admin.listeners;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.TeamAssignEvent;
import com.andrei1058.bedwars.teamselector.api.events.TeamSelectorChooseEvent;
import me.zuyte.admin.Admin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TeamSelectorListener implements Listener {
    private final Admin instance;
    public TeamSelectorListener(Admin instance) {
        this.instance = instance;
    }
    @EventHandler
    public void onTeamSelectorChoose(TeamSelectorChooseEvent e) {
        if (Admin.getInstance().temp.containsKey(e.getPlayer().getName())) {
            if (Admin.getInstance().temp.get(e.getPlayer().getName()) == e.getArena().getArenaName()) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onTeamAssign(TeamAssignEvent e) {
        if (Admin.getInstance().temp.containsKey(e.getPlayer().getName())) {
            if (Admin.getInstance().temp.get(e.getPlayer().getName()).equals(e.getArena().getArenaName())) {
                e.setCancelled(true);
                ITeam playerSelTeam = e.getArena().getTeam(Admin.getInstance().temp.get(e.getPlayer().getName() + "-team"));
                playerSelTeam.addPlayers(e.getPlayer());
            }
        }
    }
}
