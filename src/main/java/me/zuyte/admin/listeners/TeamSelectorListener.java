package me.zuyte.admin.listeners;

import com.andrei1058.bedwars.teamselector.api.events.TeamSelectorChooseEvent;
import me.zuyte.admin.Admin;
import me.zuyte.admin.storage.Cache;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TeamSelectorListener implements Listener {
    private final Admin instance;
    public TeamSelectorListener(Admin instance) {
        this.instance = instance;
    }
    @EventHandler
    public void onTeamSelectorChoose(TeamSelectorChooseEvent e) {
        if (Cache.containsPlayerTeam(e.getPlayer())) {
            if (Cache.getPlayerTeam(e.getPlayer()).getArena().equals(e.getArena())) {
                e.setCancelled(true);
            }
        }
    }
}
