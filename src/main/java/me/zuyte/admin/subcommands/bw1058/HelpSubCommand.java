package me.zuyte.admin.subcommands.bw1058;

import com.google.common.base.Joiner;
import me.zuyte.admin.Admin;
import me.zuyte.admin.utils.TextUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HelpSubCommand {
    public HelpSubCommand(CommandSender commandSender) {
        sendMessage(commandSender);
    }

    private void sendMessage(CommandSender c) {
        if (!c.hasPermission("bw.admin")) {
            c.sendMessage(ChatColor.RED + "You dont have permission to use this command.");
            return;
        }
        List<String> helpMessage = new ArrayList<>(Collections.emptyList());
        helpMessage.add(TextUtils.getColoredString("&r\n&8&lþ &6BedWars1058 Admin v" + Admin.getInstance().getDescription().getVersion() + " &7- &cCommands"));
        helpMessage.add(TextUtils.getColoredString("&r "));
        helpMessage.add(TextUtils.getColoredString("&6• &7/bwa forcejoin <player> <arena/group> &8- &eForce join player into a arena"));
        helpMessage.add(TextUtils.getColoredString("&6• &7/bwa setteam <player> <team> &8- &eSet the player's team"));
        helpMessage.add(TextUtils.getColoredString("&6• &7/bwa setbed <player> <true/false> &8- &eSet a team's bed status"));
        helpMessage.add(TextUtils.getColoredString("&6• &7/bwa revive <player> <final/bed> &8- &eRevive a player"));
        helpMessage.add(TextUtils.getColoredString("&6• &7/bwa nextevent <arena> <event> &8- &eSet an arena's next event"));
        helpMessage.add(TextUtils.getColoredString("&6• &7/bwa skipevent <arena> &8- &eSkip upcoming event"));
        helpMessage.add(TextUtils.getColoredString("&r "));
        helpMessage.add(TextUtils.getColoredString("&6• &7/bwa troll mlg <player> &8- &eMakes the player complete an mlg"));
        helpMessage.add(TextUtils.getColoredString("&6• &7/bwa troll cage <player> &8- &eTraps the player in a breakable glass cage"));
        helpMessage.add(TextUtils.getColoredString("&6• &7/bwa troll blind <player> <seconds> &8- &eBlinds the player"));
        helpMessage.add(TextUtils.getColoredString("&6• &7/bwa troll slowhands <player> <seconds> &8- &eSlows mining speed of the player"));
        helpMessage.add(TextUtils.getColoredString("&6• &7/bwa troll kaboom <player> &8- &eLaunched player in the air"));
        helpMessage.add(TextUtils.getColoredString("&6• &7/bwa troll toystick <player> &8- &eGives the player an explosive toystick"));
        helpMessage.add(TextUtils.getColoredString("&6• &7/bwa troll ghast <player> &8- &eSpawns ghast at the player"));
        helpMessage.add(TextUtils.getColoredString("&6• &7/bwa troll mobattack <player> &8- &eSpawns Zombies & Skeletons at the player"));

        c.sendMessage(Joiner.on("\n").join(helpMessage));
    }
}
