package com.obsidian.knightsmp.events;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.obsidian.knightsmp.KnightSmp;
import com.obsidian.knightsmp.items.ItemManager;
import com.obsidian.knightsmp.items.fragments.FragrentManager;
import com.obsidian.knightsmp.utils.ArmouredEntity;
import com.obsidian.knightsmp.utils.PlayerData;
import eu.endercentral.crazy_advancements.CrazyAdvancementsAPI;
import eu.endercentral.crazy_advancements.JSONMessage;
import eu.endercentral.crazy_advancements.NameKey;

import eu.endercentral.crazy_advancements.advancement.AdvancementFlag;
import eu.endercentral.crazy_advancements.advancement.AdvancementVisibility;
import eu.endercentral.crazy_advancements.advancement.criteria.Criteria;
import org.bukkit.*;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import  eu.endercentral.crazy_advancements.advancement.Advancement;

import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.obsidian.knightsmp.KnightSmp.getPlugin;

public class ItemEvents implements Listener {
    private static final HashMap<Player, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_DURATION = 6 * 1000; // 30 seconds in milliseconds

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.PHYSICAL) {
            Player player = event.getPlayer();
            if (event.getItem() != null) {
                if (event.getItem().getItemMeta().equals(ItemManager.wand.getItemMeta())) {
                    if (checkCooldown(player)) {
                        Vector direction = player.getEyeLocation().getDirection().normalize();
                        Location targetLocation = player.getEyeLocation().add(direction.multiply(5));
                        player.getWorld().createExplosion(targetLocation, 3.2f);
                        player.sendMessage("§2You have used the §9Knights Wand!");
                        setCooldown(player);
                    } else {
                        player.sendMessage("§cThe wand is on cooldown. Please wait.");
                    }
                }
                if (event.getItem().getItemMeta().equals(ItemManager.staff.getItemMeta())) {
                    if (checkCooldown(player)) {
                        // Strike lightning at the target location
                        Vector direction = player.getLocation().getDirection().normalize();
                        Location targetLocation = player.getEyeLocation().add(direction.multiply(5));
                        double spawnDistance = 2.0; // Distance in blocks in front of the player
                        Location zombieSpawnLocation = player.getLocation().add(direction.multiply(spawnDistance));
                        zombieSpawnLocation.setY(player.getLocation().getY()); // Use the player's current Y-coordinate
                        player.getWorld().strikeLightning(targetLocation);
                        // Apply potion effects to the player
                        PotionEffect effect1 = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 100, 2);
                        PotionEffect effect2 = new PotionEffect(PotionEffectType.SPEED, 20 * 100, 2);
                        player.addPotionEffect(effect1);
                        player.addPotionEffect(effect2);

                        // Spawn the zombies after a dela
                            ArmouredEntity armouredEntity = new ArmouredEntity();
                            armouredEntity.spawnArmoredZombieWithTarget(zombieSpawnLocation, player, 5);
                        // 40 ticks = 2 seconds (20 ticks per second)
                        setCooldown(player);
                    } else {
                        player.sendMessage("§cThe staff is on cooldown. Please wait.");
                    }

                }
            }
        }
    }
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            Player player = event.getPlayer();
            if (event.getItem() != null) {
                // Create Passive Active Effect
                if (event.getItem().getItemMeta().equals(ItemManager.wand.getItemMeta())) {
                    if (checkCooldown(player)) {
                        Location currentLocation = player.getLocation();
                        double yawRadians = Math.toRadians(currentLocation.getYaw()); // Convert yaw to radians
                        double distance = 10.0; // 10 blocks
                        double xOffset = -Math.sin(yawRadians) * distance; // Negative sin for X-axis
                        double zOffset = Math.cos(yawRadians) * distance; // Cosine for Z-axis
                        Location newLocation = currentLocation.clone().add(xOffset, 0, zOffset);
                        newLocation.setY(player.getWorld().getHighestBlockYAt(newLocation));
                        player.teleport(newLocation);
                        player.sendMessage("§2You have used the §9Knights Wand!");
                        setCooldown(player);
                    } else {
                      player.sendMessage("§cThe wand is on cooldown. Please wait.");
                    }
                }
                if (event.getItem().getItemMeta().equals(ItemManager.staff.getItemMeta())) {
                    if (checkCooldown(player)) {
                        Location currentLocation = player.getLocation();
                        PotionEffect effect1 = new PotionEffect(PotionEffectType.JUMP, 10 * 100, 2);
                        PotionEffect effect2 = new PotionEffect(PotionEffectType.HEALTH_BOOST , 10 * 100, 2);
                        player.addPotionEffect(effect1); // Apply the potion effect
                        player.addPotionEffect(effect2);
                        player.sendMessage("§2You have used the §9Knights Staff!");
                        setCooldown(player);
                    } else {
                        player.sendMessage("§cThe staff is on cooldown. Please wait.");
                    }
                    if (event.getItem().getItemMeta().equals(ItemManager.staff.getItemMeta())){
                        if (checkCooldown(player)){

                        }
                    }
                }

            }
        }
    }
    private final Map<Player, Integer> cooldownMap = new HashMap<>();
    private final long cooldownResetDelay = 10 * 20;
    private final int maxHeight = 3; // Maximum height to mine upwards
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (ItemManager.isCustomItem(item)) {
            if (item.equals(ItemManager.pickaxe)) {
                if (canUseItem(player)) {
                    incrementCooldown(player);

                    Location playerLocation = player.getEyeLocation(); // Get player's eye location
                    Location targetLocation = playerLocation.clone().add(playerLocation.getDirection());

                    int radius = 2; // Half the size of the area in each dimension

                    for (int y = 0; y <= maxHeight; y++) { // Up to maxHeight
                        for (int x = -radius; x <= radius; x++) {
                            for (int z = -radius; z <= radius; z++) {
                                Location blockLocation = targetLocation.clone().add(x, y, z);
                                Block targetBlock = blockLocation.getBlock();

                                if (!targetBlock.isEmpty()) {
                                    targetBlock.breakNaturally(item);
                                }
                            }
                        }
                    }

                    scheduleCooldownReset(player);
                } else {
                    player.sendMessage("You've reached the usage limit for this item.");
                }
            }
        }
    }

@EventHandler
    public void onItemCraft(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack craftedItem = event.getInventory().getResult();
        if (ItemManager.isCustomItem(craftedItem)) {
            // Define the slot where the required item should be
            int requiredSlot = 8; // Change this to the appropriate slot index
            // Get the item in the required slot
            ItemStack requiredSlotItem = event.getInventory().getItem(requiredSlot);
            assert requiredSlotItem != null;
            Bukkit.getServer().getConsoleSender().sendMessage(requiredSlotItem.toString());
            Bukkit.getServer().getConsoleSender().sendMessage(String.valueOf(FragrentManager.isFragment(requiredSlotItem)));
            // Check if the required item is your custom item with the same metadata
            if (FragrentManager.isFragment(requiredSlotItem)) {
           player.sendMessage("§2You have crafted a Sledge Fragment!");
                AdvancementDisplay rootDisplay = new AdvancementDisplay(ItemManager.pickaxe, "Hidden Depths §c", AdvancementFrameType.TASK, true, true, 0, 0, "Craft The Sledge Hammer Item!");

                return;
            }
            // Prevent crafting by cancelling the event
            event.setCancelled(true);
        }
        if (event.getCurrentItem().getItemMeta().equals(ItemManager.wand.getItemMeta())) {
            ItemStack icon = event.getCurrentItem();
            if (icon != null) {
                icon = ItemManager.wand;
            }
            Bukkit.broadcastMessage(ChatColor.RED+"A New Wand has been crafted by " + player.getName());
            player.getWorld().playSound(player.getLocation(), "wand", 1, 1);
        }
    }

    private boolean isSameCustomItem(ItemStack item1, ItemStack item2) {
        // Compare metadata or any other criteria that define "sameness"
        return item1.getItemMeta()== item2.getItemMeta() ;
    }
 public boolean checkCooldown(Player player) {
        if (cooldowns.containsKey(player)) {
            long lastUseTime = cooldowns.get(player);
            long currentTime = System.currentTimeMillis();

            if (currentTime - lastUseTime >= COOLDOWN_DURATION) {
                return true; // Cooldown expired, can use again
            }
            return false; // Still on cooldown
        }
        return true; // No previous usage, can use immediately
    }
    private final int maxUses = 10; // Maximum number of uses before cooldown
    public boolean canUseItem(Player player) {
        if (!cooldownMap.containsKey(player)) {
            return true;
        }

        int uses = cooldownMap.get(player);
        return uses < maxUses;
    }

    public void incrementCooldown(Player player) {
        int uses = cooldownMap.getOrDefault(player, 0);
        uses++;
        cooldownMap.put(player, uses);

        if (uses >= maxUses) {
            // Implement any action you want when the player reaches the usage limit
            // For example, you could remove the item or apply a cooldown period.
        }
    }
    public void scheduleCooldownReset(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                cooldownMap.remove(player);
            }
        }.runTaskLater( KnightSmp.getPlugin(), cooldownResetDelay);
    }
   public  void setCooldown(Player player) {
        cooldowns.put(player, System.currentTimeMillis());
    }
}
