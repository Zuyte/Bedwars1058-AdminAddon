package me.zuyte.admin.support;

import com.tomkeuper.bedwars.api.BedWars;
import com.tomkeuper.bedwars.api.addon.Addon;
import com.tomkeuper.bedwars.api.language.Language;
import me.zuyte.admin.Admin;
import me.zuyte.admin.commands.bw2023.AdminCommand;
import me.zuyte.admin.commands.bw2023.AdminTabComplete;
import me.zuyte.admin.listeners.bw2023.*;
import me.zuyte.admin.storage.Messages;
import me.zuyte.admin.subcommands.bw2023.AdminBWSubCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BW2023 extends Addon implements IBedWars {
    private final Admin instance;
    public BW2023(Admin instance) {
        this.instance = instance;
        instance.bw2023 = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
        instance.bw2023.getAddonsUtil().registerAddon(this);
    }
    @Override
    public void setupMessages() {
        for (Language lang : Language.getLanguages())
            Messages.setupBedWarsMessages(lang.getYml());
    }

    @Override
    public void setupCommands() {
        new AdminBWSubCommand(instance.bw2023.getBedWarsCommand(), "admin");
        instance.getCommand("bwa").setExecutor(new AdminCommand());
        instance.getCommand("bwa").setTabCompleter(new AdminTabComplete());
    }

    @Override
    public void initializeListeners() {
        instance.getServer().getPluginManager().registerEvents(new ArenaListener(instance), instance);
        instance.getServer().getPluginManager().registerEvents(new PlayerListener(instance) , instance);
        instance.getServer().getPluginManager().registerEvents(new MenuListener(instance) , instance);
        instance.getServer().getPluginManager().registerEvents(new TeamAssignerListener(instance), instance);

        if (Bukkit.getPluginManager().getPlugin("BedWars2023-TeamSelector") != null) { // Trying to hook into TeamSelector Addon
            instance.getLogger().info("Found BedWars2023-TeamSelector, Hooking into it...");
            instance.getServer().getPluginManager().registerEvents(new TeamSelectorListener(instance), instance);
        }
    }

    // BW2023 API Methods
    @Override
    public String getAuthor() {
        return instance.getDescription().getAuthors().get(0);
    }

    @Override
    public Plugin getPlugin() {
        return instance;
    }

    @Override
    public String getVersion() {
        return instance.getDescription().getVersion();
    }

    @Override
    public String getName() {
        return "AdminAddon";
    }

    @Override
    public String getDescription() {
        return instance.getDescription().getDescription();
    }

    @Override
    public void load() {
        Bukkit.getPluginManager().enablePlugin(instance);
    }

    @Override
    public void unload() {
        Bukkit.getPluginManager().disablePlugin(instance);
    }
}
