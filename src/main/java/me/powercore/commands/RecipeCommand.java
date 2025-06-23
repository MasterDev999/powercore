package me.powercore.commands;

import me.powercore.utils.RecipeManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RecipeCommand implements CommandExecutor {

    private final RecipeManager recipeManager;

    public RecipeCommand(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Usage: /recipe <key>");
            return true;
        }

        String key = args[0];
        if (recipeManager.isRecipeDefined(key)) {
            player.sendMessage(ChatColor.GREEN + "Recipe for " + key + ":");
            player.sendMessage(ChatColor.YELLOW + recipeManager.getRecipeShape(key));
            player.sendMessage(ChatColor.YELLOW + "Ingredients: " + recipeManager.getRecipeIngredients(key));
        } else {
            player.sendMessage(ChatColor.RED + "No recipe found for key: " + key);
        }

        return true;
    }
}