package com.obsidian.knightsmp.items;
import com.obsidian.knightsmp.KnightSmp;
import me.ikevoodoo.smpcore.items.CustomItem;
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
public class ExaliburItem extends ItemManager{

    public static ShapedRecipe sr;
    public static void createExalibur(){
        ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(1896);
        meta.setDisplayName("§5The §9Knights Exalibur§5");
        List lore = new ArrayList();
        lore.add(ChatColor.MAGIC+"Fight For The Truth");
        lore.add(ChatColor.MAGIC+"Just like the Knights");
        lore.add(ChatColor.MAGIC+"In the Days of Old");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
        meta.addEnchant(Enchantment.DURABILITY, 1000, true);
        meta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 5, true);
        meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 5, true);
        meta.addEnchant(Enchantment.FIRE_ASPECT, 5, true);
        meta.addEnchant(Enchantment.SWEEPING_EDGE, 5, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        sword = item;
         sr = new ShapedRecipe(new NamespacedKey("knightsmp", "exalibur"),item);
        sr.shape("DDD","GNG","BSB");
        sr.setIngredient('D',Material.DIAMOND);
        sr.setIngredient('G',Material.GOLDEN_APPLE);
        sr.setIngredient('S', Material.NETHER_STAR);
        sr.setIngredient('B', Material.BLAZE_POWDER);
        sr.setIngredient('N', Material.NETHERITE_SWORD);
        Bukkit.getServer().addRecipe(sr);
        KnightSmp.sendMessage("Registered recipe: " + sr.getKey().toString());
    }

    public static ShapedRecipe getRecipe(){
        return sr;
    }

}
