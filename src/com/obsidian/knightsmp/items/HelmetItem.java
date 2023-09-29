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
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+"Amethyst Helmet");
          List lore = new ArrayList<String>();
        lore.add(ChatColor.DARK_GRAY+"For The True Warriors");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 8, true);
        meta.addEnchant(Enchantment.PROTECTION_FIRE, 8, true);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 8, true);
        meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 8, true);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 8, true);
        meta.addEnchant(Enchantment.MENDING, 8, true);
        meta.addEnchant(Enchantment.DURABILITY, 8, true);
        meta.addEnchant(Enchantment.OXYGEN,8, true);
        meta.addEnchant(Enchantment.WATER_WORKER,8, true);
        meta.addEnchant(Enchantment.THORNS, 2, true);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        helmet = item;

       sr = new ShapedRecipe(new NamespacedKey("knightsmp", "helmet"),item);
        sr.shape("DSD","ANA","BGB");
        sr.setIngredient('D', Material.DIAMOND_BLOCK);
        sr.setIngredient('G', Material.ENCHANTED_GOLDEN_APPLE);
        sr.setIngredient('B', Material.BLAZE_POWDER);
        sr.setIngredient('N', Material.NETHERITE_HELMET);
        sr.setIngredient('S', Material.SOUL_LANTERN);
        sr.setIngredient('A', Material.AMETHYST_SHARD);
        Bukkit.getServer().addRecipe(sr);

    }

    public static ShapedRecipe getRecipe() {
        return sr;
    }
}
