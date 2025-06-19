package me.powercore.listeners;

import me.powercore.PowerCore;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        double defaultMaxHealth = PowerCore.getInstance().getConfig().getDouble("lifesteal.default_max_health", 20.0);
        if (event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() < defaultMaxHealth) {
            event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(defaultMaxHealth);
            event.getPlayer().sendMessage(ChatColor.GREEN + "Your max health has been reset to default.");
        }
    }
}
