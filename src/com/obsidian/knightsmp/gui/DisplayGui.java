package com.obsidian.knightsmp.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DisplayGui implements InventoryHolder {

    private final Inventory inventory;

    public DisplayGui(String title, int size) {
        this.inventory = Bukkit.createInventory(this, size, title);
    }

    public void updateItems(List<ItemStack> items) {
        inventory.clear();

        for (ItemStack item : items) {
            inventory.addItem(item);
        }
    }


    public void open(Player player) {
        player.openInventory(inventory);
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
