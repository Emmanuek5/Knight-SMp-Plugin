package com.obsidian.knightsmp.commands;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.obsidian.knightsmp.KnightSmp;
import com.obsidian.knightsmp.PlayerSecurity.CaptchaManager;
import com.obsidian.knightsmp.PlayerSecurity.LoginManager;
import com.obsidian.knightsmp.managers.GroundItemsManager;
import com.obsidian.knightsmp.gui.DisplayGui;
import com.obsidian.knightsmp.items.ItemManager;
import com.obsidian.knightsmp.items.fragments.FragrentManager;
import com.obsidian.knightsmp.managers.HttpRequest;
import com.obsidian.knightsmp.managers.PlayerDataManager;
import com.obsidian.knightsmp.utils.BanData;
import me.ikevoodoo.lssmp.bstats.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static com.obsidian.knightsmp.KnightSmp.*;

public class AdminCommands implements CommandExecutor {
PlayerDataManager playerDataManager;
    public AdminCommands(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return true;
        }

        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("player")) {
            if (args.length < 2) {
                sender.sendMessage(ChatColor.RED + "Usage: /player [info|restore|customitems|reset-captcha] <player>");
                return true;
            }

            String key = args[0];
            Player player1 = Bukkit.getPlayer(args[1]);

            switch (key) {
                case "info":
                    if (player1 == null){
                        sender.sendMessage(ChatColor.RED + "Player not found!");
                        return true;
                    }
                    if (key.equalsIgnoreCase("info")) {
                        player.sendMessage(ChatColor.DARK_GREEN + "----------------------------------------------------");
                        player.sendMessage(ChatColor.GOLD + "Name: " + player1.getName());
                        player.sendMessage(ChatColor.GOLD + "UUID: " + player1.getUniqueId());
                        player.sendMessage(ChatColor.GOLD + "Health: " + player1.getHealth());
                        player.sendMessage(String.format(ChatColor.GOLD + "Max Health: %.2f", player1.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
                        player.sendMessage(String.format(ChatColor.GOLD + "Level: %d", player1.getLevel()));
                        player.sendMessage(String.format(ChatColor.GOLD + "Exp: %.2f", player1.getExp()));
                        player.sendMessage(ChatColor.GOLD + "Class: " + KnightSmp.playerDataManager.getPlayerClass(player1));
                        player.sendMessage(ChatColor.GOLD + "Balance: " + essentials.getUser(player1.getUniqueId()).getMoney());
                        player.sendMessage(ChatColor.GOLD + "Power Slots Filled: " + KnightSmp.playerDataManager.getFilledPowerSlots(player1));
                        player.sendMessage(ChatColor.DARK_GREEN + "----------------------------------------------------");
                        return true;
                    }
                    break;

                case "restore":
                    if (player1 == null){
                        sender.sendMessage(ChatColor.RED + "Player not found!");
                        return true;
                    }
                    List restore = List.of(KnightSmp.playerDataManager.getPlayerLastInventory(player1));
                    for (Object item : restore) {
                        player1.getInventory().addItem((ItemStack) item);
                    }
                    player1.sendMessage(ChatColor.GREEN + "Inventory restored by " + sender.getName());
                    player.sendMessage("You Have Restored the inventory of " + player1.getName());
                    return true;
                  case "slots":
                      String [] slots = playerDataManager.getPowerSlots(player1);
                      player.sendMessage("--------------------------------------------------");
                      player.sendMessage("Name: " + player1.getName());
                      for (String slot : slots) {
                          player.sendMessage(slot);
                      }
                      player.sendMessage("--------------------------------------------------");
                      return true;
                case "customitems":
                    if (player1 == null){
                        sender.sendMessage(ChatColor.RED + "Player not found!");
                        return true;
                    }
                    ItemStack[] items = player1.getInventory().getContents();
                    player.sendMessage(""+items);
                    player.sendMessage(player.getName() +" " + player1.getName());

                    int number = 0;
                    StringBuilder itemNames = new StringBuilder();
                    for (ItemStack item : items) {
                        if (item != null && (ItemManager.isCustomItem(item) || FragrentManager.isFragment(item))) {
                            number++;
                            ItemMeta itemMeta = item.getItemMeta();
                            if (itemMeta != null && itemMeta.hasDisplayName()) {
                                String itemName = itemMeta.getDisplayName();
                                itemNames.append(itemName).append(", ");
                            }
                        }
                    }

                    String itemsMessage = itemNames.toString();
                    if (itemsMessage.length() > 2) {
                        itemsMessage = itemsMessage.substring(0, itemsMessage.length() - 2); // Remove the trailing comma and space
                    }

                    player.sendMessage(ChatColor.GOLD+"The Player " + player1.getName() + " has " + number + " custom items: " + itemsMessage);
                    return true;
                case "reset-captcha":
                    if (player1 != null) {
                        captchaManager.setPlayerCaptcha(player1, "");
                        player.sendMessage(ChatColor.GREEN + "Captcha reset for " + player1.getName());
                        return true;
                    }else {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                        if (offlinePlayer != null) {
                            playerDataManager.setCaptcha(offlinePlayer.getUniqueId(), "");
                            LoginManager.setSuspiciousLogin(offlinePlayer.getName(), false);
                            player.sendMessage(ChatColor.GREEN + "Captcha reset for " + offlinePlayer.getName()+ " thier password is now \"password\"");

                            return true;
                        }
                        player.sendMessage(ChatColor.RED + "Player not found!");
                    }
                    return true;
            }
        } else if (cmd.getName().equalsIgnoreCase("droppeditems")) {
            GroundItemsManager groundItemsManager = new GroundItemsManager();
            groundItemsManager.loadItemsFromFile();
           List<ItemStack> groundItems = groundItemsManager.getItemsOnGround();
            DisplayGui displayGui = new DisplayGui(ChatColor.BLUE+"Dropped Items", 54);
                displayGui.updateItems(groundItems);

            player.sendMessage(ChatColor.AQUA+"Opening GUI...");
            displayGui.open(player);
            return true;
        }else if (cmd.getName().equalsIgnoreCase("ban-player")) {
            if (args.length < 4) {
                player.sendMessage(ChatColor.RED + "Usage: /ban-player <player> <reason> <expiry> <isPermanent>");
                return true;
            }

            Player targetPlayer = Bukkit.getPlayer(args[0]);
            String reason = args[1];
            String expiry = args[2];
            boolean isPermanent = Boolean.parseBoolean(args[3]);

            if (targetPlayer != null) {
                try {
                    // Assuming you have a method to ban a player
                    Duration time = parseDuration(expiry);
                    targetPlayer.ban(reason, time, "Banned by " + player.getName());

                    // Replace this URL with the actual endpoint for banning players
                    String banApiUrl = configManager.getString("ban-api-route") + "/create";

                    // Create a JSON object
                    Gson gson = new Gson();
                    String json = gson.toJson(new BanData(targetPlayer.getName(), reason, expiry, isPermanent, targetPlayer.getUniqueId()));

                    // Send the POST request to the server with JSON data
                    String response = HttpRequest.sendJsonPostRequest(banApiUrl, json);
                    System.out.println(response);
                    player.sendMessage(ChatColor.GREEN + "Player " + targetPlayer.getName() + " has been banned!");
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid time format. Please provide a valid duration in milliseconds.");
                } catch (IOException e) {
                    player.sendMessage(ChatColor.RED + "Error communicating with the ban API. Please try again later.");
                    e.printStackTrace();
                }
            } else {
                player.sendMessage(ChatColor.RED + "Player not found!");
            }
            return true;
        }


        return false;
    }

    public Duration parseDuration(String duration) {
        //Try to parse the duration like 12h or 12d or 12wk or 1yyr etc
        try {
            long milliseconds = 0;
            int index = 0;

            while (index < duration.length()) {
                StringBuilder numberBuilder = new StringBuilder();

                while (index < duration.length() && Character.isDigit(duration.charAt(index))) {
                    numberBuilder.append(duration.charAt(index));
                    index++;
                }

                long number = Long.parseLong(numberBuilder.toString());

                if (index < duration.length()) {
                    char unit = duration.charAt(index);
                    index++;

                    switch (unit) {
                        case 'h':
                            milliseconds += number * 60 * 60 * 1000;
                            break;
                        case 'd':
                            milliseconds += number * 24 * 60 * 60 * 1000;
                            break;
                        case 'w':
                            milliseconds += number * 7 * 24 * 60 * 60 * 1000;
                            break;
                        case 'y':
                            milliseconds += number * 365 * 24 * 60 * 60 * 1000;
                            break;
                    }
                }
            }

            return Duration.ofMillis(milliseconds);
        } catch (NumberFormatException e) {
            // Handle invalid duration format
            throw new IllegalArgumentException("Invalid duration format, The formats are 12h, 12d, 12wk, 1y etc");
        }
    }

}
