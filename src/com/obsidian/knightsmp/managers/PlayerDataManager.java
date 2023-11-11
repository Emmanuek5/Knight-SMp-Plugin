package com.obsidian.knightsmp.managers;

import com.obsidian.knightsmp.KnightSmp;
import com.obsidian.knightsmp.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

import static com.obsidian.knightsmp.KnightSmp.configManager;

public class PlayerDataManager {

    public Map<UUID, PlayerData> playerDataMap = new HashMap<>();
    private File dataFolder;



    public PlayerDataManager(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public PlayerData getPlayerData(Player player) {
        return playerDataMap.get(player.getUniqueId());
    }
    public PlayerData getPlayerData(UUID player) {
        return playerDataMap.get(player);
    }

    public void setPlayerData(Player player, PlayerData playerData) {
        playerDataMap.put(player.getUniqueId(), playerData);
    }
    public String getLastIp(Player player) {
     PlayerData playerData = playerDataMap.get(player.getUniqueId());
     if (playerData != null) {
        return playerData.getLastIp();
     }
        return null;
    }
    public void setLastIp(Player player, String lastIp) {
        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            playerData.setLastIp(lastIp);
        }
    }

    public boolean canUsePowers(Player player) {
        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            return playerData.isUsePowers();
        }
        return false;
    }

    public  boolean canUsePowers(UUID player) {
        PlayerData playerData = playerDataMap.get(player);
        if (playerData != null) {
            return playerData.isUsePowers();
        }
        return false;
    }

    public void setUsePowers(Player player, boolean usePowers) {
        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            playerData.setUsePowers(usePowers);
        }
    }
    public String getCaptcha(Player player) {
        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            return playerData.getCaptcha();
        }
        return null;
    }
    public String getCaptcha(UUID player) {
        PlayerData playerData = playerDataMap.get(player);
        if (playerData != null) {
            return playerData.getCaptcha();
        }
        return null;
    }
    public void setCaptcha(UUID player, String captcha) {
        PlayerData playerData = playerDataMap.get(player);
        if (playerData != null) {
            playerData.setCaptcha(captcha);
        }
    }


    public boolean hasPlayerData(Player player) {
        return playerDataMap.containsKey(player.getUniqueId());
    }
    public boolean hasPlayerData(UUID player) {return playerDataMap.containsKey(player);}

    public void setPowerSlot(Player player, int slotIndex, String power) {
        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            playerData.setPowerSlot(slotIndex, power);
        }
    }

    public int getPlayerKills (Player player) {
        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            return playerData.getKills();
        }
        return 0;
    }
    public int getPlayerKills (UUID player) {
        PlayerData playerData = playerDataMap.get(player);
        if (playerData != null) {
            return playerData.getKills();
        }
        return 0;
    }

    public void setPlayerKills (Player player, int kills) {
        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            playerData.setKills(kills);
        }
    }
    public void addPlayerKill(Player player){
        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            playerData.setKills(playerData.getKills() + 1);
        }
    }

    public int getPlayerDeaths (Player player) {
        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            return playerData.getDeaths();
        }
        return 0;
    }
    public int getPlayerDeaths (UUID player) {
        PlayerData playerData = playerDataMap.get(player);
        if (playerData != null) {
            return playerData.getDeaths();
        }
        return 0;
    }

    public void setPlayerDeaths (Player player, int deaths) {
        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            playerData.setDeaths(deaths);
        }
    }
    public void addPlayerDeath(Player player){
        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            playerData.setDeaths(playerData.getDeaths() + 1);
        }
    }


    public void setNextPowerSlot(Player player, String power) {
        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            String[] powerSlots = playerData.getPowerSlots();

            // Find the next available power slot
            int nextAvailableSlot = -1;
            for (int i = 0; i < powerSlots.length; i++) {
                if (powerSlots[i] == null) {
                    nextAvailableSlot = i;
                    break;
                }
            }

            if (nextAvailableSlot != -1) {
                // Set the power in the next available slot
                playerData.setPowerSlot(nextAvailableSlot, power);
                player.sendMessage("Power slot " + (nextAvailableSlot + 1) + " filled with " + power);

                // Check the number of filled power slots and update player class
                int filledSlots = getFilledPowerSlots(player);
                if (filledSlots >= 10) {
                    setPlayerClass(player, "knight");
                } else if (filledSlots >= 4) {
                    setPlayerClass(player, "royal");
                } else if (filledSlots >= 1) {
                    setPlayerClass(player, "soldier");
                }
            } else {
                player.sendMessage("All power slots are filled. You have reached the maximum.");
            }
        }
    }


    public List<Player> getAllPlayers() {
        List<Player> allPlayers = new ArrayList<>();

        // Add all online players
        allPlayers.addAll(Bukkit.getOnlinePlayers());

        // Add all offline players
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            if (offlinePlayer.hasPlayedBefore() && offlinePlayer.isOnline()) {
                allPlayers.add(offlinePlayer.getPlayer());
            }
        }

        return allPlayers;
    }

    public List<String> getAllPlayerNames() {
        List<String> playerNames = new ArrayList<>();

        // Add names of all online players
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            playerNames.add(onlinePlayer.getName());
        }

        // Add names of all offline players
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            if (offlinePlayer.hasPlayedBefore() && offlinePlayer.getName() != null) {
                playerNames.add(offlinePlayer.getName());
            }
        }

        return playerNames;
    }

     public Player getPlayerByName(String playerName) {
        // Search online players first
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(playerName)) {
                return player;
            }
        }

        // Search offline players using Bukkit's UUID lookup
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
        if (offlinePlayer.hasPlayedBefore()) {
            return offlinePlayer.getPlayer();
        }

        return null; // Player not found|}
         //
         }

    public OfflinePlayer getOfflinePlayer(String playerName) {
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            if (Objects.equals(offlinePlayer.getName(), playerName)) {
                return offlinePlayer;
            }
        }
        return null; // Player not found
    }


    public String getLastKnownLocation(Player player) {
        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            return playerData.getLastKnownLocation();
        }
        return null;
    }

    public void setLastKnownLocation(Player player, String location) {
        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            playerData.setLastKnownLocation(location);
        }
    }

    public String getLastKnownLocation(UUID player) {
        PlayerData playerData = playerDataMap.get(player);
        if (playerData != null) {
            return playerData.getLastKnownLocation();
        }
        return null;
    }



    public Player getPlayerByUUID(UUID uuid) {
        Player onlinePlayer = Bukkit.getPlayer(uuid);
        if (onlinePlayer != null) {
            return onlinePlayer;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if (offlinePlayer != null) {
            return offlinePlayer.getPlayer();
        }
        // Note: offlinePlayer.getPlayer() will return null for offline players
        // You can use offlinePlayer.getName() to get the player's name

        return null;
    }

    public int getFilledPowerSlots(Player player) {
        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            int filledSlots = 0;
            for (String powerSlot : playerData.getPowerSlots()) {
                if (powerSlot != null) {
                    filledSlots++;
                }
            }
            return filledSlots;
        }
        return 0;
    }

    public String[] getPowerSlots(Player player) {
        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            return playerData.getPowerSlots();
        }
        return null;
    }

    public String[] getPowerSlots(UUID player) {
        PlayerData playerData = playerDataMap.get(player);
        if (playerData != null) {
            return playerData.getPowerSlots();
        }
        return null;
    }
    public ItemStack[] getPlayerLastInventory(Player player) {
        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            return playerData.getLastInventory();
        }
        return null;
    }

    public ItemStack[] getPlayerLastInventory(UUID player) {
        PlayerData playerData = playerDataMap.get(player);
        if (playerData != null) {
            return playerData.getLastInventory();
        }
        return null;
    }

    public void setPlayerLastInventory(Player player, ItemStack[] inventory) {
        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            playerData.setLastInventory(inventory);
        }
    }

    public void setPlayerLastInventory(UUID player, ItemStack[] inventory) {
        PlayerData playerData = playerDataMap.get(player);
        if (playerData != null) {
            playerData.setLastInventory(inventory);
        }
    }

    public ItemStack[] getPlayerInventory(Player player) {
        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            return playerData.getInventory();
        }
        return null;
    }

    public ItemStack[] getPlayerInventory(UUID player) {
        PlayerData playerData = playerDataMap.get(player);
        if (playerData != null) {
            return playerData.getInventory();
        }
        return null;
    }

    public void setPlayerInventory(Player player, ItemStack[] inventory) {

        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            playerData.setInventory(inventory);
        }
    }

    public void setPlayerInventory(UUID player, ItemStack[] inventory) {
        PlayerData playerData = playerDataMap.get(player);
        if (playerData != null) {
            playerData.setInventory(inventory);
        }
    }

    public String getPlayerClass(Player player) {
        UUID playerUUID = player.getUniqueId();
        PlayerData playerData = playerDataMap.get(playerUUID);
        if (playerData != null) {
            return playerData.getPlayerClass();
        }

        // If player data is not available, return a default class
        return "peasant"; // Change this to your desired default class
    }

    public String getPlayerClass(UUID player) {
        UUID playerUUID = player;
        PlayerData playerData = playerDataMap.get(playerUUID);
        if (playerData != null) {
            return playerData.getPlayerClass();
        }

        // If player data is not available, return a default class
        return "peasant"; // Change this to your desired default class
    }
    public void setPlayerClass(Player player, String playerClass) {
        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            playerData.setPlayerClass(playerClass);
            player.sendMessage(ChatColor.GREEN + "You've been promoted to " + playerClass);
        }
    }

    public void incrementPlaytime(Player player, long seconds) {
        PlayerData playerData = getPlayerData(player);
        if (playerData != null) {
            playerData.incrementPlaytime(seconds);
        }
    }

    public long getPlaytime(Player player) {
        PlayerData playerData = getPlayerData(player);
        if (playerData != null) {
            return playerData.getPlaytime();
        }
        return 0;
    }
    public long getPlaytime(UUID player) {
        PlayerData playerData = getPlayerData(player);
        if (playerData != null) {
            return playerData.getPlaytime();
        }
        return 0;
    }



    public void setPowerSlots(Player player, String[] powerSlots) {
        PlayerData playerData = playerDataMap.get(player.getUniqueId());
        if (playerData != null) {
            playerData.setPowerSlots(powerSlots);
        }
    }


    public void loadPlayerData() {
        File playerDataFolder = new File(dataFolder, "player_data");

        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
            return;
        }

        Yaml yaml = new Yaml();

        for (File file : playerDataFolder.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".yml")) {
                try (FileReader reader = new FileReader(file)) {
                    Map<String, Object> yamlData = yaml.load(reader);

                    String playerName = (String) yamlData.get("Name");
                    UUID playerUUID = UUID.fromString((String) yamlData.get("UUID"));

                    String playerClass = (String) yamlData.get("Class");
                    String Power = (String) yamlData.get("Power");
                    if (Power == null) {
                        Power = "default";
                    }
                    if (playerClass == null) {
                        playerClass = "peasant";
                    }


                    PlayerData playerData = new PlayerData(playerUUID, playerName, 0, Power, playerClass ); // Create a new PlayerData object


                    if (yamlData.containsKey("Captcha")) {
                        if (yamlData.get("Captcha") != null) {
                            String captcha = (String) yamlData.get("Captcha");
                            playerData.setCaptcha(captcha);
                        }
                    }
                    if (yamlData.containsKey("playtime")) {
                        if (yamlData.get("playtime") != null) {
                            int playtimeInt = (int) yamlData.get("playtime");

                            // Convert int to long

                            playerData.setPlaytime((long) playtimeInt);
                        }
                    }


                    if (yamlData.containsKey("kills")) {
                        if (yamlData.get("kills") != null) {
                            int kills = (int) yamlData.get("kills");
                            playerData.setKills(kills);
                        }
                    }
                    if (yamlData.containsKey("deaths")) {
                        if (yamlData.get("deaths") != null) {
                            int deaths = (int) yamlData.get("deaths");
                            playerData.setDeaths(deaths);
                        }
                    }
                    // Deserialize lastIp if available
                    if (yamlData.containsKey("last-login-ip")) {
                        if (yamlData.get("last-login-ip") != null) {
                            String lastIp = (String) yamlData.get("last-login-ip");
                          playerData.setLastIp(lastIp);
                        }
                    }
                    // Deserialize lastInventory if available
                    if (yamlData.containsKey("lastInventory")) {
                        List<Map<String, Object>> lastInventoryList = (List<Map<String, Object>>) yamlData.get("lastInventory");
                        ItemStack[] lastInventory = new ItemStack[lastInventoryList.size()];
                        for (int i = 0; i < lastInventoryList.size(); i++) {
                            Map<String, Object> itemData = lastInventoryList.get(i);
                            Material itemType = Material.valueOf((String) itemData.get("type"));
                            int itemAmount = (int) itemData.get("amount");
                            lastInventory[i] = new ItemStack(itemType, itemAmount);

                            // Check for the custom tag and retrieve the power slot
                            if (yamlData.containsKey("powerSlots")) {
                                List<String> powerSlotsList = (List<String>) yamlData.get("powerSlots");
                                String[] powerSlots = new String[powerSlotsList.size()];
                                powerSlotsList.toArray(powerSlots);
                                playerData.setPowerSlots(powerSlots);
                            }
                        }
                        playerData.setLastInventory(lastInventory);
                    }

                    // Deserialize lastKnownLocation if available
                    if (yamlData.containsKey("lastKnownLocation")) {
                        String lastKnownLocation = (String) yamlData.get("lastKnownLocation");
                        playerData.setLastKnownLocation(lastKnownLocation);
                    }
                    // Deserialize inventory if available
                    if (yamlData.containsKey("inventory")) {
                        List<Map<String, Object>> inventoryList = (List<Map<String, Object>>) yamlData.get("inventory");
                        ItemStack[] inventory = new ItemStack[inventoryList.size()];
                        for (int i = 0; i < inventoryList.size(); i++) {
                            Map<String, Object> itemData = inventoryList.get(i);
                            Material itemType = Material.valueOf((String) itemData.get("type"));
                            int itemAmount = (int) itemData.get("amount");
                            inventory[i] = new ItemStack(itemType, itemAmount);
                        }
                        playerData.setInventory(inventory);
                    }

                    // Set power slots (if available)
                    if (yamlData.containsKey("powerSlots")) {
                        List<String> powerSlotsList = (List<String>) yamlData.get("powerSlots");
                        String[] powerSlots = new String[powerSlotsList.size()];
                        powerSlotsList.toArray(powerSlots);
                        playerData.setPowerSlots(powerSlots);
                    }
                    playerData.setPlayerClass(playerClass);
                    playerDataMap.put(playerUUID, playerData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void savePlayerData() {
        File playerDataFolder = new File(dataFolder, "player_data");
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        for (Map.Entry<UUID, PlayerData> entry : playerDataMap.entrySet()) {
            UUID playerUUID = entry.getKey();
            PlayerData playerData = entry.getValue();

            File playerFile = new File(playerDataFolder, playerUUID.toString() + ".yml");
            try (FileWriter writer = new FileWriter(playerFile)) {
                Map<String, Object> dataMap = new LinkedHashMap<>();
                dataMap.put("UUID", playerData.getPlayerUUID().toString());
                dataMap.put("Name", playerData.getPlayerName());
                dataMap.put("Power", playerData.getPower());
                dataMap.put("last-login-ip", playerData.getLastIp());
                dataMap.put("Captcha", playerData.getCaptcha());
                dataMap.put("kills", playerData.getKills());
                dataMap.put("deaths", playerData.getDeaths());
                dataMap.put("playtime", (int) playerData.getPlaytime());
                dataMap.put("lastKnownLocation", playerData.getLastKnownLocation());

                if (playerData.getPower() != null && !playerData.getPower().isEmpty() && playerData.getPower() != "") {

                    dataMap.put("Power", playerData.getPower());
                }else {
                    dataMap.put("Power", configManager.getString("default-player-power"));
                }
                if (playerData.getPlayerClass() != null && !playerData.getPlayerClass().isEmpty() && playerData.getPlayerClass() != ""){
                    dataMap.put("Class", playerData.getPlayerClass());
                }else {
                    dataMap.put("Class", configManager.getString("default-player-class"));
                }
              // Save player class



                String[] powerSlots = playerData.getPowerSlots();
                if (powerSlots != null) {
                    List<String> powerSlotsList = new ArrayList<>(Arrays.asList(powerSlots));
                    dataMap.put("powerSlots", powerSlotsList);
                }

                // Extract item names, amounts, and metadata from the lastInventory
                ItemStack[] lastInventory = playerData.getLastInventory();
                if (lastInventory != null) {
                    List<Map<String, Object>> itemsList = new ArrayList<>();
                    for (int i = 0; i < lastInventory.length; i++) {
                        ItemStack item = lastInventory[i];
                        if (item != null && item.getType() != Material.AIR) {
                            Map<String, Object> itemMap = new LinkedHashMap<>();
                            itemMap.put("type", item.getType().name());
                            itemMap.put("amount", item.getAmount());

                            // Save power slot if it exists
                            itemsList.add(itemMap);
                        }
                    }
                    dataMap.put("lastInventory", itemsList);
                }

                ItemStack [] inventory = playerData.getInventory();
                if (inventory != null) {
                    List<Map<String, Object>> itemsList = new ArrayList<>();
                    for (int i = 0; i < inventory.length; i++) {
                        ItemStack item = inventory[i];
                        if (item != null && item.getType() != Material.AIR) {
                            Map<String, Object> itemMap = new LinkedHashMap<>();
                            itemMap.put("type", item.getType().name());
                            itemMap.put("amount", item.getAmount());

                            // Save power slot if it exists
                            itemsList.add(itemMap);
                        }
                    }
                    dataMap.put("inventory", itemsList);
                }

                // Write the YAML using SnakeYAML
                yaml.dump(dataMap, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean hasPowerSlot(Player player, String powerSlot) {
        PlayerData playerData = getPlayerData(player);
        if (playerData != null) {
            String[] powerSlots = playerData.getPowerSlots();
            for (String slot : powerSlots) {
                if (slot != null && slot.equalsIgnoreCase(powerSlot)) {
                    return true; // Player has the specified power slot
                }
            }
        }
        return false; // Player does not have the specified power slot
    }


}
