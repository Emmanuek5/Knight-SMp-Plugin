package com.obsidian.knightsmp.items;
import com.obsidian.knightsmp.KnightSmp;
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
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+"Amethyst Leggings");
        List <String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GRAY+"The Strength of ");
        lore.add(ChatColor.DARK_GRAY+"10 GODS");
        lore.add(ChatColor.DARK_GRAY+"Combined into one");
        meta.setLore(lore);
     meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 8, true);
        meta.addEnchant(Enchantment.PROTECTION_FIRE, 8, true);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 8, true);
        meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 8, true);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 8, true);
        meta.addEnchant(Enchantment.MENDING, 8, true);
        meta.addEnchant(Enchantment.DURABILITY, 8, true);
        meta.addEnchant(Enchantment.THORNS, 2, true);
        meta.addEnchant(Enchantment.SWIFT_SNEAK, 1, true);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        leggings = item;
          sr = new ShapedRecipe(new NamespacedKey("knightsmp", "leggings"),item);
        sr.shape("DDD","GNG","ASA");
        sr.setIngredient('D',Material.DIAMOND_BLOCK);
        sr.setIngredient('G',Material.ENCHANTED_GOLDEN_APPLE);
        sr.setIngredient('S', Material.NETHER_STAR);
        sr.setIngredient('A', Material.AMETHYST_SHARD);
        sr.setIngredient('N', Material.NETHERITE_LEGGINGS);
        Bukkit.getServer().addRecipe(sr);
        KnightSmp.sendMessage("Registered recipe: " + sr.getKey().toString());


    }

    public static ShapedRecipe getRecipe(){
        return sr;
    }

}
