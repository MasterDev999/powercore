package me.powercore;

import me.powercore.commands.PvpCommand;
import me.powercore.commands.WithdrawCommand;
import me.powercore.commands.ReviveCommand;
import me.powercore.commands.RecipeCommand;

import me.powercore.listeners.LifestealListener;
import me.powercore.listeners.PlayerJoinListener;

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

        // Load and register recipes
        recipeManager = new RecipeManager(this);
        recipeManager.loadRecipesFromConfig();
        recipeManager.registerRecipes();

        // Register commands
        getCommand("pvp").setExecutor(new PvpCommand());
        getCommand("withdraw").setExecutor(new WithdrawCommand());
        getCommand("revive").setExecutor(new ReviveCommand());
        getCommand("recipe").setExecutor(new RecipeCommand(recipeManager));

        // Register listeners
        Bukkit.getPluginManager().registerEvents(new LifestealListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);

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
        this.pvpEnabled = enabled;
        Bukkit.broadcastMessage(ChatColor.GOLD + "[PowerCore] PvP is now " +
                (enabled ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled"));
    }

    public RecipeManager getRecipeManager() {
        return recipeManager;
    }
}
