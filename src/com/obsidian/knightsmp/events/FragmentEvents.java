package com.obsidian.knightsmp.events;

import com.obsidian.knightsmp.KnightSmp;
import com.obsidian.knightsmp.items.fragments.FragrentManager;
import com.obsidian.knightsmp.utils.ArmouredEntity;
import com.obsidian.knightsmp.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.obsidian.knightsmp.KnightSmp.playerDataManager;

public class FragmentEvents implements Listener {

    private final Map<Player, Map<String, Long>> cooldowns = new HashMap<>();
    private static final Map<String, Long> COOLDOWN_DURATIONS = new HashMap<>(); // Map of power slots to cooldown durations

    // Initialize the cooldown durations for each power slot
    static {
        COOLDOWN_DURATIONS.put(ChatColor.BOLD + "" + ChatColor.BLUE + "Bulldozer", 10 * 1000L); // 10 seconds cooldown
        COOLDOWN_DURATIONS.put(ChatColor.BOLD + "" + ChatColor.YELLOW + "Super Speed", 6 * 1000L); // 6 seconds cooldown
        // Add more power slots and their cooldowns as needed
    }

    private boolean checkCooldown(Player player, String powerSlot) {
        if (cooldowns.containsKey(player)) {
            Map<String, Long> playerCooldowns = cooldowns.get(player);
            if (playerCooldowns.containsKey(powerSlot)) {
                long lastUse = playerCooldowns.get(powerSlot);
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - lastUse;
                long cooldownTime = COOLDOWN_DURATIONS.getOrDefault(powerSlot, 0L);

                return elapsedTime < cooldownTime;
            }
        }
        return false;
    }

    private void setCooldown(Player player, String powerSlot) {
        cooldowns.computeIfAbsent(player, k -> new HashMap<>());
        cooldowns.get(player).put(powerSlot, System.currentTimeMillis());
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().toString().contains("LEFT_CLICK") && player.isSneaking()) {
            PlayerData playerData = KnightSmp.playerDataManager.getPlayerData(player);
            if (playerData != null) {
                String[] powerSlots = playerData.getPowerSlots();
                for (String powerSlot : powerSlots) {
                    if (powerSlot != null) {
                        // Here, you can add logic to apply effects based on the powerSlot
                        if (checkCooldown(player, powerSlot)) {
                            player.sendMessage(ChatColor.RED + "You can't use this power yet. It's still on cooldown.");
                            return;
                        }
                        if (powerSlot.equalsIgnoreCase(ChatColor.BOLD + "" + ChatColor.BLUE + "Bulldozer")) {
                            // Your "Bulldozer" logic here
                        } else if (powerSlot.equalsIgnoreCase(ChatColor.BOLD + "" + ChatColor.YELLOW + "Super Speed")) {
                            // Calculate the start location for lightning strikes in front of the player

                            Location playerLocation = player.getLocation();
                            Vector direction = playerLocation.getDirection().normalize();
                            Location startLocation = playerLocation.clone().add(direction.multiply(4)); // Adjust the multiplier as needed
                            ArmouredEntity armouredEntity = new ArmouredEntity();
                            armouredEntity.spawnArmoredZombieWithTarget(startLocation, player, 1);
                            // Generate 5 lightning bolts in a row
                            setCooldown(player, powerSlot);
                            for (int i = 0; i < 5; i++) {
                                Location strikeLocation = startLocation.clone().add(direction.multiply(i * 7));
                                player.getWorld().strikeLightning(strikeLocation);

                            }
                            // Add more cases for other power slots as needed
                        }
                    }
                }
            }
        }
    }


    @EventHandler
    public void onRightClickAndShift(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().toString().contains("RIGHT_CLICK") && event.getPlayer().isSneaking()) {

            if (player.getInventory().getItemInMainHand().isSimilar(FragrentManager.sledgeFragment)) {
                if (!playerDataManager.hasPowerSlot(player, ChatColor.BOLD + "" + ChatColor.BLUE + "Bulldozer")) {
                    player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 99999, 3));
                    playerDataManager.setNextPowerSlot(player, ChatColor.BOLD + "" + ChatColor.BLUE + "Bulldozer");
                    PlayerEvents.powerSlotsScoreboard.updatePowerSlotsScoreboard();
                    return;
                } else {
                    player.sendMessage("§cYou already have the Bulldozer power!");
                    return;
                }
            }
            if (player.getInventory().getItemInMainHand().isSimilar(FragrentManager.flashPowerFragment)) {
                if (!playerDataManager.hasPowerSlot(player, ChatColor.BOLD + "" + ChatColor.YELLOW+ "Super Speed")) {
                    player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                    playerDataManager.setNextPowerSlot(player, ChatColor.BOLD + "" + ChatColor.YELLOW+ "Super Speed");
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 999999, 3));
                    PlayerEvents.powerSlotsScoreboard.updatePowerSlotsScoreboard();
                    return;
                } else {
                    player.sendMessage("§cYou already have the Super Speed power!");
                    return;
                }
            }
            if (player.getInventory().getItemInMainHand().isSimilar(FragrentManager.exaliburFragment)) {
                if (!playerDataManager.hasPowerSlot(player, ChatColor.BOLD + "" + ChatColor.BLUE + "Exalibur")) {
                    player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                    playerDataManager.setNextPowerSlot(player, ChatColor.BOLD + "" + ChatColor.BLUE + "Exalibur");

                    PlayerEvents.powerSlotsScoreboard.updatePowerSlotsScoreboard();
                    return;
                } else {
                    player.sendMessage("§cYou already have the Exalibur power!");
                    return;
                }
            }
        }
    }
}