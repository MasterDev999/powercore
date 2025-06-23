package me.powercore;

import org.bukkit.plugin.java.JavaPlugin;

public class PowerCore extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("PowerCore has been enabled!");
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("PowerCore has been disabled!");
    }

    private void registerCommands() {
        // Register commands here
        getCommand("pvp").setExecutor(new commands.PvpCommand());
        getCommand("recipe").setExecutor(new commands.RecipeCommand());
        getCommand("revive").setExecutor(new commands.ReviveCommand());
        getCommand("withdraw").setExecutor(new commands.WithdrawCommand());
    }

    private void registerListeners() {
        // Register event listeners here
        getServer().getPluginManager().registerEvents(new listeners.LifestealListener(), this);
        getServer().getPluginManager().registerEvents(new listeners.PlayerJoinListener(), this);
    }
}