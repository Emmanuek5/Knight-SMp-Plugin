package com.obsidian.knightsmp.PlayerSecurity;

import com.obsidian.knightsmp.KnightSmp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;

import static com.obsidian.knightsmp.KnightSmp.captchaManager;
import static com.obsidian.knightsmp.KnightSmp.playerDataManager;

public class LoginManager implements Listener {

    private static Map<String, Boolean> isPlayerSuspiciousMap = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerIP = player.getAddress().getAddress().getHostAddress();
        String lastIP = playerDataManager.getLastIp(player);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer != player) {
                String onlinePlayerIP = onlinePlayer.getAddress().getAddress().getHostAddress();
                if (playerIP.equals(onlinePlayerIP)) {
                    // Kick the newly joined player
                    player.kickPlayer(ChatColor.RED + "Another player with the same IP is already online.");
                    return;
                }
            }
        }

        // Check if the player's IP has changed
        if (!playerIP.equals(lastIP) && !lastIP.equals("")) {
            // Check for players with the same IP
            if (captchaManager.hasCaptcha(player)) {
                isPlayerSuspiciousMap.put(player.getName(), true);
                player.sendMessage(ChatColor.RED + "Your IP has been changed!");
                player.sendMessage(ChatColor.RED + "We've detected a suspicious login attempt!");
                player.sendMessage(ChatColor.RED + "Run /password verify <password> to login again");
            } else {
                player.sendMessage("Run Command /password set <password> to secure your account");
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (isSuspiciousLogin(player.getName()) ) {
           player.sendMessage(ChatColor.RED+"=============================================");
           player.sendMessage(ChatColor.RED+"We've detected a suspicious login attempt!");
           player.sendMessage(ChatColor.RED+"Run /password verify <password> to login again");
           event.setCancelled(true);
        }
    }

    public static boolean isSuspiciousLogin(String playerName) {
        return isPlayerSuspiciousMap.getOrDefault(playerName, false);
    }

    public static void setSuspiciousLogin(String playerName, boolean value) {
        isPlayerSuspiciousMap.put(playerName, value);
    }
}
