package me.powercore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class LifestealListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        int currentHearts = player.getHealth() <= 0 ? 0 : (int) (player.getHealth() / 2);
        
        // Reduce max health by 1 heart (2 health points)
        if (currentHearts > 0) {
            player.setMaxHealth(player.getMaxHealth() - 2);
            player.setHealth(Math.max(0, player.getHealth() - 2));
            player.sendMessage(ChatColor.RED + "You have lost a heart on death!");
        } else {
            // Ban the player if they reach 0 hearts
            Bukkit.getBanList(org.bukkit.BanList.Type.NAME).addBan(player.getName(), "You have reached 0 hearts and are banned.", null, null);
            player.kickPlayer(ChatColor.RED + "You have been banned for reaching 0 hearts!");
        }
    }
}