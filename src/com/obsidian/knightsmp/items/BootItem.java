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
public class BootItem extends ItemManager{


    public static ShapedRecipe sr;
    public static void createBoots(){
        ItemStack item = new ItemStack(Material.NETHERITE_BOOTS);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(1895);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+"Amethyst Boots");

        List lore = new ArrayList();
        lore.add("Forged from Amethyst");
        lore.add( "From the Depths of the Earth");
        lore.add("From the ancient cities of the Dwarves");
        meta.setLore(lore);
       meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
        meta.addEnchant(Enchantment.PROTECTION_FIRE, 4, true);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, true);
        meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 4, true);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, true);
        meta.addEnchant(Enchantment.MENDING, 4, true);
        meta.addEnchant(Enchantment.DURABILITY, 5, true);
        meta.addEnchant(Enchantment.DEPTH_STRIDER, 6, true);
        meta.addEnchant(Enchantment.SOUL_SPEED, 6, true);
        meta.addEnchant(Enchantment.FROST_WALKER, 6, true);
        meta.addEnchant(Enchantment.THORNS, 4, true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
        meta.addEnchant(Enchantment.WATER_WORKER, 1, true);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);

        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        boots = item;
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey("knightsmp", "boots"),item);
        sr.shape("DDD","GNG","ASA");
        sr.setIngredient('D',Material.DIAMOND_BLOCK);
        sr.setIngredient('G',Material.ENCHANTED_GOLDEN_APPLE);
        sr.setIngredient('S', Material.NETHER_STAR);
        sr.setIngredient('A', Material.AMETHYST_SHARD);
        sr.setIngredient('N', Material.NETHERITE_BOOTS);
        Bukkit.getServer().addRecipe(sr);
    }

    public static ShapedRecipe getRecipe(){
        return sr;
    }
}
