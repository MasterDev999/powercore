package me.powercore.commands;

import me.powercore.PowerCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReviveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("powercore.revive")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /revive <player>");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found or not online.");
            return true;
        }
        double maxHealth = PowerCore.getInstance().getConfig().getDouble("lifesteal.default_max_health", 20.0);
        target.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        target.sendMessage(ChatColor.GREEN + "You have been revived!");
        sender.sendMessage(ChatColor.GREEN + "Player " + target.getName() + " has been revived.");
        return true;
    }
}
