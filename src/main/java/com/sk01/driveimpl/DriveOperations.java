package com.sk01.driveimpl;

import com.google.api.services.drive.model.File;
import com.sk01.exceptions.UnexistingPathException;
import com.sk01.storage.Operations;
import com.sk01.utils.StorageInfo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class DriveOperations extends Operations {


    @Override
    public void deleteFile(String path) throws Exception {
        path = StorageInfo.getInstance().getConfig().getPath() + path;

        File child = GoogleDrive.getFile(path);
        GoogleDrive.getDriveService().files().delete(child.getId()).execute();
    }

    @Override
    public void deleteDir(String path) throws Exception {
        path = StorageInfo.getInstance().getConfig().getPath() + path;

        File child = GoogleDrive.getFile(path);
        GoogleDrive.getDriveService().files().delete(child.getId()).execute();
    }

    @Override
    public void moveFiles(String fromPath, String toPath) throws Exception {
        if (GoogleDrive.getFile(fromPath) == null) {
            throw new UnexistingPathException("Path doesn't exist in storage - fromPath");
        }

        if (GoogleDrive.getFile(toPath) == null) {
            throw new UnexistingPathException("Path doesn't exist in storage - toPath");
        }

        File file = GoogleDrive.getFile(fromPath);
        File dir = GoogleDrive.getFile(toPath);

        StringBuilder previousParents = new StringBuilder();  //mora builder, jer toString lose radi
        for (String parent : file.getParents()) {
            previousParents.append(parent);
            previousParents.append(',');
        }

        GoogleDrive.getDriveService().files().update(file.getId(), null).setAddParents(dir.getId()).setRemoveParents(previousParents.toString()).setFields("id, parents").execute();

    }


    @Override
    public void downloadFile(String pathFrom, String pathTo) throws Exception{
        pathFrom = StorageInfo.getInstance().getConfig().getPath() + pathFrom;

        File driveFile = GoogleDrive.getFile(pathFrom);

        java.io.File file = new java.io.File(pathTo);
        OutputStream outputstream = new FileOutputStream(file);
        GoogleDrive.getDriveService().files().get(driveFile.getId()).executeMediaAndDownloadTo(outputstream);
        outputstream.flush();
        outputstream.close();
    }

    @Override
    public void rename(String path, String name) throws Exception {

        if (GoogleDrive.getFile(path) == null) {
            throw new UnexistingPathException("Path doesn't exist in storage");
        }

        File file = GoogleDrive.getFile(path);
        file.setName(name);
    }


}
