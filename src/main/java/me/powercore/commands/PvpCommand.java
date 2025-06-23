package me.powercore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PvpCommand implements CommandExecutor {

    private boolean pvpEnabled = true; // Default PvP status

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;
        pvpEnabled = !pvpEnabled; // Toggle PvP status

        String statusMessage = pvpEnabled ? "PvP is now enabled." : "PvP is now disabled.";
        Bukkit.broadcastMessage(player.getName() + " has " + statusMessage);

        return true;
    }

    public boolean isPvpEnabled() {
        return pvpEnabled;
    }
}