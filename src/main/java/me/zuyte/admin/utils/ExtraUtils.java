package me.zuyte.admin.utils;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import me.zuyte.admin.storage.Cache_BW1058;
import me.zuyte.admin.storage.Cache_BW2023;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ExtraUtils {
    public static final int MAIN_VERSION = Integer.parseInt(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].split("_")[1]);
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
        float x = -1.0F + (float) (Math.random() * 3.0D);
        float y = 0.5F;
        float z = -0.3F + (float) (Math.random() * 1.6D);
        fb.setVelocity(new Vector(x, y, z));
    }

    public static void placeBed_BW1058(ITeam playerTeam) {
        try {
            BlockFace face = Cache_BW1058.getArenaBedsCache(playerTeam);
            if (ExtraUtils.MAIN_VERSION < 13) {
                if (face == null) {
                    face = BlockFace.SELF;
                }
                BlockState bedFoot = playerTeam.getBed().getBlock().getState();
                BlockState bedHead = bedFoot.getBlock().getRelative(face.getOppositeFace()).getState();
                bedFoot.setType(Material.getMaterial("BED_BLOCK"));
                bedHead.setType(Material.getMaterial("BED_BLOCK"));
                bedFoot.setRawData((byte) face.ordinal());
                bedHead.setRawData((byte) (face.ordinal() + 8));
                bedFoot.update(true, false);
                bedHead.update(true, true);
            } else {
                if (face == null) {
                    face = BlockFace.EAST;
                }
                Block bedBlock = playerTeam.getBed().getBlock();
                Block relativeBlock = bedBlock.getRelative(face);
                relativeBlock.setType(playerTeam.getColor().bedMaterial());
                Object bedHeadO = relativeBlock.getClass().getMethod("getBlockData").invoke(relativeBlock);
                Bed bedHead = (Bed) bedHeadO;
                bedHead.setPart(Bed.Part.HEAD);
                bedHead.setFacing(face);
                relativeBlock.getClass().getMethod("setBlockData", BlockData.class).invoke(relativeBlock, bedHead);
                bedBlock.setType(playerTeam.getColor().bedMaterial());
                Object bedFootO = bedBlock.getClass().getMethod("getBlockData").invoke(bedBlock);
                Bed bedFoot = (Bed) bedFootO;
                bedFoot.setPart(Bed.Part.FOOT);
                bedFoot.setFacing(face);
                bedBlock.getClass().getMethod("setBlockData", BlockData.class).invoke(bedBlock, bedFoot);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void placeBed_BW2023(com.tomkeuper.bedwars.api.arena.team.ITeam playerTeam) {
//        BlockFace face = Cache_BW2023.getArenaBedsCache(playerTeam);
//        if (face == null) {
//            face = BlockFace.SELF;
//        }
//        if (ExtraUtils.MAIN_VERSION < 13) {
//            BlockState bedFoot = playerTeam.getBed().getBlock().getState();
//            BlockState bedHead = bedFoot.getBlock().getRelative(face.getOppositeFace()).getState();
//            bedFoot.setType(Material.getMaterial("BED_BLOCK"));
//            bedHead.setType(Material.getMaterial("BED_BLOCK"));
//            bedFoot.setRawData((byte) face.ordinal());
//            bedHead.setRawData((byte) (face.ordinal() + 8));
//            bedFoot.update(true, false);
//            bedHead.update(true, true);
//        } else {
//            bedFoot.setType(playerTeam.getColor().bedMaterial());
//            bedHead.setType(playerTeam.getColor().bedMaterial());
//        }
    }

    public static void debug(String message) {
        Bukkit.getLogger().severe("[DEBUG] " + message);
    }
}
