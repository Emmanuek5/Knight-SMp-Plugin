package com.obsidian.knightsmp.utils;

import java.time.Duration;
import java.util.UUID;

public class BanData {
    private String username;
    private String reason;
    private String expiry;
    private boolean isPermanent;
    private UUID uuid;

    public BanData(String username, String reason, String expiry, boolean isPermanent, UUID uuid) {
        this.username = username;
        this.reason = reason;
        this.expiry = expiry;
        this.isPermanent = isPermanent;
        this.uuid = uuid;
    }

    // Add getters and setters as needed

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public boolean isPermanent() {
        return isPermanent;
    }

    public void setPermanent(boolean permanent) {
        isPermanent = permanent;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
