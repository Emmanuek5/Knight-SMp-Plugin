package com.obsidian.knightsmp.Backups;

import com.obsidian.knightsmp.KnightSmp;

import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

//import static com.obsidian.knightsmp.KnightSmp.configManager;

public class BackUpManager {

    private static final Logger logger = Logger.getLogger("BackUpManager");
    private static String baseFolder = KnightSmp.getPlugin().getServer().getWorldContainer().getAbsolutePath();


    public static void unZipFolder(String filenamepath){
try {
            File zipFile = new File(baseFolder + File.separator + filenamepath);
            File destDir = new File(baseFolder);
            KnightSmp.sendMessage("Unzipping backup..." + zipFile.getName());
            byte[] buffer = new byte[1024];
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry zipEntry = zis.getNextEntry();

            while (zipEntry != null) {
                if (zipEntry.isDirectory()) {
                    File newDir = new File(destDir + File.separator + zipEntry.getName());
                    newDir.mkdir();
                } else {
                    File newFile = new File(destDir + File.separator + zipEntry.getName());
                    new File(newFile.getParent()).mkdirs();
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }

                    fos.close();
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            KnightSmp.sendMessage("Backup unzipped successfully!");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred during backup.", e);
        }
    }
    public void backup() {
        try {
            // Create a temporary zip file
            File zipFile = File.createTempFile("plugins_backup", ".zip");
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zipOut = new ZipOutputStream(fos);

            // Zip the entire plugins folder
            Path pluginsFolderPath = Paths.get(baseFolder, "plugins");
            KnightSmp.sendMessage("Backing up plugins folder..."+ pluginsFolderPath);
            zipFolder(pluginsFolderPath.toFile(), zipOut, "");

            zipOut.close();
            fos.close();

            // Upload the zip file via HTTP/HTTPS
            try {
                uploadZipFile(zipFile); // Your upload function
                logger.info("Zip file uploaded successfully!");
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error uploading zip file.", e);
            }

            // Delete the temporary zip file
            zipFile.delete();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred during backup.", e);
        }
    }

    private void zipFolder(File folderToZip, ZipOutputStream zipOut, String parentFolder) throws IOException {
        for (File file : folderToZip.listFiles()) {
            if (file.isDirectory()) {
                zipFolder(file, zipOut, parentFolder + file.getName() + "/");
            } else {
                FileInputStream fis = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(parentFolder + file.getName());
                zipOut.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }

                fis.close();
            }
        }
    }

    private void uploadZipFile(File zipFile) throws IOException {
        KnightSmp.sendMessage("Uploading backup..." + zipFile.getName());
        String uploadUrl = "https://server-backup.blueobsidian.repl.co/upload"; // Adjust URL and port as needed
        String boundary = Long.toHexString(System.currentTimeMillis());

        HttpURLConnection connection = (HttpURLConnection) new URL(uploadUrl).openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try (
                OutputStream output = connection.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, "UTF-8"), true);
        ) {
            // Send the file part
            writer.append("--" + boundary).append("\r\n");
            writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + zipFile.getName() + "\"").append("\r\n");
            writer.append("Content-Type: application/zip").append("\r\n");
            writer.append("Content-Transfer-Encoding: binary").append("\r\n");
            writer.append("\r\n").flush();

            Files.copy(zipFile.toPath(), output);
            output.flush(); // Important before continuing with writer!
            writer.append("\r\n").flush(); // CRLF is important! It indicates end of boundary.

            // End of multipart/form-data.
            writer.append("--" + boundary + "--").append("\r\n").flush();
        }

        int responseCode = connection.getResponseCode();
        if (KnightSmp.fileManager.getFile("backups.yml") != null) {
            KnightSmp.fileManager.saveFile("backups.yml", KnightSmp.fileManager.getFile("backups.yml") + "\n" + connection.getResponseMessage());
        } else {
            KnightSmp.fileManager.saveFile("backups.yml", connection.getResponseMessage());
        }
        if (responseCode == HttpURLConnection.HTTP_OK) {
            logger.info("Backup completed successfully. |"+ connection.getResponseMessage());
        } else {
            logger.warning("Failed to complete backup. Response code: " + responseCode);
        }

        // Now you can read the response from the server
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        connection.disconnect();
    }
}
