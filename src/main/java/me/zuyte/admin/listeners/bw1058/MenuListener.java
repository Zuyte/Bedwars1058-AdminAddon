package me.zuyte.admin.listeners.bw1058;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import me.zuyte.admin.Admin;
import me.zuyte.admin.storage.Cache_BW1058;
import me.zuyte.admin.utils.TextUtils;
import org.bukkit.Bukkit;
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

        if (!Admin.getInstance().bw1058.getVersionSupport().isCustomBedWarsItem(e.getCurrentItem()) || !Admin.getInstance().bw1058.getVersionSupport().getCustomData(e.getCurrentItem()).startsWith("bwa-team-selector-")) {
            return;
        }
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();
        IArena arena = Admin.getInstance().bw1058.getArenaUtil().getArenaByPlayer(p);
        String teamName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
        ITeam team = arena.getTeam(teamName);
        if (arena.getTeam(teamName) == null) {
            TextUtils.sendPlayerConfigStringBW1058("admin-message.team.not-found", p);
            return;
        }
        if (team.getMembers().size() == arena.getMaxInTeam()) {
            TextUtils.sendPlayerConfigStringBW1058("admin-message.team.full", p);
            return;
        }

        Player player = Bukkit.getPlayer(Admin.getInstance().bw1058.getVersionSupport().getCustomData(e.getCurrentItem()).replace("bwa-team-selector-", ""));

        if (player == null) {
            TextUtils.sendPlayerConfigStringBW1058("admin-message.player.not-found", p);
            return;
        }

        if (arena.getTeam(player) != null) {
            arena.getTeam(player).getMembers().remove(player);
        }

        team.addPlayers(player);
        Cache_BW1058.setPlayerTeam(player, team);
        p.sendMessage(TextUtils.getPlayerConfigStringBW1058("admin-message.team.set-success", p).replace("{team}", team.getColor().chat() + team.getDisplayName(instance.bw1058.getPlayerLanguage(p))));
        p.closeInventory();
    }
}