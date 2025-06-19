package me.powercore;

import me.powercore.utils.RecipeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class PowerCore extends JavaPlugin {
    private static PowerCore instance;
    private boolean pvpEnabled = true;
    private RecipeManager recipeManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        recipeManager = new RecipeManager(this);
        recipeManager.loadRecipesFromConfig();
        recipeManager.registerRecipes();

        // Commands
        getCommand("pvp").setExecutor(new commands.PvpCommand());
        getCommand("withdraw").setExecutor(new commands.WithdrawCommand());
        getCommand("revive").setExecutor(new commands.ReviveCommand());
        getCommand("recipe").setExecutor(new commands.RecipeCommand(recipeManager));

        // Listeners
        getServer().getPluginManager().registerEvents(new listeners.LifestealListener(), this);
        getServer().getPluginManager().registerEvents(new listeners.PlayerJoinListener(), this);

        getLogger().info(ChatColor.GREEN + "PowerCore enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED + "PowerCore disabled!");
    }

    public static PowerCore getInstance() {
        return instance;
    }

    public boolean isPvpEnabled() {
        return pvpEnabled;
    }

    public void setPvpEnabled(boolean enabled) {
        pvpEnabled = enabled;
        Bukkit.broadcastMessage(ChatColor.GOLD + "[PowerCore] PvP is now " + (enabled ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled"));
    }
}
