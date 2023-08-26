package me.zuyte.admin.listeners;

import me.zuyte.admin.Admin;
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
                if (e.getDamager().getCustomName() == ChatColor.RED + e.getEntity().getName() + "'s Enemy") {
                    e.setCancelled(false);
                }
            }
            if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                if (e.getDamager().getType() == EntityType.FIREBALL) {
                    if (Admin.getInstance().bw.getArenaUtil().getArenaByPlayer(((Player) e.getEntity()).getPlayer()).isReSpawning(((Player) e.getEntity()).getPlayer()) || Admin.getInstance().bw.getArenaUtil().getArenaByPlayer(((Player) e.getEntity()).getPlayer()).isSpectator(((Player) e.getEntity()).getPlayer())) return;
                    e.setCancelled(false);
                }
            }
        }
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (Admin.getInstance().kaboom.containsKey(e.getEntity().getName())) {
                    e.setCancelled(true);
                    Admin.getInstance().kaboom.remove(e.getEntity().getName());
                }
            }
        }
    }
    @EventHandler
    public void onInventoryInteract(PlayerInteractEvent e) {
        if (Admin.bw.getArenaUtil().isPlaying(e.getPlayer()) && !Admin.bw.getArenaUtil().isSpectating(e.getPlayer()) && !Admin.bw.getArenaUtil().getArenaByPlayer(e.getPlayer()).isRespawning(e.getPlayer())) {
            for (String s : Admin.getCfg().getYml().getStringList("settings.enable-crafting")) {
                if (Admin.bw.getArenaUtil().getArenaByPlayer(e.getPlayer()).getArenaName().equals(s)) {
                    if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
                    Block b = e.getClickedBlock();
                    if (b == null) return;
                    if (b.getType() == Admin.bw.getVersionSupport().materialCraftingTable()) {
                        e.setCancelled(false);
                    }
                }
            }
        }
    }

    @EventHandler
    public void ClickEvent(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            try {
                if (e.hasItem() == true) {
                    if (e.getItem().getType() == Material.STICK) {
                        if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Magic Toy Stick") && e.getPlayer().getItemInHand().containsEnchantment(Enchantment.LURE)) {
                            Block clickedBlock = e.getClickedBlock();
                            List<Block> blocks = getBlocks.put(clickedBlock.getLocation(), 4, false);
                            for (Block block : blocks) {
                                if (!block.getType().equals(Material.AIR) || !block.getType().equals(Material.BED_BLOCK)) {
                                    launchBlocks.put(block);
                                }
                                e.getPlayer().setItemInHand(new ItemStack(Material.AIR));
                                Location location = e.getPlayer().getLocation();
                                try {
                                    e.getPlayer().setVelocity(new Vector(0, 10, 0));
                                } catch (Exception ex) {
                                    Admin.getInstance().getLogger().severe("An exception occurred while setting player velocity.");
                                }

                                e.getPlayer().getWorld().createExplosion(location, -1, false);
                            }
                        }
                    }
                }
            } catch (NullPointerException ex) {
                // null
            }
        }
    }
    @EventHandler
    public void onWBDeath(PlayerDeathEvent e) {
        if (Admin.getInstance().mlg.containsKey(e.getEntity().getName())) {
            Admin.getInstance().mlg.remove(e.getEntity().getName());
            e.getEntity().sendMessage(ChatColor.RED + "Failed!");
        }
    }
    @EventHandler
    public void onWBPlace(PlayerBucketEmptyEvent e) {
        if (Admin.getInstance().mlg.containsKey(e.getPlayer().getName())) {
            if (e.getBucket() == Material.WATER_BUCKET) {
                e.getPlayer().setFallDistance(0);
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.GREEN + "Success!");
                e.getPlayer().setItemInHand(Admin.getInstance().mlg.get(e.getPlayer().getName()));
                Admin.getInstance().mlg.remove(e.getPlayer().getName());
            }
        }
    }
}
