package com.obsidian.knightsmp.items;



import com.obsidian.knightsmp.items.fragments.FragrentManager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;


public class ItemManager {
    public static ItemStack wand;
    public  static ItemStack staff;
    public  static ItemStack chestPlate;
    public static ItemStack helmet;
    public static ItemStack leggings;
    public static ItemStack boots;
    public static ItemStack sword;
    public static ItemStack pickaxe;
    public static ItemStack book;
    public static void init() {
        FragrentManager.init();
     StaffItem staff = new StaffItem();
     StaffItem.createStaff();
     WandItem wand = new WandItem();
     WandItem.createWand();
     ChestPlateItem.createChestPlate();
     HelmetItem.createHelmet();
    LeggingsItem.createLeggings();
    SledgeHammerItem.createSledgeHammer();
    BookItem.createBook();
    }


    public static boolean isCustomItem(ItemStack item) {
        return item.isSimilar(wand) || item.isSimilar(staff) || item.isSimilar(chestPlate) || item.isSimilar(helmet) || item.isSimilar(leggings) || item.isSimilar(boots) || item.isSimilar(sword) || item.isSimilar(pickaxe) || item.isSimilar(book);
    }







}
