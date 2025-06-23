package me.powercore;

import org.bukkit.plugin.java.JavaPlugin;
import me.powercore.commands.PvpCommand;
import me.powercore.commands.RecipeCommand;
import me.powercore.commands.ReviveCommand;
import me.powercore.commands.WithdrawCommand;
import me.powercore.listeners.LifestealListener;
import me.powercore.listeners.PlayerJoinListener;

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
        getCommand("pvp").setExecutor(new PvpCommand(this));
        getCommand("recipe").setExecutor(new RecipeCommand(this));
        getCommand("revive").setExecutor(new ReviveCommand(this));
        getCommand("withdraw").setExecutor(new WithdrawCommand(this));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new LifestealListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
    }
}