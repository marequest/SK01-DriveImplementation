package com.sk01.driveimpl;

import com.google.api.services.drive.model.File;
import com.sk01.storage.Operations;
import com.sk01.utils.StorageInfo;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

public class DriveOperations extends Operations {
    @Override
    public void deleteFiles(List files) {

    }

    @Override
    public void deleteFiles(String path) {

    }

    @Override
    public void deleteDir(java.io.File dir) throws Exception {

    }

    @Override
    public void moveFiles(List files, java.io.File dirFrom, java.io.File dirTo) throws Exception {
        for(Object file : files){

            // TODO Mogli bi bez liste, sve fajlove iz dirFrom u dirTo?
            // Ako ne, ne treba nam dirFrom jer to imam u file objektu

        }
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

//    @Override
//    public void rename(java.io.File file, String newName) throws Exception {
//        File parent = GoogleDrive.getFile(file.getPath()); // TODO ne znam
//        File fileMetadata = new File();
//
//        fileMetadata.setName(newName);
//        fileMetadata.setParents(List.of(parent.getId()));
//
//        GoogleDrive.getDriveService().files().update(fileMetadata).setFields("id, name").execute();
//    }
}
