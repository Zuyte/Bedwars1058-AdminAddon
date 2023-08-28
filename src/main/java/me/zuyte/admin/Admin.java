package me.zuyte.admin;

import com.andrei1058.bedwars.api.BedWars;
import me.zuyte.admin.commands.AdminCommand;
import me.zuyte.admin.commands.AdminTabComplete;
import me.zuyte.admin.commands.proxy.AdminProxyTabComplete;
import me.zuyte.admin.commands.subcommands.AdminBWSubCommand;
import me.zuyte.admin.commands.proxy.AdminProxyCommand;
import me.zuyte.admin.listeners.ArenaListener;
import me.zuyte.admin.listeners.MenuListener;
import me.zuyte.admin.listeners.TeamAssignerListener;
import me.zuyte.admin.listeners.TeamSelectorListener;
import me.zuyte.admin.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Admin extends JavaPlugin {

    private static Admin instance;
    private boolean isBedWarsProxy = false;
    public BedWars bw;
    @Override
    public void onEnable() {
        instance = this;

        if (Bukkit.getPluginManager().getPlugin("BedWars1058") != null)
            setupBedWars();
        else if (Bukkit.getPluginManager().getPlugin("BedWarsProxy") != null)
            setupProxy();
        else {
            getLogger().severe("BedWars1058 or BedWarsProxy was not found. Disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        getLogger().info(TextUtils.getColoredString("&aRunning BedWars1058-AdminAddon &fv" + getDescription().getVersion() + " (Type: " + (isBedWarsProxy ? "BedWarsProxy" : "BedWars") + ") &7- &eBy Zuyte"));
    }

    private void setupBedWars() {
        bw = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
        new AdminBWSubCommand(bw.getBedWarsCommand(), "admin");
        getCommand("bwa").setExecutor(new AdminCommand());
        getCommand("bwa").setTabCompleter(new AdminTabComplete());
        getServer().getPluginManager().registerEvents(new ArenaListener(this), this);
        getServer().getPluginManager().registerEvents(new MenuListener(this) , this);
        getServer().getPluginManager().registerEvents(new TeamAssignerListener(this), this);
        if (Bukkit.getPluginManager().getPlugin("BedWars1058-TeamSelector") != null) {
            getLogger().info("Found BedWars1058-TeamSelector, Hooking into it...");
            getServer().getPluginManager().registerEvents(new TeamSelectorListener(this), this);
        }
    }

    private void setupProxy() {
        isBedWarsProxy = true;
        getCommand("bwa").setExecutor(new AdminProxyCommand());
        getCommand("bwa").setTabCompleter(new AdminProxyTabComplete());
    }

    public static Admin getInstance(){
        return instance;
    }
}