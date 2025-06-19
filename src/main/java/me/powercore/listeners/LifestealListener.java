package me.powercore.listeners;

import me.powercore.PowerCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class LifestealListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        if (killer == null || killer == victim) return;

        if (!PowerCore.getInstance().isPvpEnabled()) return;

        double stealAmount = PowerCore.getInstance().getConfig().getDouble("lifesteal.hearts_stolen", 1.0);
        double minHealth = PowerCore.getInstance().getConfig().getDouble("lifesteal.min_health", 2.0);
        double maxHealth = PowerCore.getInstance().getConfig().getDouble("lifesteal.max_health", 40.0);

        double killerHealth = killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        double victimHealth = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        // Victim loses hearts
        double newVictimHealth = Math.max(victimHealth - stealAmount * 2.0, minHealth);
        victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newVictimHealth);

        // Killer gains hearts
        double newKillerHealth = Math.min(killerHealth + stealAmount * 2.0, maxHealth);
        killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newKillerHealth);

        // Ban victim if at min health (0 hearts = 0 health)
        if (newVictimHealth <= minHealth) {
            Bukkit.getBanList(org.bukkit.BanList.Type.NAME).addBan(victim.getName(), "You reached 0 hearts.", null, null);
            victim.kickPlayer(ChatColor.RED + "You have been banned for reaching 0 hearts.");
        }

        String msg = PowerCore.getInstance().getConfig().getString("lifesteal.on_death_message", "&c{killer} stole a heart from {victim}!");
        msg = msg.replace("{killer}", killer.getName()).replace("{victim}", victim.getName());
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
}
