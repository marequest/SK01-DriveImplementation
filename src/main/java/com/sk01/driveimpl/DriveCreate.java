package com.sk01.driveimpl;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.sk01.exceptions.ConfigException;
import com.sk01.storage.Create;
import com.sk01.utils.StorageInfo;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DriveCreate extends Create {

    static int indexDir = 1;
    static int indexFile = 1;

    @Override
    public void createDir(String path) throws Exception {

        path = StorageInfo.getInstance().getConfig().getPath() + path;

        String name = "Directory " + indexDir;
        indexDir++;
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
    public void createDirs(String path, int velicinaListe) {

        String name = "Directory ";
        while(velicinaListe > 0){
            name = name.concat(String.valueOf(indexDir));
            indexDir++;

            File fileMetadata = new File();
            fileMetadata.setName(name);
            fileMetadata.setMimeType("application/vnd.google-apps.folder");

            try {
                File file = GoogleDrive.getDriveService().files().create(fileMetadata).setFields("id").execute();
                System.out.println("Folder ID: " + file.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            velicinaListe--;
        }

    }

    @Override
    public void createFiles(String path) throws Exception {
        path = StorageInfo.getInstance().getConfig().getPath() + path;
        String name = "File " + indexFile;
        indexFile++;

        makeFile(path, name);

    }

    @Override
    public void createFiles(String path, String name) throws Exception {
        path = StorageInfo.getInstance().getConfig().getPath() + path;


        String[] nameSplited = name.split("\\.");


        if (StorageInfo.getInstance().getConfig().getUnsuportedFiles().contains(nameSplited[1])) {
            throw new ConfigException("Unsupported extension");
        }

        if (!checkNumOfFiles()) {
            throw new ConfigException("Exceeded number of files");
        }

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
    public void createFiles(String path, int velicinaListe) {
        String name = "File ";
        while(velicinaListe > 0) {
            name = name.concat(String.valueOf(indexFile));
            indexFile++;

            File fileMetadata = new File();

            fileMetadata.setName(name);

            File file = null;
            try {
                file = GoogleDrive.getDriveService().files().create(fileMetadata).setFields("id, name").execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("File ID: " + file.getId());
            velicinaListe--;
        }

    }

    private boolean checkNumOfFiles() {
        int counter = 0;
        try {
            counter = countFiles(StorageInfo.getInstance().getConfig().getPath(), counter);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return counter + 1 <= Integer.parseInt(StorageInfo.getInstance().getConfig().getNumberOfFiles());
    }

    private int countFiles(String name, int counter) throws Exception {
        File root = GoogleDrive.getFile(name);
        String query = "parents='" + root.getId() + "'";
        FileList list = GoogleDrive.service.files().list().setQ(query).setFields("nextPageToken, files(id, name, size, createdTime, mimeType, modifiedTime, parents, fileExtension)").execute();

        for (File file: list.getFiles()) {
            if (file.getMimeType().equals("application/vnd.google-apps.folder")) {
                counter = countFiles(name + "/" + file.getName(), counter);
            }
            else {
                counter++;
            }
        }
        return counter;
    }
}
