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

        if (cmd.getName().equalsIgnoreCase("captcha")) {
            Player player = (Player) sender;
            sender.sendMessage("The New Commaand is /password set <password>");
            sender.sendMessage("                     /password verify <password>");
            sender.sendMessage("                     /password reset <old-password> <new-password>");

        }



        if (cmd.getName().equalsIgnoreCase("password")) {
            Player player = (Player) sender;
            if (args.length == 0) {
                sender.sendMessage("The usage is /password set <password>");
                sender.sendMessage("                     /password verify <password>");
                sender.sendMessage("                     /password reset <old-password> <new-password>");
                return true;
            }
            String key = args[0];

            switch (key){
                case "set":
                    if (args.length < 2) {
                        sender.sendMessage("The usage is /password set <password>");
                        return true;
                    }
                    if (CaptchaManager.hasCaptcha(player)) {
                        player.sendMessage(ChatColor.RED + "You already have a password.");
                        player.sendMessage(ChatColor.RED + "Run /password reset <old-password> <new-password>");
                        return true;
                    }
                    String password = args[1];
                    if (password.length() < 5) {
                        player.sendMessage(ChatColor.RED + "Password must be at least 5 characters long.");
                        return true;
                    }

                    boolean hasLetter = false;
                    boolean hasNumber = false;

                    for (char c : password.toCharArray()) {
                        if (Character.isLetter(c)) {
                            hasLetter = true;
                        } else if (Character.isDigit(c)) {
                            hasNumber = true;
                        }
                    }

                    if (!hasLetter || !hasNumber) {
                        player.sendMessage(ChatColor.RED + "Password must contain both letters and numbers.");
                        return true;
                    }

                    // Captcha is valid, store it in the CaptchaManager
                    captchaManager.setPlayerCaptcha(player, password);
                    player.sendMessage(ChatColor.GREEN + "Password set successfully!");
                    return true;
                    case "verify":
                        if (args.length < 2) {
                            sender.sendMessage("The usage is /password verify <password>");
                            return true;
                        }
                        password = args[1];
                        if (!captchaManager.hasCaptcha(player)) {
                            player.sendMessage(ChatColor.RED + "Mo Password is not set.");
                            return true;
                        }
                        if (!captchaManager.verifyCaptcha(player, password)) {
                            player.sendMessage(ChatColor.RED + "Password Incorrect.");
                            return true;
                        }else {
                            LoginManager.setSuspiciousLogin(player.getName(),false);
                            KnightSmp.playerDataManager.setLastIp(player, player.getAddress().getAddress().getHostAddress());
                            player.sendMessage(ChatColor.GREEN + "Password Verified!");
                            return true;
                        }
                       case "reset":
                        if (args.length < 3) {
                            sender.sendMessage("The usage is /password reset <old-password> <new-password>");
                            return true;
                        }
                        password = args[1];
                        String newPassword = args[2];
                        if (!captchaManager.hasCaptcha(player)) {
                            player.sendMessage(ChatColor.RED + "Mo Password is not set.");
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
        }


        return false;
    }


}