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

   



    public static boolean isSuspiciousLogin(String playerName) {
        return isPlayerSuspiciousMap.getOrDefault(playerName, false);
    }

    public static void setSuspiciousLogin(String playerName, boolean value) {
        isPlayerSuspiciousMap.put(playerName, value);
    }
}
