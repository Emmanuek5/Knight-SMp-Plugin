package com.obsidian.knightsmp.managers;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    private final JavaPlugin plugin;

    public FileManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public String getFileContent(String filePath) {
        Path path = Paths.get(plugin.getDataFolder() + File.separator + filePath);

        try {
            byte[] bytes = Files.readAllBytes(path);
            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public File getFile(String filePath) {
        Path path = Paths.get(plugin.getDataFolder() + File.separator + filePath);
        return new File(path.toString());
    }




    public void saveFile(String filePath, String data) {
        Path path = Paths.get(plugin.getDataFolder() + File.separator + filePath);

        try {
            Files.write(path, data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean folderExists(String folderPath) {
        Path path = Paths.get(plugin.getDataFolder() + File.separator + folderPath);
        return Files.exists(path) && Files.isDirectory(path);
    }

    public void deleteFile(String filePath) {
        Path path = Paths.get(plugin.getDataFolder() + File.separator + filePath);

        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFolder(String folderPath) {
        Path path = Paths.get(plugin.getDataFolder() + File.separator + folderPath);

        try {
            if (Files.exists(path) && Files.isDirectory(path)) {
                Files.walk(path)
                        .map(Path::toFile)
                        .sorted((f1, f2) -> -f1.compareTo(f2))
                        .forEach(File::delete);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getFilesInDirectory(String directoryPath) {
        Path path = Paths.get(plugin.getDataFolder() + File.separator + directoryPath);
        try {
            return Files.list(path)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .toArray(String[]::new);
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    public void downloadAndSave(String url, String savePath) {
        try {
            URL downloadUrl = new URL(url);
            InputStream inputStream = downloadUrl.openStream();
            Path saveFilePath = Paths.get(plugin.getDataFolder() + File.separator + savePath);
            Files.copy(inputStream, saveFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createFolder(String folderPath) {
        Path path = Paths.get(plugin.getDataFolder() + File.separator + folderPath);

        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
