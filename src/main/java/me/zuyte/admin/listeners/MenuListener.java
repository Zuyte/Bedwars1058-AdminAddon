package me.zuyte.admin.listeners;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import me.zuyte.admin.Admin;
import me.zuyte.admin.commands.storage.Cache;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListener implements Listener {
    private final Admin instance;

    public MenuListener(Admin instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta()) {
            return;
        }

        if (!Admin.getInstance().bw.getVersionSupport().isCustomBedWarsItem(e.getCurrentItem()) || !Admin.getInstance().bw.getVersionSupport().getCustomData(e.getCurrentItem()).equals("bwa-team-selector")) {
            return;
        }
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();
        IArena arena = Admin.getInstance().bw.getArenaUtil().getArenaByPlayer(p);
        String teamName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
        ITeam team = arena.getTeam(teamName);
        if (arena.getTeam(teamName) == null) {
            p.sendMessage(ChatColor.RED + "Error: Team not found");
            return;
        }
        if (team.getMembers().size() == arena.getMaxInTeam()) {
            p.sendMessage(ChatColor.RED + "Team is full!");
            return;
        }

        Cache.setPlayerTeam(p, team);
        p.sendMessage(ChatColor.GREEN + "Successfully set " + p.getName() + "'s Team to " + team.getColor().chat() + team.getName());
        p.closeInventory();
    }
}