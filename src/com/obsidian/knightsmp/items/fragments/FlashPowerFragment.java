package com.obsidian.knightsmp.items.fragments;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class FlashPowerFragment extends FragrentManager{



    public static void createFlashPowerFragment(){
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD +"Flash Power Fragment");
        meta.setUnbreakable(true);
        meta.setCustomModelData(1999);
        meta.addEnchant(Enchantment.LUCK, 10, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
       item.setItemMeta(meta);
        flashPowerFragment = item;

        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey("knightsmp", "flash_power_fragment"),item);
        sr.shape("GDG","GNG","CBC");
        sr.setIngredient('D',Material.DIAMOND);
        sr.setIngredient('G',Material.GOLD_BLOCK);
        sr.setIngredient('N',Material.NETHER_STAR);
        sr.setIngredient('C',Material.COPPER_BLOCK);
        sr.setIngredient('B',Material.BLAZE_ROD);
        Bukkit.getServer().addRecipe(sr);
    }


}
