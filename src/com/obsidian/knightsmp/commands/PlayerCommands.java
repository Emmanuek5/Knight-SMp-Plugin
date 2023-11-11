package com.obsidian.knightsmp.commands;

import com.obsidian.knightsmp.PlayerSecurity.LoginManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.obsidian.knightsmp.KnightSmp.captchaManager;
import static com.obsidian.knightsmp.KnightSmp.playerDataManager;

public class PlayerCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return true; // Return true to indicate that the command was handled
        }

        Player player = (Player) sender;
        String commandName = cmd.getName();

        if (commandName.equalsIgnoreCase("turn-off-powers")) {
            playerDataManager.setUsePowers(player, false);
            player.sendMessage(ChatColor.GREEN + "Turned off powers!");
            return true;
        }

        if (commandName.equalsIgnoreCase("turn-on-powers")) {
            playerDataManager.setUsePowers(player, true);
            player.sendMessage(ChatColor.GREEN + "Turned on powers!");
            return true;
        }



        return false;
    }


}