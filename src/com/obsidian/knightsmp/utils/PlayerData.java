package com.obsidian.knightsmp.utils;

import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.UUID;

public class PlayerData implements Serializable {

    private ItemStack[] lastInventory;
    private String lastKnownLocation;
    private ItemStack[] inventory;
    private UUID playerUUID;
    private String playerName;
    private int playerScore;
    private String power; // New field for power
    private String[] powerSlots;
    private String playerClass;
    private String lastIp;
    private String Captcha;
    private int deaths;
    private int kills;
    private long playtime;
    private boolean usePowers;

    public PlayerData(UUID playerUUID, String playerName, int playerScore, String power , String playerClass) {
        this.playerUUID = playerUUID;
        this.playerName = playerName;
        this.playerScore = playerScore;
        this.power = power; // Default value for power
        this.powerSlots = new String[10];
        this.playerClass = playerClass;
        this.lastIp = "";
        this.Captcha = "";

    }



    public void incrementPlaytime(long seconds) {
        this.playtime += seconds;
    }

    public long getPlaytime() {
        return this.playtime;
    }
    public void setPlaytime(long playtime) {
        this.playtime = playtime;
    }

    public boolean isUsePowers() {
        return usePowers;
    }
    public void setUsePowers(boolean usePowers) {
        this.usePowers = usePowers;
    }
    public ItemStack[] getLastInventory() {
        return lastInventory;
    }
    public void setLastInventory(ItemStack[] lastInventory) {
        this.lastInventory = lastInventory;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public void setInventory(ItemStack[] inventory) {
        this.inventory = inventory;
    }

    public String getLastKnownLocation() {
        return lastKnownLocation;
    }

    public void setLastKnownLocation(String lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }
    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getKills() {
        return kills;
    }
    public void setKills(int kills) {
        this.kills = kills;
    }
    public int getDeaths() {
        return deaths;
    }
    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }




    public void setPowerSlot(int slotIndex, String power) {
        if (slotIndex >= 0 && slotIndex < powerSlots.length) {
            powerSlots[slotIndex] = power;
        }
    }
    public String getLastIp() {
        return lastIp;
    }
    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }
    public String getCaptcha() {
        return Captcha;
    }

    public void setCaptcha(String captcha) {
        Captcha = captcha;
    }
    public String[] getPowerSlots() {
        return powerSlots;
    }
    public String getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }
    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }


    public void setPowerSlots(String[] powerSlots) {
        this.powerSlots = powerSlots;
    }
}