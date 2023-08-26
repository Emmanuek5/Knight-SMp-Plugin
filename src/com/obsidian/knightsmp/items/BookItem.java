package com.obsidian.knightsmp.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class BookItem extends ItemManager {

    public static void createBook() {
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "The Tutorial Book");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.MENDING, 22, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<String> pages = new ArrayList<>();
        StringBuilder pageContent = new StringBuilder();
        meta.setAuthor("Knights SMP");
        meta.setTitle(ChatColor.LIGHT_PURPLE+"The Tutorial Book");

        pageContent.append(ChatColor.BOLD ).append(ChatColor.BOLD).append("Welcome to the Knights SMP Tutorial Book!\n\n")
                .append("In this book, you'll learn about the power and class system.\n\n")
                .append("Power Slots:\n")
                .append("Your power level is determined by the number of power slots you have.\n")
                .append("Collect power fragments to fill your power slots and gain power.\n\n")
                .append("Classes:\n")
                .append("Based on your power slots, you'll belong to one of these classes:\n")
                .append("- Peasant: 0 power slots\n")
                .append("- Soldier: 1-4 power slots\n")
                .append("- Royal: 5-9 power slots\n")
                .append("- Knight: 10 power slots\n\n")
                .append("Collect power fragments and achieve greatness!\n");

        // Split the content into pages
        int maxCharsPerPage = 256; // Maximum characters per page (approximate)
        String[] lines = pageContent.toString().split("\n");
        StringBuilder currentPage = new StringBuilder();
        for (String line : lines) {
            if (currentPage.length() + line.length() > maxCharsPerPage) {
                pages.add(currentPage.toString());
                currentPage = new StringBuilder();
            }
            currentPage.append(line).append("\n");
        }
        if (currentPage.length() > 0) {
            pages.add(currentPage.toString());
        }
        meta.setPages(pages);
        item.setItemMeta(meta);

       book = item ;

    }
}
