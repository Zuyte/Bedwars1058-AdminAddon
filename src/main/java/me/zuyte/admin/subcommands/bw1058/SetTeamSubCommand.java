package me.zuyte.admin.subcommands.bw1058;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import me.zuyte.admin.Admin;
import me.zuyte.admin.storage.Cache_BW1058;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class SetTeamSubCommand {

    BedWars.ArenaUtil arenaUtil = Admin.getInstance().bw1058.getArenaUtil();

    public SetTeamSubCommand(CommandSender commandSender, String[] args) {
        if (commandSender instanceof Player)
            player((Player) commandSender, args);
        else if (commandSender instanceof ConsoleCommandSender)
            console((ConsoleCommandSender) commandSender, args);
    }

    private void player(Player p, String[] args) {
        if (!p.hasPermission("bw.admin.setteam")) {
            p.sendMessage(ChatColor.RED + "You dont have permission to use this command.");
            return;
        }
        if (args.length > 1) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                p.sendMessage(ChatColor.RED + "Player not found");
                return;
            }
            if (arenaUtil.getArenaByPlayer(player) == null) {
                p.sendMessage(ChatColor.RED + "Player has not joined an arena");
                return;
            }
            if (arenaUtil.getArenaByPlayer(player).getStatus() == GameState.playing || arenaUtil.getArenaByPlayer(player).getStatus() == GameState.restarting) {
                p.sendMessage(ChatColor.RED + "Can't change player team after the game has been started");
                return;
            }
            IArena playerArena = arenaUtil.getArenaByPlayer(player);
            if (args.length >= 3) {
                String team = args[2].substring(0, 1).toUpperCase() + args[2].substring(1).toLowerCase();
                if (playerArena.getTeam(team) == null) {
                    p.sendMessage(ChatColor.RED + "Team not found");
                    return;
                }
                ITeam playerTeam = playerArena.getTeam(team);
                if (playerArena.getTeam(player) != null) {
                    playerArena.getTeam(player).getMembers().remove(player);
                }

                if (playerTeam.getMembers().size() == playerArena.getMaxInTeam()) {
                    p.sendMessage(ChatColor.RED + "Team is full!");
                    return;
                }
                Cache_BW1058.setPlayerTeam(player, playerTeam);
                playerTeam.addPlayers(player);
                p.sendMessage(ChatColor.GREEN + "Successfully set " + args[1] + "'s Team to " + playerTeam.getColor().chat() + playerTeam.getName());
                return;
            } else {
                if (playerArena.getTeam(player) != null) {
                    playerArena.getTeam(player).getMembers().remove(player);
                }
                setTeamGui(player, playerArena);
                return;
            }
        }
        p.sendMessage(ChatColor.RED + "Usage: /bw setteam <player> <team>");
    }

    private void console(ConsoleCommandSender c, String[] args) {
        if (args.length > 1) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                c.sendMessage(ChatColor.RED + "Player not found");
                return;
            }
            if (arenaUtil.getArenaByPlayer(player) == null) {
                c.sendMessage(ChatColor.RED + "Player has not joined an arena");
                return;
            }
            if (arenaUtil.getArenaByPlayer(player).getStatus() == GameState.playing || arenaUtil.getArenaByPlayer(player).getStatus() == GameState.restarting) {
                c.sendMessage(ChatColor.RED + "Can't change player team after the game has been started");
                return;
            }
            IArena playerArena = arenaUtil.getArenaByPlayer(player);
            if (args.length >= 3) {
                String team = args[2].substring(0, 1).toUpperCase() + args[2].substring(1).toLowerCase();
                if (playerArena.getTeam(team) == null) {
                    c.sendMessage(ChatColor.RED + "Team not found");
                    return;
                }
                ITeam playerTeam = playerArena.getTeam(team);
                if (playerArena.getTeam(player) != null) {
                    playerArena.getTeam(player).getMembers().remove(player);
                }

                if (playerTeam.getMembers().size() == playerArena.getMaxInTeam()) {
                    c.sendMessage(ChatColor.RED + "Team is full!");
                    return;
                }
                Cache_BW1058.setPlayerTeam(player, playerTeam);
                playerTeam.addPlayers(player);
                c.sendMessage(ChatColor.GREEN + "Successfully set " + args[1] + "'s Team to " + playerTeam.getColor().chat() + playerTeam.getName());
                return;
            } else {
                if (playerArena.getTeam(player) != null) {
                    playerArena.getTeam(player).getMembers().remove(player);
                }
                setTeamGui(player, playerArena);
                return;
            }
        }
        c.sendMessage(ChatColor.RED + "Usage: /bw setteam <player> <team>");
    }

    private void setTeamGui(Player player, IArena arena) {
        Inventory inventory = Bukkit.createInventory(player, 9, "Admin - Team Selector");
        for (int i = 0; i < arena.getTeams().size(); i++) {
            ITeam team = arena.getTeams().get(i);
            ItemStack itemStack = new ItemStack(Material.WOOL, 1, team.getColor().dye().getWoolData());
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(team.getColor().chat() + String.valueOf(ChatColor.BOLD) + team.getName());
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "•" + ChatColor.GREEN + " Click to select team!");
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            ItemStack newItemStack = Admin.getInstance().bw1058.getVersionSupport().addCustomData(itemStack, "bwa-team-selector");
            inventory.setItem(i, newItemStack);
        }
        player.openInventory(inventory);
    }
}