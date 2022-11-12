package com.sk01.driveimpl;

import com.google.api.services.drive.model.File;
import com.sk01.storage.Create;
import com.sk01.utils.StorageInfo;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DriveCreate extends Create {
    @Override
    public void createDir(String path) throws Exception {

        path = StorageInfo.getInstance().getConfig().getPath() + path;
        String name = "Folder1"; // TODO Vidi kako da pamtis brojeve

        makeDir(path, name);
    }

    @Override
    public void createDir(String path, String name) throws Exception {

        path = StorageInfo.getInstance().getConfig().getPath() + path;

        makeDir(path, name);
    }

    private void makeDir(String path, String name) throws IOException {
        File parent = GoogleDrive.getFile(path);
        File fileMetadata = new File();

        fileMetadata.setName(name);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        fileMetadata.setParents(List.of(parent.getId()));

        GoogleDrive.getDriveService().files().create(fileMetadata).setFields("id, name").execute();
    }

    @Override
    public void createDirs(int velicinaListe) { // TODO Sta radimo ovde za path?

        String name = "FolderRek"; // TODO Vidi kako da pamtis brojeve
        for(int i = 0; i < velicinaListe; i++){
            name = name.concat(String.valueOf(i));

            File fileMetadata = new File();
            fileMetadata.setName(name);
            fileMetadata.setMimeType("application/vnd.google-apps.folder");

            try {
                GoogleDrive.getDriveService().files().create(fileMetadata).setFields("id").execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void createFiles(String path) throws Exception {

    }

    @Override
    public void createFiles(String path, String name) throws Exception {

    }

    @Override
    public void createFiles(int velicinaListe) {

    }
}
