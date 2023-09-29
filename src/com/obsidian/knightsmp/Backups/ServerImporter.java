package com.obsidian.knightsmp.Backups;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.checkerframework.checker.units.qual.K;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;



public class ServerImporter {
    private static final String FTP_HOST = "5.62.127.53";
    private static final int FTP_PORT =555;
    private static final String FTP_USER = "1101490.209215";
    private static final String FTP_PASS = "523v8OUrW8GevyshjBJ1eLqseOAyzu2PBEsl48gNuGvxu580";
    private static final String IMPORTS_FOLDER =  "./imports";

    public static void main(String[] args) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(FTP_HOST, FTP_PORT);
            ftpClient.login(FTP_USER, FTP_PASS);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

           System.out.println("Connected to FTP server");
            System.out.println("The Server Might Not Respond For A While, Please Wait");
            System.out.println("Downloading server files...");
            System.out.println("This May Take A While, Please Wait");
            downloadFolder(ftpClient, "world", IMPORTS_FOLDER + "/world");
            downloadFolder(ftpClient, "world_nether", IMPORTS_FOLDER + "/world_nether");
            downloadFolder(ftpClient, "world_the_end", IMPORTS_FOLDER + "/world_the_end");
            downloadFolder(ftpClient, "plugins", IMPORTS_FOLDER + "/plugins");

            ftpClient.logout();
            ftpClient.disconnect();
            System.out.println("Disconnected from FTP server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void downloadFolder(FTPClient ftpClient, String remoteFolder, String localFolder) throws IOException {
        downloadDirectory(ftpClient, remoteFolder, localFolder, "");
    }

    private static void downloadDirectory(FTPClient ftpClient, String remoteFolder, String localFolder, String subFolderPath) throws IOException {
        String remoteFolderPath = remoteFolder + "/" + subFolderPath;
        String localFolderPath = localFolder + "/" + subFolderPath;
        FTPFile[] ftpFiles = ftpClient.listFiles(remoteFolderPath);

        if (ftpFiles != null && ftpFiles.length > 0) {
            for (FTPFile file : ftpFiles) {
                if (file.isFile()) {
                    // Download the file
                    String remoteFilePath = remoteFolderPath + "/" + file.getName();
                    String localFilePath = localFolderPath + "/" + file.getName();
                    File localFile = new File(localFilePath);
                    if (!localFile.exists()) {
                        // Create the local file if it does not exist
                        localFile.getParentFile().mkdirs();
                        localFile.createNewFile();
                    }
                    System.out.println("Downloading " + remoteFilePath + " to " + localFilePath);
                    FileOutputStream fos = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(remoteFilePath, fos);
                    fos.close();
                } else if (file.isDirectory()) {
                    // Create the subfolder

                    String newSubFolderPath = subFolderPath + "/" + file.getName();
                    File newSubFolder = new File(localFolderPath + "/" + file.getName());
                    newSubFolder.mkdirs();
                    System.out.println("Downloading " + remoteFolderPath + " to " + localFolderPath);
                    // Download the subfolder
                    downloadDirectory(ftpClient, remoteFolder, localFolder, newSubFolderPath);
                }
            }
        }
        ftpClient.changeToParentDirectory();
    }




}
