package com.obsidian.knightsmp.utils;

import com.obsidian.knightsmp.KnightSmp;
import com.obsidian.knightsmp.managers.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;

import static com.obsidian.knightsmp.KnightSmp.essentials;

public class PowerSlotsScoreboard {

    private final PlayerDataManager playerDataManager;
    private Scoreboard basicScoreboard;
    private Scoreboard powerSlotsScoreboard;
    private Objective basicObjective;
    private Objective powerSlotsObjective;
    private Player player;
    private String balance;

    public PowerSlotsScoreboard(Player player, PlayerDataManager playerDataManager) {
        this.player = player;
        this.playerDataManager = playerDataManager;
        this.basicScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.powerSlotsScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.basicObjective = basicScoreboard.registerNewObjective("basicInfo", "dummy", ChatColor.DARK_GRAY+ "Basic Info");
        this.powerSlotsObjective = powerSlotsScoreboard.registerNewObjective("powerSlots", "dummy", "Power Slots");
        this.basicObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.powerSlotsObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

        // Schedule scoreboard update task
        new BukkitRunnable() {
            boolean isBasicScoreboard = true;

            @Override
            public void run() {
                if (isBasicScoreboard) {
                    updateBasicScoreboard();
                } else {
                    updatePowerSlotsScoreboard();
                }
                isBasicScoreboard = !isBasicScoreboard;
            }
        }.runTaskTimer(KnightSmp.getPlugin(), 0, 100); // Change 100 to 100 ticks (5 seconds)
    }

    public void initScoreboard() {
        // Initialize the basic scoreboard for a new player
        balance = essentials.getUser(player).getMoney().toString();
        String playTime = String.valueOf(new Date( essentials.getUser(player).getLastOnlineActivity()).getTime());
        KnightSmp.getPlugin().getLogger().info(playTime);
        basicObjective.getScore(ChatColor.YELLOW +"Balance: " +ChatColor.DARK_GRAY+"$" + formatBalance(Double.parseDouble(balance))).setScore(0);
        basicObjective.getScore(ChatColor.BLUE+ "Name: " + player.getName()).setScore(1);
        player.setScoreboard(basicScoreboard);
    }

    public void updateBasicScoreboard() {
        basicObjective.setDisplayName(ChatColor.BOLD + "Basic Info");
        // Clear existing entries from the scoreboard
        for (String entry : basicScoreboard.getEntries()) {
            basicScoreboard.resetScores(entry);
        }
        // Update basic scoreboard content
        balance = essentials.getUser(player).getMoney().toString();

        basicObjective.getScore(ChatColor.YELLOW +"Balance: " +ChatColor.DARK_PURPLE+"$" + formatBalance(Double.parseDouble(balance))).setScore(0);
        basicObjective.getScore(ChatColor.BLUE+ "Name: " + player.getName()).setScore(1);
        player.setScoreboard(basicScoreboard);
    }

    public void updatePowerSlotsScoreboard() {
        powerSlotsObjective.setDisplayName(ChatColor.BOLD + "Power Slots");
        // Clear existing entries from the scoreboard
        for (String entry : powerSlotsScoreboard.getEntries()) {
            powerSlotsScoreboard.resetScores(entry);
        }
        // Update power slots scoreboard content
        String[] powerSlots = getPlayerPowerSlots(player);
        for (int i = 0; i < Math.min(10, powerSlots.length); i++) {
            Score score = powerSlotsObjective.getScore(getPowerSlotDisplayName(i));
            score.setScore(Math.min(9, powerSlots.length) - i);
        }
        player.setScoreboard(powerSlotsScoreboard);
    }


    public static String formatBalance(double balance) {
        if (balance >= 1_000_000_000_000L) {
            return roundUp(balance / 1_000_000_000_000L, 1) + "T";
        } else if (balance >= 1_000_000_000L) {
            return roundUp(balance / 1_000_000_000L, 1) + "B";
        } else if (balance >= 1_000_000) {
            return roundUp(balance / 1_000_000, 1) + "M";
        } else if (balance >= 1_000) {
            return roundUp(balance / 1_000, 1) + "K";
        } else {
            return String.valueOf(balance);
        }
    }

    private static double roundUp(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = (long) Math.ceil(value);
        return (double) tmp / factor;
    }




    private String[] getPlayerPowerSlots(Player player) {
        PlayerData playerData = playerDataManager.getPlayerData(player);
        if (playerData != null) {
            return playerData.getPowerSlots();
        }
        return new String[0]; // Return an empty array if no power slots data
    }

    private String getPowerSlotDisplayName(int slotIndex) {
        String[] powerSlots = playerDataManager.getPowerSlots(player);
        if (slotIndex >= 0 && slotIndex < powerSlots.length) {
            String powerSlot = powerSlots[slotIndex];
            if (powerSlot != null && !powerSlot.isEmpty()) {
                return ChatColor.YELLOW + "Slot " + ChatColor.GOLD + (slotIndex + 1) + ChatColor.WHITE + ": " + powerSlot;
            } else {
                return ChatColor.YELLOW + "Slot " + ChatColor.GOLD + (slotIndex + 1) + ChatColor.WHITE + ": " + ChatColor.RED + "Empty";
            }
        }
        return ChatColor.RED + "Invalid Slot";
    }



    public void hideScoreboard() {
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }


    private PlayerData getPlayerData(Player player, PlayerDataManager playerDataManager) {
        return playerDataManager.getPlayerData(player);
    }
}
