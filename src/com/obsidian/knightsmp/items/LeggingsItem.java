package com.obsidian.knightsmp.items;
import me.ikevoodoo.lssmp.LSSMP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
public class LeggingsItem extends ItemManager {

public static ShapedRecipe sr;
    public static void createLeggings(){
        ItemStack item = new ItemStack(Material.NETHERITE_LEGGINGS,1);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(1894);
        meta.setDisplayName("§5The §9Knights Leggings§5");
        List <String> lore = new ArrayList<>();
        lore.add(ChatColor.MAGIC+" This Amour");
        lore.add(ChatColor.MAGIC+" Will Protect You");
        lore.add(ChatColor.MAGIC+" Against Evil");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.SWIFT_SNEAK, 2, true);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
        meta.addEnchant(Enchantment.PROTECTION_FIRE, 5, true);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 5, true);
        meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 5, true);
        meta.addEnchant(Enchantment.DURABILITY, 1000, true);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        leggings = item;
          sr = new ShapedRecipe(new NamespacedKey("knightsmp", "leggings"),item);
        sr.shape("DDD","GNG","BSB");
        sr.setIngredient('D',Material.DIAMOND);
        sr.setIngredient('G',Material.GOLDEN_APPLE);
        sr.setIngredient('S', Material.NETHER_STAR);
        sr.setIngredient('B', Material.BLAZE_POWDER);
        sr.setIngredient('N', Material.NETHERITE_LEGGINGS);
        Bukkit.getServer().addRecipe(sr);


    }

    public static ShapedRecipe getRecipe(){
        return sr;
    }

}
