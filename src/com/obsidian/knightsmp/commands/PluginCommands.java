package com.obsidian.knightsmp.commands;

import com.obsidian.knightsmp.Backups.VersionManager;
import com.obsidian.knightsmp.inventories.SelectionScreen;
import com.obsidian.knightsmp.items.ItemManager;
import com.obsidian.knightsmp.items.fragments.FragrentManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Objects;

public class PluginCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)){
            sender.sendMessage("You must be a player to use this command!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("heal")){
            if (args.length >0){
                Player player1 = Bukkit.getPlayer(args[0]);
                if (player1 != null){
                    player1.setHealth(player1.getMaxHealth());
                    player1.sendMessage(ChatColor.YELLOW + "Healed You from "+ player1.getPlayer().getHealth() + "to " + player1.getMaxHealth());
                    player.sendMessage(""+ player1.getName() + " has been healed!");
                }
            }
            double maxhealth = (int) player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            player.setHealth(maxhealth);
            player.sendMessage(ChatColor.YELLOW + "Healed You from "+ player.getPlayer().getHealth() + "to " + maxhealth );
        }

        if (cmd.getName().equalsIgnoreCase("giveitem")) {
            if (args.length < 1) {
                player.sendMessage(ChatColor.RED + "Usage: /giveitem <item name>");
                return true; // Exit the command
            }

            String item = args[0];
            if (Objects.equals(item, "wand")) {
                if (args.length >= 2) {
                    Player targetPlayer = Bukkit.getPlayer(args[1]);
                    if (targetPlayer != null) {
                        targetPlayer.getInventory().addItem(ItemManager.wand);
                        player.sendMessage(ChatColor.AQUA + "You have given the wand to " + targetPlayer.getName());
                        targetPlayer.sendMessage(ChatColor.AQUA + "You have received the Wand! Use it with caution!");
                    } else {
                        player.sendMessage(ChatColor.RED + "Target player not found.");
                    }
                } else {
                    player.getInventory().addItem(ItemManager.wand);
                    player.sendMessage(ChatColor.AQUA + "You have received the Wand! Use it with caution!");
                }
            } else if (Objects.equals(item, "staff")) {
                if (args.length >= 2) {
                    Player targetPlayer = Bukkit.getPlayer(args[1]);
                    if (targetPlayer != null) {
                        targetPlayer.getInventory().addItem(ItemManager.staff);
                        player.sendMessage(ChatColor.AQUA + "You have given the staff to " + targetPlayer.getName());
                        targetPlayer.sendMessage(ChatColor.AQUA + "You have received the Staff! Use it with caution!");
                    } else {
                        player.sendMessage(ChatColor.RED + "Target player not found.");
                    }
                } else {
                    player.getInventory().addItem(ItemManager.staff);
                    player.sendMessage(ChatColor.AQUA + "You have received the Staff! Use it with caution!");
                }
            } else if (Objects.equals(item,"chestplate")) {
                if (args.length >= 2) {
                    Player targetPlayer = Bukkit.getPlayer(args[1]);
                    if (targetPlayer != null) {
                        targetPlayer.getInventory().addItem(ItemManager.chestPlate);
                        player.sendMessage(ChatColor.AQUA + "You have given the chestplate to " + targetPlayer.getName());
                        targetPlayer.sendMessage(ChatColor.AQUA + "You have received the Chestplate! Use it with caution!");
                    } else {
                        player.sendMessage(ChatColor.RED + "Target player not found.");
                    }
                } else {
                    player.getInventory().addItem(ItemManager.chestPlate);
                    player.sendMessage(ChatColor.AQUA + "You have received the Chestplate! Use it with caution!");
                }
            } else if (Objects.equals(item,"helmet")){
                if (args.length >= 2) {
                    Player targetPlayer = Bukkit.getPlayer(args[1]);
                    if (targetPlayer != null) {
                        targetPlayer.getInventory().addItem(ItemManager.helmet);
                        player.sendMessage(ChatColor.AQUA + "You have given the Helmet to " + targetPlayer.getName());
                        targetPlayer.sendMessage(ChatColor.AQUA + "You have received the Helmet! Use it with caution!");
                    } else {
                        player.sendMessage(ChatColor.RED + "Target player not found.");
                    }
                } else {
                    player.getInventory().addItem(ItemManager.helmet);
                    player.sendMessage(ChatColor.AQUA + "You have received the Helmet! Use it with caution!");
                }
            } else if (Objects.equals(item,"leggings")){
                if (args.length >= 2) {
                    Player targetPlayer = Bukkit.getPlayer(args[1]);
                    if (targetPlayer != null) {
                        targetPlayer.getInventory().addItem(ItemManager.leggings);
                        player.sendMessage(ChatColor.AQUA + "You have given the Leggings to " + targetPlayer.getName());
                        targetPlayer.sendMessage(ChatColor.AQUA + "You have received the Leggings! Use it with caution!");
                    } else {
                        player.sendMessage(ChatColor.RED + "Target player not found.");
                    }
                } else {
                    player.getInventory().addItem(ItemManager.leggings);
                    player.sendMessage(ChatColor.AQUA + "You have received the Leggings! Use it with caution!");
                }
                  } else if (Objects.equals(item, "boots")){
                if (args.length >= 2) {
                    Player targetPlayer = Bukkit.getPlayer(args[1]);
                    if (targetPlayer != null) {
                        targetPlayer.getInventory().addItem(ItemManager.boots);
                        player.sendMessage(ChatColor.AQUA + "You have given the Boots to " + targetPlayer.getName());
                        targetPlayer.sendMessage(ChatColor.AQUA + "You have received the Boots! Use it with caution!");
                    } else {
                        player.sendMessage(ChatColor.RED + "Target player not found.");
                    }
                } else {
                    player.getInventory().addItem(ItemManager.boots);
                    player.sendMessage(ChatColor.AQUA + "You have received the Boots! Use it with caution!");
                }
            }
             else if (Objects.equals(item, "flashpower")){
                if (args.length >= 2) {
                    Player targetPlayer = Bukkit.getPlayer(args[1]);
                    if (targetPlayer != null) {
                        targetPlayer.getInventory().addItem(FragrentManager.flashPowerFragment);
                        player.sendMessage(ChatColor.AQUA + "You have given the FlashPower to " + targetPlayer.getName());
                        targetPlayer.sendMessage(ChatColor.AQUA + "You have received the FlashPower! Use it with caution!");
                    } else {
                        player.sendMessage(ChatColor.RED + "Target player not found.");
                    }
                } else {
                    player.getInventory().addItem(FragrentManager.flashPowerFragment);
                    player.sendMessage(ChatColor.AQUA + "You have received the FlashPower! Use it with caution!");
                }
            }else {
                player.sendMessage(ChatColor.RED + "Invalid item name.");
            }
        }

        if (cmd.getName().equalsIgnoreCase("select")){
            SelectionScreen gui = new SelectionScreen();
            player.openInventory(gui.getInventory());
            player.sendMessage(ChatColor.AQUA+"Make A Selection");
        }

        if (cmd.getName().equalsIgnoreCase("feed")) {
            player.setFoodLevel(20);
        }
        if (cmd.getName().equalsIgnoreCase("farmtime")){
            if (!(args.length >= 2)) {
                player.sendMessage(ChatColor.RED + "Usage: /farmtime <mob> <amount>");
            }
            try {
                EntityType mob = EntityType.valueOf(args[0].toUpperCase());
                int amount = Integer.parseInt(args[1]);
                for (int i = 0; i < amount; i++) {
                    player.getWorld().spawnEntity(player.getLocation(), mob);
                }


            }catch (IllegalArgumentException e){
             player.sendMessage(ChatColor.RED + "That is not a valid mob!");
            }




        }

        return true;
    }
}
