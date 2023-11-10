package me.zuyte.admin.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ExtraUtils {
    public static List<Block> getRadiusBlocks(Location location, int radius, boolean hollow) {
        List<Block> blocks = new ArrayList<>();
        int bX = location.getBlockX();
        int bY = location.getBlockY();
        int bZ = location.getBlockZ();
        for (int x = bX - radius; x <= bX + radius; x++) {
            for (int y = bY - radius; y <= bY + radius; y++) {
                for (int z = bZ - radius; z <= bZ + radius; z++) {
                    double distance = ((bX - x) * (bX - x) + (bY - y) * (bY - y) + (bZ - z) * (bZ - z));
                    if (distance < (radius * radius) && (!hollow || distance >= ((radius - 1) * (radius - 1)))) {
                        Location Blocklocation = new Location(location.getWorld(), x, y, z);
                        if (Blocklocation.getBlock().getType() != Material.BARRIER)
                            blocks.add(Blocklocation.getBlock());
                    }
                }
            }
        }
        return blocks;
    }
    public static void launchBlock(Block b) {
        FallingBlock fb;
        if (b == null) return;
        fb = b.getWorld().spawnFallingBlock(b.getLocation().add(0.0D, 1.0D, 0.0D), b.getType(), b.getData());
        fb.setDropItem(false);
        b.setType(Material.AIR);
        float x = -1.0F + (float)(Math.random() * 3.0D);
        float y = 0.5F;
        float z = -0.3F + (float)(Math.random() * 1.6D);
        fb.setVelocity(new Vector(x, y, z));
    }
}
