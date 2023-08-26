package com.obsidian.knightsmp.events;

import com.earth2me.essentials.PlayerList;
import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.events.PlayerLoadingCompletedEvent;
import com.obsidian.knightsmp.KnightSmp;
import com.obsidian.knightsmp.PlayerSecurity.CaptchaManager;
import com.obsidian.knightsmp.PlayerSecurity.LoginManager;
import com.obsidian.knightsmp.items.ItemManager;
import com.obsidian.knightsmp.items.fragments.FragrentManager;
import com.obsidian.knightsmp.utils.ArmouredEntity;
import com.obsidian.knightsmp.utils.PlayerData;
import com.obsidian.knightsmp.utils.PlayerDataManager;
import com.obsidian.knightsmp.utils.PowerSlotsScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

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
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        if (!playerDataManager.hasPlayerData(player)) {
            event.setJoinMessage(ChatColor.GOLD + player.getName() + ", Welcome to the Knights SMP! Enjoy your stay.");
        } else {
          if (LoginManager.isSuspiciousLogin(player.getName())){
              event.setJoinMessage(ChatColor.RED + player.getName() + ", Welcome back to the Knights SMP!");
              player.sendMessage(ChatColor.RED + "Your IP has been changed, Run /captcha verify <captcha> to secure your account");
          }else {
              event.setJoinMessage(ChatColor.GREEN + player.getName() + ", Welcome back to the Knights SMP!");
          }
        }

        // Assuming you have a method to initialize the scoreboard in your PowerSlotsScoreboard class
        // Cycle through power slots and apply effects

        player.setResourcePack("https://obsidianator-code-1.blueobsidian.repl.co/data.zip");
    }


    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        ChatColor playerNameColor = ChatColor.getByChar(player.getName().charAt(1));
        if (playerNameColor == null) {
            playerNameColor = ChatColor.WHITE; // Default to white if the player's name color is not recognized
        }
        if (!CaptchaManager.hasCaptcha(player)) {
            if (!captchaManager.hasCaptcha(player)) {

                event.setFormat(ChatColor.DARK_RED + "[Unverified] "+ playerNameColor.getChar() + player.getName() + ChatColor.GREEN + " >>> " + ChatColor.WHITE + message);
                player.sendMessage(ChatColor.GOLD + "To verify your account, run /password set <password>");
            }
        }
        if (LoginManager.isSuspiciousLogin(player.getName())) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Your IP has been changed. Run /password verify <password> to secure your account.");
        }
    }



    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage().substring(1); // Removing the "/" at the beginning

        if (LoginManager.isSuspiciousLogin(player.getName())) {
            if (!command.startsWith("password")) {
                player.sendMessage("Your IP has been changed. Run /password verify <password> to login to your account.");
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public  void  onPlayerLoad(PlayerLoadingCompletedEvent event) {
        Player player = event.getPlayer();
        String ip = event.getPlayer().getAddress().getAddress().getHostAddress();
        powerSlotsScoreboard = new PowerSlotsScoreboard(player, playerDataManager);
        if (LoginManager.isSuspiciousLogin(player.getName())) {

        }else {
            if (playerDataManager.getLastIp(event.getPlayer()) == null || playerDataManager.getLastIp(event.getPlayer()).equals("")) {
                playerDataManager.setLastIp(event.getPlayer(), ip);
            } else if (playerDataManager.getLastIp(event.getPlayer()).equals(ip)) {

            }
        }
        powerSlotsScoreboard = new PowerSlotsScoreboard(player, playerDataManager);
        powerSlotsScoreboard.initScoreboard();
        if (!playerDataManager.hasPlayerData(player)) {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Your current class is: " + playerDataManager.getPlayerClass(player));
            player.sendMessage(ChatColor.LIGHT_PURPLE + "To Upgrade your class, fill up your power slots");
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Get Power Items from the Fragrents");
            player.sendMessage(ChatColor.YELLOW+"Do /password set <password> to secure your account from other players");
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
            if (!captchaManager.hasCaptcha(player)){
                player.sendMessage(ChatColor.RED + "Your account is not secured");
                player.sendMessage(ChatColor.RED+"Run /password set <password> to secure your account");
            }
            if (LoginManager.isSuspiciousLogin(player.getName())){
                KnightSmp.getPlugin().getServer().getConsoleSender().sendMessage("SUSPICIOUS LOGIN DETECTED BY : " + player.getName());
            }else {
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Your current class is: " + playerDataManager.getPlayerClass(player));
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Your current number of power slots filled is: " + playerDataManager.getFilledPowerSlots(player) + "/10");
            }
        }
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
    }


    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (playerItemsMap.containsKey(player.getName())) {
            ItemStack[] savedItems = playerItemsMap.get(player.getName());
            player.getInventory().setContents(savedItems);
            playerItemsMap.remove(player.getName());
        }
    }




}
