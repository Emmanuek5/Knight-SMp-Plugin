package com.obsidian.knightsmp.commands;

import com.obsidian.knightsmp.KnightSmp;
import com.obsidian.knightsmp.PlayerSecurity.CaptchaManager;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static com.obsidian.knightsmp.KnightSmp.playerDataManager;

public class ConsoleCommands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(command.getName().equalsIgnoreCase("reset-player-password")) {
            if (strings.length < 1) {
                commandSender.sendMessage("The usage is /reset-player-password <player>");
                return true;
            }
            String player = strings[0];
            OfflinePlayer offlinePlayer = playerDataManager.getOfflinePlayer(player);
            if (offlinePlayer != null) {
                KnightSmp.sendMessage(offlinePlayer.getUniqueId().toString());
                playerDataManager.setCaptcha(offlinePlayer.getUniqueId(), "");
               commandSender.sendMessage(ChatColor.GREEN + "Password reset successfully!");
                return true;
            }
            commandSender.sendMessage(ChatColor.RED + "Player not found!");
            return true;
        }


        return false;
    }
}
