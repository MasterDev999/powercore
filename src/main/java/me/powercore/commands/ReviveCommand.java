package me.powercore.commands;

import me.powercore.PowerCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class ReviveCommand implements CommandExecutor {

    private final PowerCore plugin;

    public ReviveCommand(PowerCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        if (!sender.hasPermission("powercore.revive")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /revive <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !plugin.getBannedPlayers().contains(target.getUniqueId())) {
            sender.sendMessage(ChatColor.RED + "Player not found or is not banned.");
            return true;
        }

        // Revive the player
        target.setHealth(20.0); // Set health back to normal
        plugin.getBannedPlayers().remove(target.getUniqueId()); // Unban the player
        sender.sendMessage(ChatColor.GREEN + "You have revived " + target.getName() + ".");
        target.sendMessage(ChatColor.GREEN + "You have been revived by " + sender.getName() + ".");

        return true;
    }
}