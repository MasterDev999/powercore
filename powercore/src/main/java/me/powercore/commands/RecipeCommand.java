package me.powercore.commands;

import me.powercore.utils.RecipeManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Optional;

public class RecipeCommand implements CommandExecutor {
    private final RecipeManager recipeManager;

    public RecipeCommand(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("powercore.recipe")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to view recipes.");
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /recipe <name>");
            sender.sendMessage(ChatColor.RED + "Available recipes: " + String.join(", ", recipeManager.getRecipeKeys()));
            return true;
        }
        String key = args[0].toLowerCase();
        Optional<String> chat = Optional.ofNullable(recipeManager.getRecipeChat(key));
        if (chat.isEmpty()) {
            sender.sendMessage(ChatColor.RED + "Recipe not found: " + key);
            return true;
        }
        sender.sendMessage(chat.get());
        return true;
    }
}
