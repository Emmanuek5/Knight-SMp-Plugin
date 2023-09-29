package com.obsidian.knightsmp.commands;

import com.obsidian.knightsmp.Backups.BackUpManager;
import com.obsidian.knightsmp.Backups.ServerImporter;
import com.obsidian.knightsmp.Backups.VersionManager;
import com.obsidian.knightsmp.KnightSmp;
import com.obsidian.knightsmp.PlayerSecurity.CaptchaManager;
import com.obsidian.knightsmp.PlayerSecurity.LoginManager;
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
        if (command.getName().equalsIgnoreCase("reset-player-password")) {
            if (strings.length < 1) {
                commandSender.sendMessage("The usage is /reset-player-password <player>");
                return true;
            }
            String player = strings[0];
            OfflinePlayer offlinePlayer = playerDataManager.getOfflinePlayer(player);
            if (offlinePlayer != null) {
                KnightSmp.sendMessage(offlinePlayer.getUniqueId().toString());
                playerDataManager.setCaptcha(offlinePlayer.getUniqueId(), "");
                LoginManager.setSuspiciousLogin(offlinePlayer.getName(), false);
                commandSender.sendMessage(ChatColor.GREEN + "Password reset successfully!");
                return true;
            }
            commandSender.sendMessage(ChatColor.RED + "Player not found!");
            return true;
        } else if (command.getName().equalsIgnoreCase("download-latest-version")) {
            VersionManager.downloadLatestVersion();
            commandSender.sendMessage(ChatColor.GREEN + "Downloading latest version...");
            return true;
        } else if (command.getName().equalsIgnoreCase("import-server")) {
            ServerImporter serverImporter = new ServerImporter();
            commandSender.sendMessage(ChatColor.GREEN + "Importing server...");
            return true;
        } else if (command.getName().equalsIgnoreCase("backup-server")) {
            BackUpManager backUpManager = new BackUpManager();
            backUpManager.backup();
            commandSender.sendMessage(ChatColor.GREEN + "Backup complete!");
            return true;
        } else if (command.getName().equalsIgnoreCase("download-plugin")){
            if (strings.length <1){
                commandSender.sendMessage("Usage: /ddownload-plugin <url> <name>");
                return true;
            }
            String url = strings[0];
            String name = strings[1];
            VersionManager.downloadAndSavePlugin(url,name);
            commandSender.sendMessage(ChatColor.GREEN + "Downloading plugin...");
            return true;
        }else if (command.getName().equalsIgnoreCase("update-server-version")){
            VersionManager.manageVersion();
            commandSender.sendMessage(ChatColor.GREEN + "Updating server...");
            return true;
        }else if (command.getName().equalsIgnoreCase("unzip-file")) {
            if (strings.length < 1) {
                commandSender.sendMessage("Usage: /unzip-file <file>");
                return true;
            }
            String file = strings[0];
            BackUpManager.unZipFolder(file);
            commandSender.sendMessage(ChatColor.GREEN + "Unzipping file...");
            return true;
        }

        return false;
    }
}
