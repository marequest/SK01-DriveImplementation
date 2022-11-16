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

        File file = GoogleDrive.getDriveService().files().create(fileMetadata).setFields("id, name").execute();
        System.out.println("Folder ID: " + file.getId());
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
                File file = GoogleDrive.getDriveService().files().create(fileMetadata).setFields("id").execute();
                System.out.println("Folder ID: " + file.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void createFiles(String path) throws Exception {
        path = StorageInfo.getInstance().getConfig().getPath() + path;
        String name = "File1";

        makeFile(path, name);

    }

    @Override
    public void createFiles(String path, String name) throws Exception {
        path = StorageInfo.getInstance().getConfig().getPath() + path;

        // TODO Provera configa

        makeFile(path, name);

    }

    private void makeFile(String path, String name) throws IOException {
        File parent = GoogleDrive.getFile(path);
        File fileMetadata = new File();

        fileMetadata.setName(name);
        fileMetadata.setParents(List.of(parent.getId()));

        File file = GoogleDrive.getDriveService().files().create(fileMetadata).setFields("id, name").execute();
        System.out.println("File ID: " + file.getId());
    }

    @Override
    public void createFiles(int velicinaListe) {
        String name = "fileRek";
        for(int i = 0; i < velicinaListe; i++) {
            name = name.concat(String.valueOf(i));

            File fileMetadata = new File();

            fileMetadata.setName(name);

            File file = null;
            try {
                file = GoogleDrive.getDriveService().files().create(fileMetadata).setFields("id, name").execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("File ID: " + file.getId());
        }

    }
}
