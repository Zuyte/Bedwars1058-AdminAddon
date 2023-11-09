package me.zuyte.admin.support;

import com.andrei1058.bedwars.proxy.api.BedWars;
import com.andrei1058.bedwars.proxy.api.Language;
import me.zuyte.admin.Admin;
import me.zuyte.admin.command.proxy.AdminProxyCommand;
import me.zuyte.admin.command.proxy.AdminProxyTabComplete;
import me.zuyte.admin.storage.Messages;
import org.bukkit.Bukkit;

public class BWProxy implements IBedWars {
    private final Admin instance;
    public BWProxy(Admin instance) {
        this.instance = instance;
        instance.isBedWarsProxy = true;
        instance.bwProxy = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
    }
    @Override
    public void setupMessages() {
        for (Language lang : instance.bwProxy.getLanguageUtil().getLanguages())
            Messages.setupProxyMessages(lang);
    }

    @Override
    public void setupCommands() {
        instance.getCommand("bwa").setExecutor(new AdminProxyCommand());
        instance.getCommand("bwa").setTabCompleter(new AdminProxyTabComplete());
    }

    @Override
    public void initializeListeners() {
        // None right now
    }
}
