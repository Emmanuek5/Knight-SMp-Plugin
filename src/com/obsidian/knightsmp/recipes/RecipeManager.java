package com.obsidian.knightsmp.recipes;

import com.obsidian.knightsmp.items.*;
import com.obsidian.knightsmp.items.fragments.SledgeFragment;
import org.bukkit.Bukkit;

public class RecipeManager extends ItemManager {

    public void registerRecipes() {
        Bukkit.getServer().addRecipe(SledgeHammerItem.getRecipe());
        Bukkit.getServer().addRecipe(WandItem.getRecipe());
        Bukkit.getServer().addRecipe(StaffItem.getRecipe());
        Bukkit.getServer().addRecipe(ChestPlateItem.getRecipe());
        Bukkit.getServer().addRecipe(HelmetItem.getRecipe());
        Bukkit.getServer().addRecipe(LeggingsItem.getRecipe());
        Bukkit.getServer().addRecipe(BootItem.getRecipe());
        Bukkit.getServer().addRecipe(ExaliburItem.getRecipe());
        Bukkit.getServer().addRecipe(SledgeFragment.getRecipe());
    }

}
