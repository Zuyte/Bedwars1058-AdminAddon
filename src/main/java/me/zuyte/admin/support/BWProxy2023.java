package me.zuyte.admin.support;

import com.tomkeuper.bedwars.proxy.api.BedWars;
import com.tomkeuper.bedwars.proxy.api.Language;
import me.zuyte.admin.Admin;
import me.zuyte.admin.commands.bwproxy2023.AdminProxyCommand;
import me.zuyte.admin.commands.bwproxy2023.AdminProxyTabComplete;
import me.zuyte.admin.storage.Messages;
import org.bukkit.Bukkit;

public class BWProxy2023 implements IBedWars{
    private final Admin instance;
    public BWProxy2023(Admin instance) {
        this.instance = instance;
        instance.isBedWarsProxy = true;
        instance.bwProxy2023 = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
    }
    @Override
    public void setupMessages() {
        for (Language lang : instance.bwProxy2023.getLanguageUtil().getLanguages())
            Messages.setupProxy2023Messages(lang);
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
