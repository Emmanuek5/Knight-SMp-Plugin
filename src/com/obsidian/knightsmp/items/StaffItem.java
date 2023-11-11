package com.obsidian.knightsmp.items;

import com.obsidian.knightsmp.KnightSmp;
import org.bukkit.Bukkit;
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

public class StaffItem extends ItemManager{
    public static ShapedRecipe sr;
    public static void createStaff(){

        ItemStack item = new ItemStack(Material.FISHING_ROD,1);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(1891);
        meta.setDisplayName("§5The Wizard Staff");
        List<String> lore = new ArrayList<>();
        lore.add("§aFight Evil");
        lore.add("§dJust Like the Wizards");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY, 1000, true);
        meta.addEnchant(Enchantment.LUCK,2,false);
        meta.addEnchant(Enchantment.BINDING_CURSE,1,false);
        item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);

        staff = item;

        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey("knightsmp", "staff"),item);
        sr.shape("DDD"," S "," G ");
        sr.setIngredient('D',Material.DIAMOND_BLOCK);
        sr.setIngredient('S',Material.STICK);
        sr.setIngredient('G',Material.GOLDEN_APPLE);
        Bukkit.getServer().addRecipe(sr);
        KnightSmp.sendMessage("Registered recipe: " + sr.getKey().toString());

    }

    public static ShapedRecipe getRecipe(){
        return sr;
    }
}
