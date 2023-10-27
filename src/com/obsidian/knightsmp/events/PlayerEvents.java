package com.obsidian.knightsmp.events;

import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.events.PlayerLoadingCompletedEvent;
import com.obsidian.knightsmp.KnightSmp;
import com.obsidian.knightsmp.PlayerSecurity.CaptchaManager;
import com.obsidian.knightsmp.PlayerSecurity.LoginManager;
import com.obsidian.knightsmp.items.ItemManager;
import com.obsidian.knightsmp.items.fragments.FragrentManager;
import com.obsidian.knightsmp.managers.ThreadManager;
import com.obsidian.knightsmp.utils.PlayerData;
import com.obsidian.knightsmp.managers.PlayerDataManager;
import com.obsidian.knightsmp.utils.PowerSlotsScoreboard;
import net.ess3.api.IEssentials.*;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


import java.util.*;

import static com.obsidian.knightsmp.KnightSmp.*;

public class PlayerEvents implements Listener {



    public static AdvancementTab advancementTab;
    private RootAdvancement root;
    public PowerSlotsScoreboard PowerSlotsScoreboard;
    private UltimateAdvancementAPI api;
    private final Map<String, ItemStack[]> playerItemsMap = new HashMap<>();
    private final PlayerDataManager playerDataManager;
   public static PowerSlotsScoreboard powerSlotsScoreboard;

    public PlayerEvents(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }



    @EventHandler
    public void omPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event){
        UUID id = event.getUniqueId();
        OfflinePlayer OfflinePlayer = playerDataManager.getOfflinePlayer(event.getName());
        if (OfflinePlayer.isBanned()){
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        if (!playerDataManager.hasPlayerData(player)) {
            event.setJoinMessage(ChatColor.GOLD + player.getName() + ", Welcome to the Knights SMP! Enjoy your stay.");
        } else {

              event.setJoinMessage(ChatColor.GREEN + player.getName() + ", Welcome back to the Knights SMP!");
          }
        // Assuming you have a method to initialize the scoreboard in your PowerSlotsScoreboard class
        // Cycle through power slots and apply effects

        player.setResourcePack("https://file-host-1.blueobsidian.repl.co/download.php?file=data.zip");
    }




    @EventHandler
    public  void  onPlayerLoad(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String ip = Objects.requireNonNull(event.getPlayer().getAddress()).getAddress().getHostAddress();
        powerSlotsScoreboard = new PowerSlotsScoreboard(player, playerDataManager);
        powerSlotsScoreboard.initScoreboard();
        if (!playerDataManager.hasPlayerData(player)) {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Your current class is: " + playerDataManager.getPlayerClass(player));
            player.sendMessage(ChatColor.LIGHT_PURPLE + "To Upgrade your class, fill up your power slots");
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Get Power Items from the Fragrents");
            player.resetTitle();
            player.getInventory().addItem(ItemManager.book);
            // List of available powers
            // Assign a random power from the list
            PlayerData newPlayerData = new PlayerData(player.getUniqueId(), player.getName(), 0, configManager.getString("default-player-power"), configManager.getString("default-player-class"));
            playerDataManager.setPlayerData(player, newPlayerData);
            playerDataManager.savePlayerData();
        } else {
            boolean hasBook = false;
            for (ItemStack item : player.getInventory().getContents()) {
                if (item != null && item.isSimilar(ItemManager.book)) {  // Check if the item is similar to the desired book
                    hasBook = true;
                    break;
                }
            }
            if (!hasBook) {
                player.getInventory().addItem(ItemManager.book);
            }
            PlayerData playerData = playerDataManager.getPlayerData(player);
            if (playerData != null) {
                String[] powerSlots = playerData.getPowerSlots();
                for (String powerSlot : powerSlots) {
                    if (powerSlot != null) {
                        // Here, you can add logic to apply effects based on the powerSlot
                        if (powerSlot.equalsIgnoreCase(ChatColor.BOLD + "" + ChatColor.BLUE + "Bulldozer")) {
                            // Apply effect for Sledge power
                            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 99999, 3));
                        } else if (powerSlot.equalsIgnoreCase(ChatColor.BOLD + "" + ChatColor.YELLOW+ "Super Speed")) {
                            // Apply effect for SomeOtherPower
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 999999, 3));
                        }
                        // Add more cases for other power slots as needed
                    }
                }
            }

                player.sendMessage(ChatColor.LIGHT_PURPLE + "Your current class is: " + playerDataManager.getPlayerClass(player));
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Your current number of power slots filled is: " + playerDataManager.getFilledPowerSlots(player) + "/10");

        }
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        // Use ThreadManager to run the task in another thread
        ThreadManager.createThread("PlayerMoveThread-" + player.getName(), () -> {
            if (playerDataManager.hasPlayerData(player)) {
                String location = "x:" + player.getLocation().getBlockX() + " y:" + player.getLocation().getBlockY() + " z:" + player.getLocation().getBlockZ() + " world:" + player.getLocation().getWorld().getName();
                playerDataManager.setLastKnownLocation(player, location);
                playerDataManager.setPlayerInventory(player, player.getInventory().getContents());
            }
        });

    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setKeepInventory(true);
        Player player = event.getEntity();
        ItemStack[] savedItems = new ItemStack[player.getInventory().getSize()];
        playerDataManager.setPlayerLastInventory(player, player.getInventory().getContents());

        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item != null){
                if (  ItemManager.isCustomItem(item) || FragrentManager.isCustomItem(Objects.requireNonNull(item))) {
                    savedItems[i] = item.clone(); // Clone the item to avoid modifying the original
                    player.getInventory().remove(item);
                }
            }
        }
        // Remove custom items from the drops
        List<ItemStack> drops = event.getDrops();
        drops.removeIf(item -> ItemManager.isCustomItem(item));
        event.setKeepInventory(false);
        player.dropItem(true);
        playerItemsMap.put(player.getName(), savedItems);
        playerDataManager.setPlayerInventory(player, player.getInventory().getContents());
    }


    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (playerItemsMap.containsKey(player.getName())) {
            ItemStack[] savedItems = playerItemsMap.get(player.getName());
            player.getInventory().setContents(savedItems);
            playerItemsMap.remove(player.getName());
        }
        playerDataManager.setPlayerInventory(player, player.getInventory().getContents());
    }




}
