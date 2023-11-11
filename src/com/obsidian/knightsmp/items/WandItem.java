package com.obsidian.knightsmp.items;

import com.obsidian.knightsmp.KnightSmp;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WandItem extends ItemManager {
    public static void createWand() {
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(1890);
        meta.setLocalizedName("knight_wand");
        meta.setDisplayName("§5The §9Knights Wand§5");
        List<String> lore = new ArrayList<>();
        lore.add("Fight For The Truth");
        lore.add("Just like the Knights");
        lore.add("In the Days of Old");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.addEnchant(Enchantment.WATER_WORKER, 1, true);
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        wand = item;

        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey("knightsmp", "wand_item"),item);
        sr.shape("BBB","DSD", "  S");
        sr.setIngredient('S', Material.STICK);
        sr.setIngredient('D', Material.DIAMOND);
        sr.setIngredient('B', Material.BLAZE_POWDER);
        Bukkit.getServer().addRecipe(sr);
        KnightSmp.sendMessage("Registered recipe: " + sr.getKey().toString());

    }

}