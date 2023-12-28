package me.zuyte.admin;

import me.zuyte.admin.support.*;
import me.zuyte.admin.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Admin extends JavaPlugin {

    private static Admin instance;
    public boolean isBedWarsProxy = false;
    public com.andrei1058.bedwars.api.BedWars bw1058;
    public com.tomkeuper.bedwars.api.BedWars bw2023;
    public com.tomkeuper.bedwars.proxy.api.BedWars bwProxy2023;
    @Override
    public void onEnable() {
        instance = this;
        IBedWars bwSupport;

        if (Bukkit.getPluginManager().getPlugin("BedWars1058") != null)
            bwSupport = new BW1058(this);
        else if (Bukkit.getPluginManager().getPlugin("BedWars2023") != null)
            bwSupport = new BW2023(this);
        else if (Bukkit.getPluginManager().getPlugin("BedWarsProxy") != null)
            bwSupport = new BWProxy(this);
        else if (Bukkit.getPluginManager().getPlugin("BWProxy2023") != null)
            bwSupport = new BWProxy2023(this);
        else {
            getLogger().severe("Couldn't find a bedwars supported plugin. Disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // setup support
        bwSupport.setupMessages();
        bwSupport.setupCommands();
        bwSupport.initializeListeners();
        getServer().getConsoleSender().sendMessage(TextUtils.getColoredString("&aRunning BedWars-AdminAddon &fv" + getDescription().getVersion() + " &a(Type: " + (isBedWarsProxy ? "&cBedWarsProxy" : "&cBedWars") + "&a) &7- &eBy Zuyte"));
    }

    public static Admin getInstance(){
        return instance;
    }
}