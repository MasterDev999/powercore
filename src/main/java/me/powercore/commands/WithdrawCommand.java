package me.powercore.commands;

import me.powercore.PowerCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;

public class WithdrawCommand implements CommandExecutor {

    private final PowerCore plugin;

    public WithdrawCommand(PowerCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        // Check if player has enough hearts
        int currentHearts = (int) (player.getHealth() / 2);
        if (currentHearts <= 1) {
            player.sendMessage(ChatColor.RED + "You need at least 2 hearts to withdraw.");
            return true;
        }

        // Withdraw a heart
        player.setHealth(player.getHealth() - 2); // Reduce health by 1 heart
        player.getInventory().addItem(createHeartItem()); // Add heart item to inventory
        player.sendMessage(ChatColor.GREEN + "You have successfully withdrawn a heart!");

        return true;
    }

    private ItemStack createHeartItem() {
        ItemStack heartItem = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta meta = heartItem.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Heart");
        meta.setLore(Arrays.asList(ChatColor.GRAY + "Right click to gain +1 heart"));
        heartItem.setItemMeta(meta);
        return heartItem;
    }
}