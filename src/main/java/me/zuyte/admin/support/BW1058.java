package me.zuyte.admin.support;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.language.Language;
import me.zuyte.admin.Admin;
import me.zuyte.admin.command.bw1058.AdminCommand;
import me.zuyte.admin.command.bw1058.AdminTabComplete;
import me.zuyte.admin.command.bw1058.subcommand.AdminBWSubCommand;
import me.zuyte.admin.listener.bw1058.*;
import me.zuyte.admin.storage.Messages;
import org.bukkit.Bukkit;

public class BW1058 implements IBedWars {
    private final Admin instance;
    public BW1058(Admin instance) {
        this.instance = instance;
        instance.bw = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
    }
    @Override
    public void setupMessages() {
        for (Language lang : Language.getLanguages())
            Messages.setupBedWarsMessages(lang.getYml());
    }

    @Override
    public void setupCommands() {
        new AdminBWSubCommand(instance.bw.getBedWarsCommand(), "admin");
        instance.getCommand("bwa").setExecutor(new AdminCommand());
        instance.getCommand("bwa").setTabCompleter(new AdminTabComplete());
    }

    @Override
    public void initializeListeners() {
        instance.getServer().getPluginManager().registerEvents(new ArenaListener(instance), instance);
        instance.getServer().getPluginManager().registerEvents(new PlayerListener(instance) , instance);
        instance.getServer().getPluginManager().registerEvents(new MenuListener(instance) , instance);
        instance.getServer().getPluginManager().registerEvents(new TeamAssignerListener(instance), instance);

        if (Bukkit.getPluginManager().getPlugin("BedWars1058-TeamSelector") != null) { // Trying to hook into TeamSelector Addon
            instance.getLogger().info("Found BedWars1058-TeamSelector, Hooking into it...");
            instance.getServer().getPluginManager().registerEvents(new TeamSelectorListener(instance), instance);
        }
    }
}
