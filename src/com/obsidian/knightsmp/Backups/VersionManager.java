package com.obsidian.knightsmp.Backups;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.obsidian.knightsmp.KnightSmp;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
        import java.io.FileInputStream;
        import java.io.IOException;

        import java.net.URL;



public class VersionManager {

    private static final Logger logger = Logger.getLogger("VersionManager");
    private static final String SERVER_URL = "https://server-backup.blueobsidian.repl.co" ;
    private static String baseFolder = KnightSmp.getPlugin().getServer().getWorldContainer().getAbsolutePath();

    public static void manageVersion() {
        String currentVersion = getVersionFromPluginJar();
        String latestVersion = getLatestVersionFromServer();

        if (currentVersion == null || latestVersion == null) {
            logger.warning("Unable to determine version information.");
            return;
        }

        int compareResult = compareVersions(currentVersion, latestVersion);

        if (compareResult < 0) {
            logger.info("Current version is older than the latest version on the server.");
            downloadLatestVersion();
        } else if (compareResult > 0) {
            logger.info("Current version is newer than the latest version on the server.");
            uploadPluginJar();
        } else {
            logger.info("Current version is up to date.");
        }
    }

    private static String getVersionFromPluginJar() {
        return KnightSmp.getVerison();
    }


    public static String getVersionState(){
        String currentVersion = getVersionFromPluginJar();
        String latestVersion = getLatestVersionFromServer();

        if (currentVersion == null || latestVersion == null) {
            logger.warning("Unable to determine version information.");
            return "Unable to determine version information." ;
        }

        int compareResult = compareVersions(currentVersion, latestVersion);
        if (compareResult < 0) {
            logger.info("Current version is older than the latest version on the server.");
            return "Current version is older than the latest version on the server." ;
        } else if (compareResult > 0) {
            logger.info("Current version is newer than the latest version on the server.");
            return "Current version is newer than the latest version on the server." ;
        } else {
            logger.info("Current version is up to date.");
            return "Current version is up to date." ;
        }
    }


    public static void downloadAndSavePlugin(String downloadUrl, String saveFileName) {
        String savePath = baseFolder + "/plugins/" + saveFileName;

        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (InputStream inputStream = connection.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(savePath)) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                logger.info("Plugin downloaded and saved: " + saveFileName);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error downloading or saving plugin.", e);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error connecting to server.", e);
        }
    }
    private static String getLatestVersionFromServer() {
        String serverUrl = SERVER_URL; // Change to your server URL
        String endpoint = "/latest"; // Change to the appropriate endpoint

        try {
            URL url = new URL(serverUrl + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String latestVersion = reader.readLine().replace("version-", "");
                 logger.info("Latest version from server: " + latestVersion);
                    return latestVersion;
                }
            } else {
                logger.warning("Failed to fetch latest version from server. Response code: " + responseCode);
                return null;
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error connecting to server.", e);
            return null;
        }
    }
    private static int compareVersions(String version1, String version2) {
        // Logic to compare versions
        // Implement your version comparison logic here
        return version1.compareTo(version2);
    }

 public static void downloadLatestVersion() {
        String downloadUrl = SERVER_URL + "/latest";
        String savePath = baseFolder + "/plugins/Knight Smp Plugin  1.jar";

        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (InputStream inputStream = connection.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(savePath)) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                logger.info("Latest version downloaded and saved.");
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error downloading latest version.", e);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error connecting to server.", e);
        }
    }


    public static void uploadPluginJar() {
        File pluginFile = new File(baseFolder, "plugins/Knight Smp Plugin  1.jar");

        if (!pluginFile.exists()) {
            logger.warning("Plugin JAR file not found.");
            return;
        }

        try {
            String uploadUrl = "https://server-backup.blueobsidian.repl.co/upload"; // Adjust URL and port as needed
            String boundary = Long.toHexString(System.currentTimeMillis());

            HttpURLConnection connection = (HttpURLConnection) new URL(uploadUrl).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            try (
                    FileInputStream fis = new FileInputStream(pluginFile);
                    OutputStream output = connection.getOutputStream();
            ) {
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, "UTF-8"), true);

                // Send the file part
                writer.append("--" + boundary).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + pluginFile.getName() + "\"").append("\r\n");
                writer.append("Content-Type: application/java-archive").append("\r\n");
                writer.append("Content-Transfer-Encoding: binary").append("\r\n");
                writer.append("\r\n").flush();

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                output.flush(); // Important before continuing with writer!
                writer.append("\r\n").flush(); // CRLF is important! It indicates end of boundary.

                // End of multipart/form-data.
                writer.append("--" + boundary + "--").append("\r\n").flush();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                logger.info("Plugin JAR uploaded successfully.");
            } else {
                logger.warning("Failed to upload plugin JAR. Response code: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error uploading plugin JAR.", e);
        }
    }
}
