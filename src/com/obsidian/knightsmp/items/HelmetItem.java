package com.obsidian.knightsmp.items;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class HelmetItem extends ItemManager {

    public static ShapedRecipe sr;
    public static void createHelmet() {
        ItemStack item = new ItemStack(Material.NETHERITE_HELMET, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(1893);
        meta.setDisplayName("§5The §9Knights Helmet§5");
          List lore = new ArrayList<String>();
        lore.add(ChatColor.DARK_PURPLE+"Protection Against Evil");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.WATER_WORKER, 3, true);
        meta.addEnchant(Enchantment.OXYGEN, 3, true);
        meta.addEnchant(Enchantment.DURABILITY,3, true);
        meta.addEnchant(Enchantment.LUCK,3, true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
        meta.addEnchant(Enchantment.PROTECTION_FIRE, 5, true);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 5, true);
        meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 5, true);
        meta.addEnchant(Enchantment.BINDING_CURSE,3, true);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        helmet = item;

       sr = new ShapedRecipe(new NamespacedKey("knightsmp", "helmet"),item);
        sr.shape("DSD"," N ","BGB");
        sr.setIngredient('D', Material.DIAMOND);
        sr.setIngredient('G', Material.GOLDEN_APPLE);
        sr.setIngredient('B', Material.BLAZE_POWDER);
        sr.setIngredient('N', Material.NETHERITE_HELMET);
        sr.setIngredient('S', Material.SOUL_LANTERN);
        Bukkit.getServer().addRecipe(sr);

    }

    public static ShapedRecipe getRecipe() {
        return sr;
    }
}
