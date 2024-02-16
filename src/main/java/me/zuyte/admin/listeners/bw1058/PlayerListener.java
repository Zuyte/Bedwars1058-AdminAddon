package me.zuyte.admin.listeners.bw1058;

import me.zuyte.admin.Admin;
import me.zuyte.admin.storage.Cache_BW1058;
import me.zuyte.admin.utils.ExtraUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

public class PlayerListener implements Listener {
    private final Admin instance;
    public PlayerListener(Admin instance) {
        this.instance = instance;
    }
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getDamager() instanceof LivingEntity) {
                if (e.getDamager().getCustomName() != null && e.getDamager().getCustomName().equals(ChatColor.RED + e.getEntity().getName() + "'s Enemy")) {
                    e.setCancelled(false);
                }
            }
            if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                if (e.getDamager().getType() == EntityType.FIREBALL) {
                    if (Admin.getInstance().bw1058.getArenaUtil().getArenaByPlayer(((Player) e.getEntity()).getPlayer()).isReSpawning(((Player) e.getEntity()).getPlayer()) || Admin.getInstance().bw1058.getArenaUtil().getArenaByPlayer(((Player) e.getEntity()).getPlayer()).isSpectator(((Player) e.getEntity()).getPlayer())) return;
                    e.setCancelled(false);
                }
            }
        }
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (Cache_BW1058.containsKaboomCache((Player) e.getEntity())) {
                    e.setCancelled(true);
                    Cache_BW1058.removeKaboomCache((Player) e.getEntity());
                }
            }
        }
    }

    @EventHandler
    public void ClickEvent(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            try {
                if (e.hasItem()) {
                    if (e.getItem().getType() == Material.STICK) {
                        if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Magic Toy Stick") && e.getPlayer().getItemInHand().containsEnchantment(Enchantment.LURE)) {
                            Block clickedBlock = e.getClickedBlock();
                            List<Block> blocks = ExtraUtils.getRadiusBlocks(clickedBlock.getLocation(), 4, false);
                            for (Block block : blocks) {
                                if (!block.getType().equals(Material.AIR) || !block.getType().name().endsWith("_BED")) {
                                    ExtraUtils.launchBlock(block);
                                }
                                e.getPlayer().setItemInHand(new ItemStack(Material.AIR));
                                Location location = e.getPlayer().getLocation();
                                e.getPlayer().setVelocity(new Vector(0, 10, 0));
                                e.getPlayer().getWorld().createExplosion(location, -1, false);
                            }
                        }
                    }
                }
            } catch (NullPointerException ex) {
                Admin.getInstance().getLogger().severe("Error occurred while using toystick");
            }
        }
    }
    @EventHandler
    public void onWBDeath(PlayerDeathEvent e) {
        if (Cache_BW1058.containsMLGCache(e.getEntity())) {
            Cache_BW1058.removeMLGCache(e.getEntity());
            e.getEntity().sendMessage(ChatColor.RED + "Failed!");
        }
    }
    @EventHandler
    public void onWBPlace(PlayerBucketEmptyEvent e) {
        if (Cache_BW1058.containsMLGCache(e.getPlayer())) {
            if (e.getBucket() == Material.WATER_BUCKET) {
                e.getPlayer().setFallDistance(0);
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.GREEN + "Success!");
                e.getPlayer().setItemInHand(Cache_BW1058.getMLGCache(e.getPlayer()));
                Cache_BW1058.removeMLGCache(e.getPlayer());
            }
        }
    }
}
