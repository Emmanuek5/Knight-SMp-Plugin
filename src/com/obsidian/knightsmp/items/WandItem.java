package com.obsidian.knightsmp.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WandItem extends ItemManager{
    static ShapedRecipe sr;
    public static   void createWand(){
        ItemStack item = new ItemStack(Material.STICK,1);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(1890);
        meta.setDisplayName("§5The §9Knights Wand§5");
        List <String> lore = new ArrayList<>();
        lore.add("Fight For The Truth");
        lore.add("Just like the Knights");
        lore.add("In the Days of Old");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY, 1000, true);
        meta.addEnchant(Enchantment.WATER_WORKER, 1, true);
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addEnchant(Enchantment.KNOCKBACK, 100, true);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        wand = item;

        sr = new ShapedRecipe(new NamespacedKey("knightsmp", "wand"),item);
        sr.shape(
                "BBB"
                , "DSD",
                "  S");
        sr.setIngredient('S', Material.STICK);
        sr.setIngredient('D', Material.DIAMOND);
        sr.setIngredient('B', Material.BLAZE_POWDER);
      Bukkit.getServer().addRecipe(sr);

    }

    public static ShapedRecipe getRecipe(){
        return sr;
    }
}
