package com.obsidian.knightsmp.inventories;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class SelectionScreen implements InventoryHolder {

    private  Inventory inv;

    public SelectionScreen() {
        inv = Bukkit.createInventory(this , 9, ChatColor.BLUE + "Selection Screen");
        init();
    }

    private void init() {
   ItemStack item ;
    for (int i = 0; i < 4; i++) {
     item = createItem("§a§lAccept",Material.LIME_STAINED_GLASS_PANE, (List) Collections.singletonList("§l§7Click to accept the request"));
    inv.setItem(i,item);
     }

    item = createItem("§9 Make a Selection",Material.BOOK, Collections.singletonList("§7Click to make a selection"));
    inv.setItem(inv.firstEmpty(),item);
        for (int i = 5; i < 9; i++) {
            item = createItem("§c§lDecline",Material.RED_STAINED_GLASS_PANE, Collections.singletonList("§7Click to decline the request"));
            inv.setItem(i,item);
        }

    }

    public void handleError(Exception e,String a) {
        Bukkit.getServer().getConsoleSender().sendMessage("Error on "+ a);
    }

    private ItemStack createItem(String name, Material mat , List lore  ) {
        try {
            ItemStack item = new ItemStack(mat,1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(name);
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }catch (Exception e){
            handleError(e,"Main");
            return null ;
        }
    }


    @Override
    public Inventory getInventory() {
        return inv;
    }
}
