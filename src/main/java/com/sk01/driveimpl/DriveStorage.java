package com.sk01.driveimpl;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;
import com.google.gson.Gson;
import com.sk01.storage.Storage;
import com.sk01.utils.StorageInfo;


import java.io.*;
import java.util.*;

public class DriveStorage extends Storage {
    @Override
    public java.io.File getConfig(String path) throws Exception {
        File file = GoogleDrive.getFile(path + "/config.json");
        String fileID = file.getId();

        java.io.File config = new java.io.File("config.json");
        OutputStream outputstream = new FileOutputStream(config);
        GoogleDrive.getDriveService().files().get(fileID).executeMediaAndDownloadTo(outputstream);
        outputstream.flush();
        outputstream.close();

        return config;
    }

    @Override
    public void editConfig(String storageName, String maxSize, String maxNumOfFiles, List<String> unsupportedFiles) throws Exception {

        Map<String, Object> configMap = new HashMap<>();
        configMap.put("path", storageName);
        configMap.put("maxSize", (maxSize != null) ? maxSize : StorageInfo.getInstance().getConfig().getMaxSize());
        configMap.put("maxNumOfFiles", (maxNumOfFiles != null) ? maxNumOfFiles : StorageInfo.getInstance().getConfig().getNumberOfFiles());
        configMap.put("unsupportedFiles", (unsupportedFiles != null) ? unsupportedFiles : StorageInfo.getInstance().getConfig().getUnsuportedFiles());

        java.io.File config = new java.io.File("config.json");
        try {
            Writer writer = new FileWriter(config);
            new Gson().toJson(configMap, writer);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // Uzimamo fajl, brisemo, dodajemo novi
        File configDrive = GoogleDrive.getFile(storageName + "/config.json");
        GoogleDrive.getDriveService().files().delete(configDrive.getId()).execute();
        createSettings(config, storageName);
        config.delete();
    }

    @Override
    public void createStorage(String path, String storageName) throws Exception {

        File parent = GoogleDrive.getFile(path);
        File fileMetadata = new File();

        fileMetadata.setName(storageName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        if (parent == null) {
            fileMetadata.setParents(null);
        }
        else {
            fileMetadata.setParents(Collections.singletonList(parent.getId()));
        }

        GoogleDrive.getDriveService().files().create(fileMetadata).setFields("id, name").execute();

        java.io.File configFile = new java.io.File("config.json");

        // Pravimo i cuvamo config na drajvu
        initConfig(configFile, storageName);
        createSettings(configFile, storageName);
    }

    private void initConfig(java.io.File configFile, String path) {
        Map<String, Object> configMap = new HashMap<>();
        configMap.put("path", path);
        configMap.put("maxSize", "undefined");
        configMap.put("maxNumOfFiles", "undefined");
        configMap.put("unsupportedFiles", null);

        try {
            Writer writer = new FileWriter(configFile);
            new Gson().toJson(configMap, writer);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createSettings(java.io.File settings, String storageName) {
        AbstractInputStreamContent uploadStreamContent = new FileContent(null, settings);
        File parent = GoogleDrive.getRootFile(storageName);

        File fileMetadata = new File();
        fileMetadata.setName(settings.getName());
        fileMetadata.setParents(List.of(parent.getId()));

        try { // TODO ?
            GoogleDrive.service.files().create(fileMetadata, uploadStreamContent).setFields("id, webContentLink, webViewLink, parents").execute();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
