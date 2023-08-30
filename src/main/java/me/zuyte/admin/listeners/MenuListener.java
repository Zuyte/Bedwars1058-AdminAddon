package me.zuyte.admin.listeners;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import me.zuyte.admin.Admin;
import me.zuyte.admin.commands.storage.Cache;
import me.zuyte.admin.utils.TextUtils;
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
            TextUtils.sendPlayerConfigString("admin-message.team.not-found", p);
            return;
        }
        if (team.getMembers().size() == arena.getMaxInTeam()) {
            TextUtils.sendPlayerConfigString("admin-message.team.full", p);
            return;
        }

        Cache.setPlayerTeam(p, team);
        p.sendMessage(TextUtils.getPlayerConfigString("admin-message.team.success", p).replace("{player}", p.getName()).replace("{team}", team.getColor().chat() + team.getDisplayName(instance.bw.getPlayerLanguage(p))));
        p.closeInventory();
    }
}