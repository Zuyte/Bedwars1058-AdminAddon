package me.zuyte.admin.commands.database;

import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Cache {
    public Map<String, String> temp = new HashMap<String, String>();
    public Map<String, ItemStack> mlg = new HashMap<String, ItemStack>();
    public Map<String, String> kaboom = new HashMap<String, String>();
    public Map<String, BlockFace> beds = new HashMap<String, BlockFace>();
}
