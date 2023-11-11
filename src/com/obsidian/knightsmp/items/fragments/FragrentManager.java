package com.obsidian.knightsmp.items.fragments;

import com.obsidian.knightsmp.items.ItemManager;
import org.bukkit.inventory.ItemStack;

import static com.obsidian.knightsmp.items.fragments.FlashPowerFragment.createFlashPowerFragment;
import static com.obsidian.knightsmp.items.fragments.SledgeFragment.createSledgeFragment;

public class FragrentManager extends ItemManager {
    public static ItemStack sledgeFragment;
    public static ItemStack exaliburFragment;
    public static ItemStack flashPowerFragment;


    public static void init (){
        createSledgeFragment();
        createFlashPowerFragment();
    }

    public static boolean isFragment(ItemStack item) {
        return item.isSimilar(sledgeFragment) || item.isSimilar(exaliburFragment) || item.isSimilar(flashPowerFragment);
    }

}
 