package com.obsidian.knightsmp.items;

import com.obsidian.knightsmp.items.fragments.FragrentManager;
import com.obsidian.knightsmp.items.fragments.SledgeFragment;
import me.ikevoodoo.lssmp.menus.RecipeEditor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SledgeHammerItem extends ItemManager{
    public static ShapedRecipe sr;

    public static void createSledgeHammer(){
        ItemStack item = new ItemStack(Material.NETHERITE_PICKAXE);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(1896);
        meta.setDisplayName(ChatColor.GOLD+"The Sledge Hammer");
        List lore = new ArrayList();
        lore.add(ChatColor.DARK_PURPLE+"An Ancient Relic from the dwarves");
        meta.addEnchant(Enchantment.MENDING,22, true);
        meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 5, true);
        meta.addEnchant(Enchantment.DIG_SPEED, 2, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setLore(lore);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        pickaxe = item;

         sr = new ShapedRecipe(new NamespacedKey("knightsmp", "sledge_hammer"),item);
        sr.shape("III"," P ","GFG");
        sr.setIngredient('I',Material.IRON_BLOCK);
        sr.setIngredient('F', Objects.requireNonNull(SledgeFragment.getMaterialData()));
        sr.setIngredient('P', Material.NETHERITE_PICKAXE);
        sr.setIngredient('G',Material.GOLD_INGOT);
        Bukkit.getServer().addRecipe(sr);
    }
    public static ShapedRecipe getRecipe(){
        return sr;
    }
}
