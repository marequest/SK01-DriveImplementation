package com.sk01.driveimpl;

import com.google.api.services.drive.model.File;
import com.sk01.storage.Storage;


import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

public class DriveStorage extends Storage {
    @Override
    public java.io.File getConfig(String path) {
        File file = GoogleDrive.getFile(path + "/config.json");
        String fileID = file.getId();

        java.io.File config = new java.io.File("config.json");
        OutputStream outputstream = new FileOutputStream(config);
        GoogleDrive.service.files().get(fileID).executeMediaAndDownloadTo(outputstream);
        outputstream.flush();
        outputstream.close();

        return config;
    }

    @Override
    public void editConfig(String path, String maxSize, String maxNumOfFiles, List<String> unsupportedFiles) {

    }

    @Override
    public void createStorage() {

    }
}
