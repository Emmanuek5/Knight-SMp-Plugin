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
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+"Amethyst ChestPlate");
        List lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY+"Nah fam, this is the real deal");
        meta.setLore(lore);
       meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 8, true);
        meta.addEnchant(Enchantment.PROTECTION_FIRE, 8, true);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 8, true);
        meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 8, true);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 8, true);
        meta.addEnchant(Enchantment.MENDING, 8, true);
        meta.addEnchant(Enchantment.DURABILITY, 8, true);
        meta.addEnchant(Enchantment.THORNS, 2, true);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.addEnchant(Enchantment.WATER_WORKER, 8, true);
        meta.addEnchant(Enchantment.OXYGEN, 8, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);

        chestPlate = item;
        //
         sr = new ShapedRecipe(new NamespacedKey("knightsmp", "chest_plate"),item);
        sr.shape("DDD","GNG","ASA");
        sr.setIngredient('D',Material.DIAMOND_BLOCK);
        sr.setIngredient('G',Material.ENCHANTED_GOLDEN_APPLE);
        sr.setIngredient('S', Material.NETHER_STAR);
        sr.setIngredient('A', Material.AMETHYST_SHARD);
        sr.setIngredient('N', Material.NETHERITE_CHESTPLATE);
        Bukkit.getServer().addRecipe(sr);
    }

    public static ShapedRecipe getRecipe(){
        return sr;
    }

}
