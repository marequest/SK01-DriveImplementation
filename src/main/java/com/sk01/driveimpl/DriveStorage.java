package com.sk01.driveimpl;

import com.sk01.storage.Storage;

import java.io.File;
import java.util.List;

public class DriveStorage extends Storage {
    @Override
    public File getConfig(String path) {
        return null;
    }

    @Override
    public void editConfig(String path, String maxSize, String maxNumOfFiles, List<String> unsupportedFiles) {

    }

    @Override
    public void createStorage() {

    }
}
