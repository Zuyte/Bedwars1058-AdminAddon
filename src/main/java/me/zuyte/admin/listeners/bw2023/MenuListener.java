package me.zuyte.admin.listeners.bw2023;

import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import me.zuyte.admin.Admin;
import me.zuyte.admin.storage.Cache_BW2023;
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

        if (!Admin.getInstance().bw2023.getVersionSupport().isCustomBedWarsItem(e.getCurrentItem()) || !Admin.getInstance().bw2023.getVersionSupport().getCustomData(e.getCurrentItem()).equals("bwa-team-selector")) {
            return;
        }
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();
        IArena arena = Admin.getInstance().bw2023.getArenaUtil().getArenaByPlayer(p);
        String teamName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
        ITeam team = arena.getTeam(teamName);
        if (arena.getTeam(teamName) == null) {
            TextUtils.sendPlayerConfigStringBW2023("admin-message.team.not-found", p);
            return;
        }
        if (team.getMembers().size() == arena.getMaxInTeam()) {
            TextUtils.sendPlayerConfigStringBW2023("admin-message.team.full", p);
            return;
        }

        Cache_BW2023.setPlayerTeam(p, team);
        p.sendMessage(TextUtils.getPlayerConfigStringBW2023("admin-message.team.success", p).replace("{player}", p.getName()).replace("{team}", team.getColor().chat() + team.getDisplayName(instance.bw2023.getPlayerLanguage(p))));
        p.closeInventory();
    }
}