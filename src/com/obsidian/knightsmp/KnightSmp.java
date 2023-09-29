package com.obsidian.knightsmp;

import com.obsidian.knightsmp.Backups.BackUpManager;
import com.obsidian.knightsmp.Backups.VersionManager;
import com.obsidian.knightsmp.PlayerSecurity.CaptchaManager;
import com.obsidian.knightsmp.PlayerSecurity.LoginManager;
import com.obsidian.knightsmp.commands.AdminCommands;
import com.obsidian.knightsmp.commands.ConsoleCommands;
import com.obsidian.knightsmp.commands.PlayerCommands;
import com.obsidian.knightsmp.commands.PluginCommands;
import com.obsidian.knightsmp.commands.TabCompleters.LoginTabCompleter;
import com.obsidian.knightsmp.commands.TabCompleters.PlayerTabCompleter;
import com.obsidian.knightsmp.commands.TabCompleters.RegisterTabCompleter;
import com.obsidian.knightsmp.commands.TabCompleters.ResetPasswordTabCompleter;
import com.obsidian.knightsmp.events.*;
import com.obsidian.knightsmp.managers.*;
import com.obsidian.knightsmp.items.*;
import com.obsidian.knightsmp.managers.HttpHandlers.DefaultHttpHandler;
import com.obsidian.knightsmp.managers.HttpHandlers.PlayerHandler;
import com.obsidian.knightsmp.server.HttpMethod;
import com.obsidian.knightsmp.server.WebServer;
import com.obsidian.knightsmp.server.WebServerHandler;
import com.obsidian.knightsmp.utils.*;

import net.ess3.api.IEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class KnightSmp extends JavaPlugin {
   public static PlayerDataManager playerDataManager;
   public static  PowerSlotsGUI powerSlotsGUI;
   public static CaptchaManager captchaManager;
   public static FileManager fileManager;
    public  static ConfigManager configManager ;
    public static WebServerHandler webServerHandler;
    public static ApiManager apiManager;

    public static IEssentials essentials = (IEssentials) Bukkit.getPluginManager().getPlugin("Essentials");

    public static Object getInstance() {
        return KnightSmp.class;
    }
    public static   KnightSmp getPlugin() {
        return (KnightSmp) Bukkit.getPluginManager().getPlugin("Knight_Plugin");
    }
    public static String getVerison(){
        return getPlugin().getDescription().getVersion();
    }
    @Override
    public void onEnable() {
        ItemManager.init();
        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        playerDataManager = new PlayerDataManager(dataFolder);
        playerDataManager.loadPlayerData();
        KnightSmp.sendMessage(VersionManager.getVersionState());
        fileManager = new FileManager(this);

        if (fileManager.getFile("config.yml").exists()) {
            configManager = new ConfigManager(this);
            if (configManager.getBoolean("first-run")) {
                FileConfiguration config = configManager.getConfig();
                config.set("first-run",false);
            }
        }else{
            fileManager.downloadAndSave("https://file-host-1.blueobsidian.repl.co/uploads/default-config.yml", "config.yml");
            configManager = new ConfigManager(this);
            FileConfiguration config = configManager.getConfig();
            config.set("ftp-server-host-address","5.62.127.53");
            config.set("ftp-server-port", 555);
            config.set("ftp-server-username","1101490.209215");
            config.set("ftp-server-password","523v8OUrW8GevyshjBJ1eLqseOAyzu2PBEsl48gNuGvxu580");
            config.set("server-api-port", 5500);
            configManager.saveConfig();
            configManager.reloadConfig();
        }

        try {
            WebServer webServer = new WebServer(configManager.getInt("server-api-port"));
            webServer.start();
            webServerHandler= new WebServerHandler(webServer.getServer());
             apiManager = new ApiManager(webServerHandler);
            apiManager.registerRoutes();
            webServerHandler.handleDefault(HttpMethod.GET,new DefaultHttpHandler());
            KnightSmp.sendMessage("Server Web Api Started on Port "+configManager.getInt("server-api-port"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        captchaManager  = new CaptchaManager(dataFolder);
        configManager.reloadConfig();
        GroundItemsManager groundItemsManager = new GroundItemsManager();
        groundItemsManager.loadItemsFromFile();

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "-------------------------");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Loading player data...");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Loading Scoreboard");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Loading Recipes...");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Loading Items...");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Loading Ground Items...");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "There are " + groundItemsManager.getItemsOnGround().size() + " ground items");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN+"Loaded: "+playerDataManager.playerDataMap.size()+" player's");
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "_ _ _ ___ _");
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "_ __ /_\\ _ __ | |_(_) / __\\ ___ | |_");
        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "| '_ \\ //_\\\\| '_ \\| __| |/__\\/// _ \\| __|");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "| | | / _ \\ | | | |_| / \\/ \\ (_) | |_");
        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "|_| |_\\_/ \\_/_| |_|\\__|_\\_____/\\___/ \\__|");


// Display "knight" with different colors
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "k" +
                ChatColor.GOLD + "n" +
                ChatColor.YELLOW + "i" +
                ChatColor.GREEN + "g" +
                ChatColor.AQUA + "h" +
                ChatColor.DARK_PURPLE + "t");

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "-------------------------");

        PluginCommands commands = new PluginCommands();
        AdminCommands adminCommands = new AdminCommands(playerDataManager);
        PlayerCommands playerCommands = new PlayerCommands();
        ConsoleCommands consoleCommands = new ConsoleCommands();

        getServer().getPluginManager().registerEvents(new PluginEvents(),this);
        getServer().getPluginManager().registerEvents(new FragmentEvents(),this);
        getServer().getPluginManager().registerEvents(new ItemEvents(),this);
        getServer().getPluginManager().registerEvents(new PlayerEvents(playerDataManager),this);
        getServer().getPluginManager().registerEvents(new SelectionEvent(),this);
        getServer().getPluginManager().registerEvents(groundItemsManager,this);
        getCommand("heal").setExecutor(commands);
        getCommand("select").setExecutor(commands);
        getCommand("giveitem").setExecutor(commands);
        getCommand("feed").setExecutor(commands);
        getCommand("farmtime").setExecutor(commands);
        getCommand("player").setExecutor(adminCommands);
        getCommand("droppeditems").setExecutor(adminCommands);
        getCommand("download-latest-version").setExecutor(consoleCommands);

        getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "The KnightSmp Plugin is now enabled!");

    }

    public static void sendMessage(String message){
        getPlugin().getServer().getConsoleSender().sendMessage(message);
    }

    @Override
    public void onDisable() {

        // Create a folder for the plugin if it doesn't exist

        ConfigManager configManager = new ConfigManager(this);
        configManager.reloadConfig();
        // Access the configuration
        FileConfiguration config = configManager.getConfig();
        // Use config.get("key") to get values from the configuration

        // Modify the configuration
        config.set("name", "Knight SMP PLugin");
        config.set("plugin-version", getVerison());
        config.set("plugin-author", "Blue Obsidian");
        config.set("player-number", playerDataManager.playerDataMap.size());
        config.set("default-palyer-class", configManager.getString("default-player-class")); ;
        config.set("default-player-power", configManager.getString("default-player-power")); ;
        config.set("server-port", Bukkit.getServer().getPort());
        config.set("default-language", configManager.getString("default-language"));
        config.set("server-gamemode",configManager.getString("default-gamemode"));
        config.set("default-world", Bukkit.getServer().getWorlds().get(0).getName());
        config.set("server-ip", Bukkit.getServer().getIp());
        config.set("server-name", Bukkit.getServer().getName());
        config.set("server-version", Bukkit.getServer().getVersion());
        config.set("server-api-port",configManager.getInt("server-api-port"));
        // Save the modified configuration
        configManager.saveConfig();

        if (playerDataManager != null) {
            playerDataManager.savePlayerData();
        }
        VersionManager.manageVersion();
       if (!configManager.getBoolean("is-test-server")) {
           BackUpManager backUpManager = new BackUpManager();
           sendMessage(ChatColor.GOLD+"Backing up the server !>");
           backUpManager.backup();
       }

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Saving player data...");
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "The KnightSmp Plugin is now disabled!");
    }

    public PlayerData getUserInfo(Player player) {
        return playerDataManager.getPlayerData(player);
    }

    public void saveUserInfo(Player player, PlayerData playerData) {
        playerDataManager.setPlayerData(player, playerData);
        playerDataManager.savePlayerData();
    }
}
