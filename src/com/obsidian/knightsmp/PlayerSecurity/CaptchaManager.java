package com.obsidian.knightsmp.PlayerSecurity;

import com.obsidian.knightsmp.KnightSmp;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.obsidian.knightsmp.KnightSmp.playerDataManager;

public class CaptchaManager {

    private final Map<UUID, String> captchaMap = new HashMap<>();
    private final File captchaFile;
    private final FileConfiguration captchaConfig;

    public CaptchaManager(File dataFolder) {
        captchaFile = new File(dataFolder, "player_data/security/captchas.yml");
        captchaConfig = YamlConfiguration.loadConfiguration(captchaFile);
        loadCaptchas();
    }

    private void loadCaptchas() {
        ConfigurationSection captchasSection = captchaConfig.getConfigurationSection("captchas");
        if (captchasSection != null) {
            for (String uuidString : captchasSection.getKeys(false)) {
                String captchaHash = captchasSection.getString(uuidString);
                captchaMap.put(UUID.fromString(uuidString), captchaHash);
            }
        }
    }
    public static boolean hasCaptcha(Player player) {
       if (playerDataManager.getCaptcha(player) != null && !playerDataManager.getCaptcha(player).equals("") || playerDataManager.getCaptcha(player) != null && !playerDataManager.getCaptcha(player).equals("null") || playerDataManager.getCaptcha(player) != null && !playerDataManager.getCaptcha(player).isEmpty()) {
           return true;
       }
        return false;
    }

    private String hashCaptcha(String captcha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(captcha.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte hashedByte : hashedBytes) {
                String hex = Integer.toHexString(0xff & hashedByte);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setPlayerCaptcha(Player player, String captcha) {
        String hashedCaptcha = hashCaptcha(captcha);
        if (hashedCaptcha != null) {
            playerDataManager.setCaptcha(player.getUniqueId(), hashedCaptcha);
            captchaMap.put(player.getUniqueId(), hashedCaptcha);
            saveCaptchaFile();
        }
    }

    public boolean verifyCaptcha(Player player, String input) {
        if (playerDataManager.getCaptcha(player) != null) {
            String inputCaptchaHash = hashCaptcha(input);
            KnightSmp.getPlugin().getServer().getConsoleSender().sendMessage(inputCaptchaHash);
            if (playerDataManager.getCaptcha(player).equals(inputCaptchaHash)) {
                captchaConfig.set("captchas." + player.getUniqueId(), null);
                saveCaptchaFile();
                return true;
            }
        }else {
            KnightSmp.getPlugin().getServer().getConsoleSender().sendMessage(input);
        }
        return false;
    }

    private void saveCaptchaFile() {
        try {
            captchaMap.forEach((uuid, captcha) -> captchaConfig.set("captchas." + uuid, captcha));
            captchaConfig.save(captchaFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
