package com.obsidian.knightsmp.events;

import com.obsidian.knightsmp.KnightSmp;
import com.obsidian.knightsmp.gui.DisplayGui;
import com.obsidian.knightsmp.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class GroundItemsManager implements Listener {

    private final FileManager fileManager;
    private final String itemCacheFolder = "/item-cache";
  public  List<ItemStack> itemsOnGround = new ArrayList<>();

    public GroundItemsManager() {
        this.fileManager = KnightSmp.fileManager;
    }

    public List<ItemStack> getItemsOnGround() {
        KnightSmp.getPlugin().getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "Loaded " +itemsOnGround.size() + " items from cache, "+ itemsOnGround);
        // Add items from actual items on the ground
        for (Item itemEntity : Bukkit.getWorlds().get(0).getEntitiesByClass(Item.class)) {
          itemsOnGround.add(itemEntity.getItemStack());
        }
        return itemsOnGround;
    }
    public void removeItemFromGround(ItemStack itemToRemove) {
        KnightSmp.sendMessage(itemsOnGround.toString());
        itemsOnGround.removeIf(item -> item.isSimilar(itemToRemove));
        KnightSmp.sendMessage(itemsOnGround.toString());
        saveItemsToFile(itemsOnGround, fileManager.getFile(itemCacheFolder + "/items.yml"));
    }
    public boolean isGroundItem(ItemStack item) {
        for (ItemStack groundItem : itemsOnGround) {
            if (groundItem.isSimilar(item)) {
                return true;
            }
        }
        return false;
    }



    public void saveItemsToFile(List<ItemStack> items, File outputFile) {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        List<Map<String, Object>> itemsList = new ArrayList<>();
        for (ItemStack itemStack : items) {
            Map<String, Object> itemMap = new LinkedHashMap<>();
            itemMap.put("type", itemStack.getType().name());
            itemMap.put("amount", itemStack.getAmount());

            // Add enchantments to the map
            Map<String, Integer> enchantmentsMap = new HashMap<>();
            for (Map.Entry<Enchantment, Integer> enchantment : itemStack.getEnchantments().entrySet()) {
                if (enchantment.getValue() > 0) {
                    // Remove "minecraft:" prefix from enchantment key
                    String enchantmentKey = enchantment.getKey().getKey().toString().toLowerCase();
                    if (enchantmentKey.startsWith("minecraft:")) {
                        enchantmentKey = enchantmentKey.substring("minecraft:".length());
                    }
                    enchantmentsMap.put(enchantmentKey, enchantment.getValue());
                }
            }
            itemMap.put("enchantments", enchantmentsMap);

            itemsList.add(itemMap);
            KnightSmp.sendMessage(itemsList.toString());
        }

        try (FileWriter writer = new FileWriter(outputFile)) {
            yaml.dump(itemsList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadItemsFromFile() {
        File itemsFile = fileManager.getFile(itemCacheFolder + "/items.yml");

        if (!itemsFile.exists()) {
            return; // No items file to load
        }

        Yaml yaml = new Yaml();
        try (FileReader reader = new FileReader(itemsFile)) {
            List<Map<String, Object>> itemsList = yaml.load(reader);

            if (itemsList != null) {
                for (Map<String, Object> itemMap : itemsList) {
                    String type = (String) itemMap.get("type");
                    int amount = (int) itemMap.get("amount");

                    // Load enchantments if available
                    ItemStack itemStack = new ItemStack(Material.valueOf(type), amount);
                    Map<String, Object> enchantmentsMap = (Map<String, Object>) itemMap.get("enchantments");
                    if (enchantmentsMap != null) {
                        for (Map.Entry<String, Object> enchantmentEntry : enchantmentsMap.entrySet()) {
                            // Remove "minecraft:" prefix from enchantment key
                            String enchantmentKey = enchantmentEntry.getKey();
                            Enchantment enchantment = Enchantment.getByKey(new NamespacedKey("minecraft", enchantmentKey));
                            if (enchantment != null && enchantmentEntry.getValue() instanceof Integer) {
                                int level = (int) enchantmentEntry.getValue();
                                itemStack.addUnsafeEnchantment(enchantment, level);
                            }
                        }
                    }
                    itemsOnGround.add(itemStack);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @EventHandler
    public void onPluginDisabled(PluginDisableEvent event) {
        if (event.getPlugin().getName().equals("Knight_Plugin")) {
            saveItemsToCache();
        }
    }





   @EventHandler
   public void inventoryClickEvent(InventoryClickEvent event){
        Player player = (Player ) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        if (inventory.getHolder() instanceof DisplayGui){
            ItemStack item = event.getCurrentItem();
                if (isGroundItem(item)) {
                    removeItemFromGround(item);
                }
        }
   }
    // Helper method to check if an array contains an ItemStack
    private boolean containsItem(ItemStack[] array, ItemStack target) {
        for (ItemStack item : array) {
            if (item != null && item.isSimilar(target)) {
                return true;
            }
        }
        return false;
    }
    public void saveItemsToCache() {
        if (!fileManager.folderExists(itemCacheFolder)) {
            fileManager.createFolder(itemCacheFolder);

            // Add items from actual items on the ground
            for (Item itemEntity : Bukkit.getWorlds().get(0).getEntitiesByClass(Item.class)) {
                itemsOnGround.add(itemEntity.getItemStack());
            }
            File itemsFile = fileManager.getFile(itemCacheFolder + "/items.yml");
            saveItemsToFile(itemsOnGround, itemsFile);
        }
    }

    public void loadItemsFromCache(File inputFile) {
        // Load and process the items from the specified file (inputFile)
        // Add the loaded items to itemsOnGround
    }
}
