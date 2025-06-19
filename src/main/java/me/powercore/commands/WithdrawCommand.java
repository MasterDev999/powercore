package me.powercore.commands;

import me.powercore.PowerCore;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WithdrawCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return true;
        }
        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Usage: /withdraw <amount>");
            return true;
        }
        int amount;
        try {
            amount = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "Invalid number.");
            return true;
        }
        if (amount <= 0) {
            player.sendMessage(ChatColor.RED + "Amount must be positive.");
            return true;
        }

        double currentHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        double minHealth = PowerCore.getInstance().getConfig().getDouble("lifesteal.min_health", 2.0);

        if (currentHealth - amount * 2.0 < minHealth) {
            player.sendMessage(ChatColor.RED + "You don't have enough hearts to withdraw that amount.");
            return true;
        }

        // Reduce max health
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(currentHealth - amount * 2.0);

        // Create heart item
        ItemStack heartItem = new ItemStack(Material.NETHER_STAR); // Example item
        ItemMeta meta = heartItem.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Heart");
        heartItem.setItemMeta(meta);
        heartItem.setAmount(amount);

        // Drop item at player's location
        player.getWorld().dropItemNaturally(player.getLocation(), heartItem);

        player.sendMessage(ChatColor.GREEN + "You withdrew " + amount + " heart(s).");
        return true;
    }
}
