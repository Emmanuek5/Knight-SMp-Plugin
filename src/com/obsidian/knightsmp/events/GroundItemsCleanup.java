package com.obsidian.knightsmp.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class GroundItemsCleanup implements Listener {

    private final Plugin plugin;
    private final List<ItemStack> itemsOnGround;

    public GroundItemsCleanup(Plugin plugin, List<ItemStack> itemsOnGround) {
        this.plugin = plugin;
        this.itemsOnGround = itemsOnGround;

        // Schedule the cleanup task to run every 500 seconds (20 ticks per second)
        new CleanupTask().runTaskTimer(plugin, 0, 500 * 20);
    }

    @EventHandler
    public void onItemDespawn(ItemDespawnEvent event) {
        // When an item despawns, remove it from the itemsOnGround list
        itemsOnGround.removeIf(item -> item.isSimilar(event.getEntity().getItemStack()));
    }

    private class CleanupTask extends BukkitRunnable {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();

            // Send warning messages to players
            for (Player player : Bukkit.getOnlinePlayers()) {
                long timeLeft = ((currentTime + 500000) - (currentTime % 1000000)) - currentTime; // Time left until cleanup
                if (timeLeft == 60000 || timeLeft == 20000 || timeLeft == 10000) {
                    player.sendMessage(ChatColor.YELLOW + "Ground items will be cleaned up in " + (timeLeft / 1000) + " seconds!");
                }
            }
            // Clear all entities on the ground
            for (Item itemEntity : Bukkit.getWorlds().get(0).getEntitiesByClass(Item.class)) {
                itemsOnGround.add(itemEntity.getItemStack());
                itemEntity.remove();
            }
            // Create an item representing the time
            ItemStack timeItem = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
            ItemMeta timeItemMeta = timeItem.getItemMeta();
            timeItemMeta.setDisplayName(ChatColor.WHITE + "Time: " + ChatColor.GOLD + (currentTime + 500000) + ChatColor.WHITE + "ms");


            // Add the time item to itemsOnGround
            itemsOnGround.add(timeItem);

            // Optionally, you can perform additional tasks here, such as saving the items to a file
            plugin.getLogger().info("Ground items cleanup completed.");
        }
    }
}
