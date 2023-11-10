package me.zuyte.admin.subcommand.bw1058;

import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import me.zuyte.admin.util.TextUtils;
import net.md_5.bungee.api.chat.*;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class AdminBWSubCommand extends SubCommand {

    public AdminBWSubCommand(ParentCommand parent, String name) {
        super(parent, name);
        showInList(true);
        setPriority(20);
        setArenaSetupCommand(false);
        setDisplayInfo(textComponentBuilder(TextUtils.getColoredString(" &6▪ &7/bw admin &8- &eTo see command list")));
    }

    @Override
    public boolean execute(String[] args, CommandSender sender) {
        if (!sender.hasPermission("bw.admin")) {
            return false;
        }
        new HelpSubCommand(sender);
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return Collections.emptyList();
    }

    public TextComponent textComponentBuilder(String s) {
        TextComponent textComponent = new TextComponent(s);
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/bw " + getSubCommandName()));
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Sends admin commands list").create()));
        return textComponent;
    }
}