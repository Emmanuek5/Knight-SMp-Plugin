package com.obsidian.knightsmp.items.fragments;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class SledgeFragment extends FragrentManager{
   public static ShapedRecipe sr;

    public static void createSledgeFragment(){
        ItemStack item = new ItemStack(Material.EMERALD);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(1900);
        meta.setDisplayName("§3Sledge Fragment");
        meta.addEnchant(Enchantment.DURABILITY, 1000, true);
        meta.addEnchant(Enchantment.MENDING,22, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        sledgeFragment = item;

        sr = new ShapedRecipe(new NamespacedKey("knightsmp", "sledge_fragment"),item);
        sr.shape("RDR","RER","GGG");
        sr.setIngredient('D',Material.DIAMOND);
        sr.setIngredient('R',Material.REDSTONE_BLOCK);
        sr.setIngredient('E',Material.NETHER_STAR);
        sr.setIngredient('G',Material.GOLD_BLOCK);
        Bukkit.getServer().addRecipe(sr);

    }

    public static ShapedRecipe getRecipe(){
        return sr;

    }

    public static MaterialData getMaterialData(){
        return sledgeFragment.getData();
    }

}
