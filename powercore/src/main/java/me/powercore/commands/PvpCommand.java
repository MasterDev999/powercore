package me.powercore.commands;

import me.powercore.PowerCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PvpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("powercore.pvp.toggle")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /pvp <enable|disable>");
            return true;
        }
        String arg = args[0].toLowerCase();
        if (arg.equals("enable")) {
            PowerCore.getInstance().setPvpEnabled(true);
            sender.sendMessage(ChatColor.GREEN + "PvP enabled.");
        } else if (arg.equals("disable")) {
            PowerCore.getInstance().setPvpEnabled(false);
            sender.sendMessage(ChatColor.RED + "PvP disabled.");
        } else {
            sender.sendMessage(ChatColor.RED + "Usage: /pvp <enable|disable>");
        }
        return true;
    }
}
