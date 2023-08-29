package com.obsidian.knightsmp.commands;

import com.obsidian.knightsmp.KnightSmp;
import com.obsidian.knightsmp.PlayerSecurity.CaptchaManager;
import com.obsidian.knightsmp.PlayerSecurity.LoginManager;
import com.obsidian.knightsmp.utils.PlayerDataManager;
import com.obsidian.knightsmp.utils.PowerSlotsGUI;
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

        if (cmd.getName().equalsIgnoreCase("register")) {
            Player player = (Player) sender;
            if (args.length < 1) {
                sender.sendMessage("The usage is /register <password> <verify-password>");
                return true;
            }
            String password = args[0];
            String verifyPassword = args[1];
            if (verifyPassword == null){
                player.sendMessage(ChatColor.RED + "Usage: /register <password> <verify-password>");
            }
            if (!password.equals(verifyPassword)) {
                player.sendMessage(ChatColor.RED + "Passwordsts do not match.");
                return true;
            }
            if (captchaManager.hasCaptcha(player)) {
                player.sendMessage(ChatColor.RED + "You have already registered.");
                return true;
            }
            captchaManager.setPlayerCaptcha(player, password);
            player.sendMessage(ChatColor.GREEN + "Registered successfully!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("reset-password")){
            if (args.length < 1) {
                sender.sendMessage("The usage is /reset-password <old-password> <new-password>");
                return true;
            }
            Player player = (Player) sender;
            String password = args[0];
            String newPassword = args[1];
            if (!captchaManager.hasCaptcha(player)) {
                player.sendMessage(ChatColor.RED + "Password is not set.");
                return true;
            }
            if (!captchaManager.verifyCaptcha(player, password)) {
                player.sendMessage(ChatColor.RED + "Password Incorrect.");
                return true;
            }
            captchaManager.setPlayerCaptcha(player, newPassword);
            player.sendMessage(ChatColor.GREEN + "Password reset successfully!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("login")) {
            Player player = (Player) sender;
            String password = args[0];
            if (password == null){
                player.sendMessage(ChatColor.RED + "Usage: /login <password>");
            }
            if (!captchaManager.hasCaptcha(player)) {
                player.sendMessage(ChatColor.RED + "Password is not set.");
                return true;
            }
            if (!captchaManager.verifyCaptcha(player, password)) {
                player.sendMessage(ChatColor.RED + "Password Incorrect.");
                return true;
            }
            captchaManager.setPlayerCaptcha(player, password);
            LoginManager.setSuspiciousLogin(player.getName(), false);
            playerDataManager.setLastIp(player, player.getAddress().getAddress().getHostAddress());
            player.sendMessage(ChatColor.GREEN + "Logged in successfully!");
            return true;


            }





        return false;
    }


}