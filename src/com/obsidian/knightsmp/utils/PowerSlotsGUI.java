package com.obsidian.knightsmp.utils;

import com.obsidian.knightsmp.managers.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PowerSlotsGUI implements InventoryHolder, Listener {

    private final Plugin plugin;
    private final PlayerDataManager playerDataManager;
    private Inventory gui;

    public PowerSlotsGUI(Plugin plugin, PlayerDataManager playerDataManager) {
        this.plugin = plugin;
        this.playerDataManager = playerDataManager;
        gui = Bukkit.createInventory(this, 9 * 2, ChatColor.BOLD + "Power Slots Arrangement");
    }

    public void openPowerSlotsGUI(Player player) {
        String[] powerSlots = playerDataManager.getPowerSlots(player);
        List<String> powerSlotsList = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            if (i < powerSlots.length && powerSlots[i] != null) {
                powerSlotsList.add(powerSlots[i]);
            } else {
                powerSlotsList.add(""); // Empty slot
            }
        }


        ItemStack blankSlot = createSlotItem(Material.BOOK, ChatColor.GRAY + "Empty Slot", Arrays.asList(""));

        for (int i = 0; i < 9 * 2; i++) {
            ItemStack slotItem;
            if (i < powerSlotsList.size()) {
                slotItem = createSlotItem(Material.BOOK, powerSlotsList.get(i), Arrays.asList(""));
            } else {
                slotItem = blankSlot.clone();
            }
            gui.setItem(i, slotItem);
        }

        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedInventory != null && clickedItem != null && clickedInventory.getHolder() instanceof PowerSlotsGUI) {
            PowerSlotsGUI powerSlotsGUI = (PowerSlotsGUI) clickedInventory.getHolder();

            // Check if the clicked item is a book with a custom name (power slot name)
            if (clickedItem.getType() == Material.BOOK && clickedItem.hasItemMeta() && clickedItem.getItemMeta() instanceof BookMeta) {
                BookMeta bookMeta = (BookMeta) clickedItem.getItemMeta();
                String powerSlotName = ChatColor.stripColor(bookMeta.getDisplayName());

                // Get the index of the clicked slot
                int clickedSlotIndex = event.getSlot();

                // Check if the clicked slot is within the valid range of power slots
                if (clickedSlotIndex >= 0 && clickedSlotIndex < 9 * 2) {
                    // Get the power slots
                    String[] powerSlots = playerDataManager.getPowerSlots(player);

                    // Find the index of the power slot with the same name
                    int targetSlotIndex = -1;
                    for (int i = 0; i < powerSlots.length; i++) {
                        if (powerSlotName.equals(powerSlots[i])) {
                            targetSlotIndex = i;
                            break;
                        }
                    }

                    // Swap power slots if the target slot is valid
                    if (targetSlotIndex != -1 && clickedSlotIndex != targetSlotIndex) {
                        String temp = powerSlots[clickedSlotIndex];
                        powerSlots[clickedSlotIndex] = powerSlots[targetSlotIndex];
                        powerSlots[targetSlotIndex] = temp;

                        // Update the player's power slots
                        playerDataManager.setPowerSlots(player, powerSlots);

                        // Update the GUI
                        powerSlotsGUI.openPowerSlotsGUI(player);
                    }

                    event.setCancelled(true); // Cancel the event to prevent taking items out
                }
            }
        }
    }





    private ItemStack createSlotItem(Material material, String displayName, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public Inventory getInventory() {
        return gui;
    }

}
