package me.zuyte.admin.support;

import com.andrei1058.bedwars.proxy.api.BedWars;
import com.andrei1058.bedwars.proxy.api.Language;
import com.andrei1058.bedwars.proxy.arenamanager.ArenaManager;
import com.andrei1058.bedwars.proxy.language.LanguageManager;
import me.zuyte.admin.Admin;
import me.zuyte.admin.commands.bwproxy.AdminProxyCommand;
import me.zuyte.admin.commands.bwproxy.AdminProxyTabComplete;
import me.zuyte.admin.storage.Messages;

public class BWProxy implements IBedWars {
    private final Admin instance;
    public BWProxy(Admin instance) {
        this.instance = instance;
        instance.isBedWarsProxy = true;
    }
    @Override
    public void setupMessages() {
        for (Language lang : LanguageManager.get().getLanguages())
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
