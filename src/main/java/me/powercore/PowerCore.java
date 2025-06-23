package me.powercore;

import org.bukkit.plugin.java.JavaPlugin;
import me.powercore.commands.PvpCommand;
import me.powercore.commands.RecipeCommand;
import me.powercore.commands.ReviveCommand;
import me.powercore.commands.WithdrawCommand;
import me.powercore.listeners.LifestealListener;
import me.powercore.listeners.PlayerJoinListener;
import me.powercore.utils.RecipeManager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PowerCore extends JavaPlugin {

    private RecipeManager recipeManager;
    private final Set<UUID> bannedPlayers = new HashSet<>();

    @Override
    public void onEnable() {
        getLogger().info("PowerCore has been enabled!");
        this.saveDefaultConfig();
        recipeManager = new RecipeManager(getConfig());
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        getLogger().info("PowerCore has been disabled!");
    }

    public Set<UUID> getBannedPlayers() {
        return bannedPlayers;
    }

    public RecipeManager getRecipeManager() {
        return recipeManager;
    }

    private void registerCommands() {
        getCommand("pvp").setExecutor(new PvpCommand());
        getCommand("recipe").setExecutor(new RecipeCommand(recipeManager));
        getCommand("revive").setExecutor(new ReviveCommand(this));
        getCommand("withdraw").setExecutor(new WithdrawCommand(this));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new LifestealListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);