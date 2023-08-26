package com.obsidian.knightsmp.items;

import com.obsidian.knightsmp.KnightSmp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ChestPlateItem extends ItemManager{

public static ShapedRecipe sr;
    public static void createChestPlate(){
        ItemStack item = new ItemStack(Material.NETHERITE_CHESTPLATE,1);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(1892);
        meta.setDisplayName(ChatColor.GOLD+"Knights Chest Plate");
        List lore = new ArrayList<String>();
        lore.add(ChatColor.MAGIC+" This Amour");
        lore.add(ChatColor.MAGIC+" Will Protect You");
        lore.add(ChatColor.MAGIC+" Against Evil");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY, 1000, true);
        meta.addEnchant(Enchantment.LUCK,2,true);
        meta.addEnchant(Enchantment.MENDING,4,true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
        meta.addEnchant(Enchantment.PROTECTION_FIRE, 5, true);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 5, true);
        meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 5, true);
        meta.addEnchant(Enchantment.THORNS,1,true);
        meta.addEnchant(Enchantment.BINDING_CURSE,1,true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);

        chestPlate = item;
        //
         sr = new ShapedRecipe(new NamespacedKey("knightsmp", "chest_plate"),item);
        sr.shape("DDD","GNG","BSB");
        sr.setIngredient('D',Material.DIAMOND);
        sr.setIngredient('G',Material.GOLDEN_APPLE);
        sr.setIngredient('S', Material.NETHER_STAR);
        sr.setIngredient('B', Material.BLAZE_POWDER);
        sr.setIngredient('N', Material.NETHERITE_CHESTPLATE);
        Bukkit.getServer().addRecipe(sr);
    }

    public static ShapedRecipe getRecipe(){
        return sr;
    }

}
